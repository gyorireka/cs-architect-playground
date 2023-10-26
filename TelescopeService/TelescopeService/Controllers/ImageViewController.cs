using Dapr;
using Dapr.Client;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using TelescopeService.Dto;
using TelescopeService.Events;

namespace TelescopeService.Controllers;

[ApiController]
[Route("")]
public class ImageViewController : ControllerBase
{
    private readonly ILogger<ImageViewController> _logger;

    private const string EventStoreName = "statestore";
    private const string AnalysisRequestTopic = "analysisRequest";
    private const string AnalysisResponseTopic = "imageListResult";
    private const string PubSub = "pubsub";

    public ImageViewController(ILogger<ImageViewController> logger, DaprClient daprClient)
    {
        _logger = logger;
    }

    [Topic(PubSub, AnalysisRequestTopic, "deadletters", false)]
    [Route("/requestImageList")]
    [HttpPost()]
    public async Task<ActionResult> RequestImageList(CloudEvent<AnalysisRequestInDto> ImageListEvent, [FromServices] DaprClient daprClient)
    {
        _logger.LogInformation("Got view images request...");

        AnalysisRequestInDto analysisRequest = ImageListEvent.Data;

        var query = "{" +
                    "\"filter\": {" +
                        "\"EQ\": { \"userName\": \"" + analysisRequest.requestedByUser + "\" }" +
                    "}}";

        var queryResponse = await daprClient.QueryStateAsync<ImageCreated>(EventStoreName, query);

        var imageURLList = new List<string>();

        foreach (var imageCreated in queryResponse.Results)
        {
            _logger.LogInformation(imageCreated.Data.BlobUrl);
            imageURLList.Add(imageCreated.Data.BlobUrl);
        }

        await daprClient.PublishEventAsync(PubSub, AnalysisResponseTopic, imageURLList);

        return Ok();
    }

    [Route("images")]
    [HttpGet()]
    public async Task<ActionResult> GetImageList(string userName, [FromServices] DaprClient daprClient)
    {
        _logger.LogInformation($"Got return images list request for user {userName}...");

        var query = "{" +
                    "\"filter\": {" +
                        "\"EQ\": { \"userName\": \"" + userName + "\" }" +
                    "}}";

        var queryResponse = await daprClient.QueryStateAsync<ImageCreated>(EventStoreName, query);

        var imageURLList = new List<string>();

        foreach (var imageCreated in queryResponse.Results)
        {
            _logger.LogInformation(imageCreated.Data.BlobUrl);
            imageURLList.Add(imageCreated.Data.BlobUrl);
        }

        return Ok(imageURLList);
    }

}