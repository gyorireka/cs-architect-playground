namespace TelescopeService.Dto
{
    public class AnalysisRequestInDto
    {
        public required string requestedByUser { get; set; }

        public required string startDateTime { get; set; }

        public required int endDateTime { get; set; }
    }
}