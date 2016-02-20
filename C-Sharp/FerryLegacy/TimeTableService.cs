using System;
using System.Collections.Generic;
using System.Linq;

namespace FerryLegacy
{
    public class TimeTableService
    {
        private readonly TimeTables _timeTables;
        private readonly Bookings _bookings;
        private readonly FerryAvailabilityService _ferryService;

        public TimeTableService(TimeTables timeTables, Bookings bookings, FerryAvailabilityService ferryService)
        {
            _timeTables = timeTables;
            _bookings = bookings;
            _ferryService = ferryService;
        }

        public List<TimeTableViewModelRow> GetTimeTable(List<Port> ports)
        {
            var timetables = _timeTables.All();

            var allEntries = timetables.SelectMany(x => x.Entries).OrderBy(x => x.Time).ToList();
            var rows = new List<TimeTableViewModelRow>();

            foreach (var timetable in allEntries)
            {
                var origin = ports.Single(x => x.Id == timetable.OriginId);
                var destination = ports.Single(x => x.Id == timetable.DestinationId);
                var destinationName = destination.Name;
                var originName = origin.Name;
                var ferry = _ferryService.NextFerryAvailableFrom(origin.Id, timetable.Time);
                var arrivalTime = timetable.Time.Add(timetable.JourneyTime);
                var row = new TimeTableViewModelRow
                {
                    DestinationPort = destinationName,
                    FerryName = ferry == null ? "" : ferry.Name,
                    JourneyLength = timetable.JourneyTime.ToString("hh':'mm"),
                    OriginPort = originName,
                    StartTime = timetable.Time.ToString("hh':'mm"),
                    ArrivalTime = arrivalTime.ToString("hh':'mm"),
                };
                rows.Add(row);
            }
            return rows;
        }

        public IEnumerable<AvailableCrossing> GetAvailableCrossings(TimeSpan time, int fromPort, int toPort)
        {
            var ports = new Ports().All();
            var timetables = _timeTables.All();

            var allEntries = timetables.SelectMany(x => x.Entries).OrderBy(x => x.Time).ToList();

            foreach (var timetable in allEntries)
            {
                var origin = ports.Single(x => x.Id == timetable.OriginId);
                var destination = ports.Single(x => x.Id == timetable.DestinationId);
                var ferry = _ferryService.NextFerryAvailableFrom(timetable.OriginId, timetable.Time);

                if (toPort == destination.Id && fromPort == origin.Id)
                {
                    if (timetable.Time >= time)
                    {
                        var bookings = _bookings.All().Where(x => x.JourneyId == timetable.Id);
                        var seatsLeft = ferry.Passengers - bookings.Sum(x => x.Passengers);
                        if (seatsLeft > 0)
                        {
                            yield return new AvailableCrossing
                            {
                                Arrive = timetable.Time.Add(timetable.JourneyTime),
                                FerryName = ferry.Name,
                                SeatsLeft = ferry.Passengers - bookings.Sum(x => x.Passengers),
                                SetOff = timetable.Time,
                                OriginPort = origin.Name,
                                DestinationPort = destination.Name,
                                JourneyId = timetable.Id
                            };
                        }
                    }
                }
            }
        }
    }
}