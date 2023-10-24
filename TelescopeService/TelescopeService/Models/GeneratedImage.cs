namespace TelescopeService.Models
{
    public record struct GeneratedImage
    {
        public GeneratedImage(string _ImageName, byte[] _ImageData)
        {
            ImageName = _ImageName;
            ImageData = _ImageData;
        }
        public string ImageName { get; set; }

        public byte[] ImageData { get; set; }

    }
}
