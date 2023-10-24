namespace TelescopeService.Events
{
    public record struct ImageCreated(string UserName, string BlobUrl, DateTime Date);

}
