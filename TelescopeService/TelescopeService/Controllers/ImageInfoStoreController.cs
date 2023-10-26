using Dapr;
using Dapr.Client;
using Microsoft.AspNetCore.Mvc;
using TelescopeService.Dto;
using TelescopeService.Events;
using TelescopeService.Service;

namespace TelescopeService.Controllers;

[ApiController]
[Route("")]
public class ImageInfoStoreController : ControllerBase
{
    private readonly ILogger<ImageInfoStoreController> _logger;

    private const string EventStoreName = "statestore";

    public ImageInfoStoreController(ILogger<ImageInfoStoreController> logger, DaprClient daprClient)
    {
        _logger = logger;

    }

    [Topic("pubsub", "schedule", "deadletters", false)]
    [Route("/storeImageInfo")]
    [HttpPost()]
    public async Task<ActionResult> StoreImageInfo(CloudEvent<ScheduleTelescopeInDto> telescopeScheduleEvent, [FromServices] DaprClient daprClient)
    {
        ScheduleTelescopeInDto telescopeSchedule = telescopeScheduleEvent.Data;

        _logger.LogInformation($"Got schedule request: user {telescopeSchedule.requestedByUser}");

        ImageHandlerService imageHandlerService = new ImageHandlerService(daprClient);

        try {
            for (int i = 0; i < telescopeSchedule.endDateTime; i++)
            {
                var image = await Generator.Generator.Generate(telescopeSchedule.lensType);

                var blobURL = await imageHandlerService.SaveImageToBlob(image.ImageName, image.ImageData);

                _logger.LogInformation($"Image created and uploaded to blob store. Blob download URL: {blobURL}");

                ImageCreated imageCreated = new ImageCreated(telescopeSchedule.requestedByUser, blobURL, DateTime.Now);
                Guid guid = Guid.NewGuid();

                await daprClient.SaveStateAsync<ImageCreated>(EventStoreName, guid.ToString(), imageCreated);
            }
        } catch (Exception ex)
        {
            //return BadRequest(ex.Message);
            throw;
        }
        
        return Ok();
    }

}