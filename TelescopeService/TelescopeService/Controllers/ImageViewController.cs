using Dapr.Client;
using Microsoft.AspNetCore.Mvc;

namespace FineCollectionService.Controllers;

[ApiController]
[Route("")]
public class ImageViewController : ControllerBase
{
    private readonly ILogger<ImageViewController> _logger;

    public ImageViewController(ILogger<ImageViewController> logger, DaprClient daprClient)
    {
        _logger = logger;
    }

    [Route("viewImages")]
    [HttpPost()]
    public async Task<ActionResult> ViewImages([FromServices] DaprClient daprClient)
    {
        _logger.LogInformation("Got view images request...");

        // Read image from event store

        // Return response

        return Ok();
    }

}