namespace TelescopeService.Dto
{
    public class ScheduleTelescopeInDto
    {
        public string? Id { get; set; }

        public required string RequestedByUser { get; set; }

        public required string StartDateTime { get; set; }

        public required int EndDateTime { get; set; }

        public required string LensType { get; set; }
    }
}