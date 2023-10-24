using TelescopeService.Service;

var builder = WebApplication.CreateBuilder(args);

var daprHttpPort = Environment.GetEnvironmentVariable("DAPR_HTTP_PORT") ?? "3602";
var daprGrpcPort = Environment.GetEnvironmentVariable("DAPR_GRPC_PORT") ?? "60002";

builder.Services.AddDaprClient(builder => builder
    .UseHttpEndpoint($"http://localhost:{daprHttpPort}")
    .UseGrpcEndpoint($"http://localhost:{daprGrpcPort}"));

/*builder.Services.AddSingleton<UserManagementService>(
    new ImageHandlerService(DaprClient.CreateInvokeHttpClient(
        "usermanagementservice", $"http://localhost:{daprHttpPort}")));*/

builder.Services.AddControllers().AddDapr();

builder.Services.AddSingleton<IImageHandlerFactory, ImageHandlerFactory>();

var app = builder.Build();

app.MapControllers();

// it finds all the WebAPI actions decorated with the Dapr.Topic attribute and will tell Dapr to create subscriptions for them
app.MapSubscribeHandler();

app.Run("http://localhost:6002");