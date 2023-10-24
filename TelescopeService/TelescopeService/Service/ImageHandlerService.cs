using Dapr.Client;
using TelescopeService.Models;

namespace TelescopeService.Service
{
    public class ImageHandlerService : IImageHandlerService
    {
        const string blobStoreName = "blobstorage";
        const string create = "create";

        private readonly DaprClient _daprClient;

        public ImageHandlerService(DaprClient daprClient) {

            _daprClient = daprClient;

        }

        public async Task<string> SaveImageToBlob(string imageName, byte[] fileContent)
        {
            IReadOnlyDictionary<string, string> metaData = new Dictionary<string, string>()
            {
                { "blobName", $"{imageName}" },
            };

            string encodedFileAsBase64 = Convert.ToBase64String(fileContent);

            Blob blob = await _daprClient.InvokeBindingAsync<object, Blob>(blobStoreName, create, encodedFileAsBase64, metaData);

            return blob.BlobURL;
        }
    }
}
