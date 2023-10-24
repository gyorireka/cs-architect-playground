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
    [Route("storeImageInfo")]
    [HttpPost()]
    public async Task<ActionResult> StoreImageInfo(ScheduleTelescopeInDto telescopeSchedule, [FromServices] DaprClient daprClient)
    {
        _logger.LogInformation($"Got schedule request: user {telescopeSchedule.RequestedByUser}");

        ImageHandlerService imageHandlerService = new ImageHandlerService(daprClient);

        try {
            for (int i = 0; i < telescopeSchedule.EndDateTime; i++)
            {
                var image = await Generator.Generator.Generate(telescopeSchedule.LensType);

                var blobURL = await imageHandlerService.SaveImageToBlob(image.ImageName, image.ImageData);

                _logger.LogInformation($"Image created and uploaded to blob store. Blob download URL: {blobURL}");

                ImageCreated imageCreated = new ImageCreated(telescopeSchedule.RequestedByUser, blobURL, DateTime.Now);

                await daprClient.SaveStateAsync<ImageCreated>(EventStoreName, telescopeSchedule.RequestedByUser, imageCreated);
            }
        } catch (Exception ex)
        {
            return BadRequest(ex.Message);
        }
        
        return Ok();
    }

}