using Dapr.Client;
using Microsoft.AspNetCore.Mvc;

namespace TelescopeService.Service
{
    public interface IImageHandlerFactory
    {
        IImageHandlerService Create([FromServices]DaprClient daprClient);
    }
}
