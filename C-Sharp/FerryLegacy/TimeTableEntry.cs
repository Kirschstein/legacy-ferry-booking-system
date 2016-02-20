using System;

namespace FerryLegacy
{
    public class TimeTableEntry
    {
        public int Id { get; set; }
        public int TimeTableId { get; set; }
        public int OriginId { get; set; }
        public int DestinationId { get; set; }
        public TimeSpan Time { get; set; }
        public TimeSpan JourneyTime { get; set; }
    }
}