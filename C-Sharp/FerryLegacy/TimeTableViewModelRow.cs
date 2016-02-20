namespace FerryLegacy
{
    public class TimeTableViewModelRow
    {
        public string FerryName { get; set; }
        public string DestinationPort { get; set; }
        public string OriginPort { get; set; }
        public string StartTime { get; set; }
        public string JourneyLength { get; set; }
        public string ArrivalTime { get; set; }
        public int JourneyId { get; set; }
    }
}