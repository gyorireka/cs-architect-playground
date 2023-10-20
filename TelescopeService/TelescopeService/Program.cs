using Dapr.Client;
using TelescopeService.Generator;

const string blobStoreName = "blobstorage";
const string create = "create";

var daprClient = new DaprClientBuilder().Build();

string response = Generator.Generate("green").Result;
string downloadUrlFromGenerator = Generator.GetDownloadURL(response);

var lastIndexOfSlash = downloadUrlFromGenerator.LastIndexOf("/");
var imageName = downloadUrlFromGenerator.Substring(lastIndexOfSlash + 1, downloadUrlFromGenerator.Length - lastIndexOfSlash - 1);

Console.WriteLine(downloadUrlFromGenerator);

IReadOnlyDictionary<string, string> metaData = new Dictionary<string, string>()
{
    { "blobName", $"{imageName}" },
};

using (var client = new HttpClient())
{
    byte[] dataBytes = client.GetByteArrayAsync(downloadUrlFromGenerator).Result;
    string encodedFileAsBase64 = Convert.ToBase64String(dataBytes);

    await daprClient.InvokeBindingAsync(blobStoreName, create, encodedFileAsBase64, metaData);
}