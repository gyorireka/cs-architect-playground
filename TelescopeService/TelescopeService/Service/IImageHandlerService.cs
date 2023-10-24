namespace TelescopeService.Service
{
    public interface IImageHandlerService
    {
        public Task<string> SaveImageToBlob(string imageName, byte[] fileContent);
    }
}
