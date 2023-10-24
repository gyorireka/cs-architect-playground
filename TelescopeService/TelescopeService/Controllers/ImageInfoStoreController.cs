using Dapr;
using Dapr.Client;
using Microsoft.AspNetCore.Mvc;
using TelescopeService.Dto;

namespace FineCollectionService.Controllers;

[ApiController]
[Route("")]
public class ImageInfoStoreController : ControllerBase
{
    private readonly ILogger<ImageInfoStoreController> _logger;

    public ImageInfoStoreController(ILogger<ImageInfoStoreController> logger, DaprClient daprClient)
    {
        _logger = logger;
    }

    [Topic("pubsub", "schedule", "deadletters", false)]
    [Route("storeImageInfo")]
    [HttpPost()]
    public async Task<ActionResult> StoreImageInfo(ScheduleTelescopeInDto speedingViolation, [FromServices] DaprClient daprClient)
    {
        _logger.LogInformation($"Got schedule request: user {speedingViolation.RequestedByUser}");

        // store to event store

        return Ok();
    }

}