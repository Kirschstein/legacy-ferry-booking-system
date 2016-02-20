using System;

namespace FerryLegacy
{
    public class AvailableCrossing
    {
        public string FerryName { get; set; }
        public string OriginPort { get; set; }
        public string DestinationPort { get; set; }
        public TimeSpan SetOff { get; set; }
        public TimeSpan Arrive { get; set; }
        public int SeatsLeft { get; set; }
        public int JourneyId { get; set; }
    }
}