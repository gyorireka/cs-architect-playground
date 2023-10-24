using Dapr.Client;
using Microsoft.AspNetCore.Mvc;

namespace TelescopeService.Service
{
    public class ImageHandlerFactory : IImageHandlerFactory
    {
        public IImageHandlerService Create([FromServices]DaprClient daprClient)
        {
            return new ImageHandlerService(daprClient);
        }
    }
}
