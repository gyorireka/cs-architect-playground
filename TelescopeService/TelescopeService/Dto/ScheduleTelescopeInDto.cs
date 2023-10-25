namespace TelescopeService.Dto
{
    public class ScheduleTelescopeInDto
    {
        public string? id { get; set; }

        public required string requestedByUser { get; set; }

        public required string startDateTime { get; set; }

        public required int endDateTime { get; set; }

        public required string lensType { get; set; }
    }
}