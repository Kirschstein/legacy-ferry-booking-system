using System;

namespace FerryLegacy
{
    public class FerryModule
    {
        public static TimeSpan TimeReady(TimeTableEntry timetable, PortModel destination)
        {
            if (timetable == null)
                return new TimeSpan(0,0,0);
            if (destination == null)
                throw new ArgumentNullException("destination");

            var arrivalTime = timetable.Time.Add(timetable.JourneyTime);
            int turnaroundTime = FerryManager.GetFerryTurnaroundTime(destination);
            var timeReady = arrivalTime.Add(TimeSpan.FromMinutes(turnaroundTime));
            return timeReady;
        }
    }
}