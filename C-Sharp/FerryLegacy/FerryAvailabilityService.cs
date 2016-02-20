using System;
using System.Collections.Generic;
using System.Linq;

namespace FerryLegacy
{
    public class FerryAvailabilityService
    {
        private readonly Ports _ports;
        private readonly Ferries _ferries;
        private readonly TimeTables _timeTables;
        private readonly PortManager _portManager;

        public FerryAvailabilityService(Ports ports, Ferries ferries, TimeTables timeTables, PortManager portManager)
        {
            _ports = ports;
            _ferries = ferries;
            _timeTables = timeTables;
            _portManager = portManager;
        }

        public Ferry NextFerryAvailableFrom(int portId, TimeSpan time)
        {
            var ports = _portManager.PortModels();
            var allEntries = _timeTables.All().SelectMany(x => x.Entries).OrderBy(x => x.Time).ToList();

            foreach (var entry in allEntries)
            {
                var ferry = FerryManager.CreateFerryJourney(ports, entry);
                if (ferry != null)
                {
                    BoatReady(entry, ferry.Destination, ferry);
                }
                if (entry.OriginId == portId)
                {
                    if (entry.Time >= time)
                    {
                        if (ferry != null)
                        {
                            return ferry.Ferry;
                        }
                    }
                }
            }

            return null;
        }

        private static void BoatReady(TimeTableEntry timetable, PortModel destination, FerryJourney ferryJourney)
        {
            if (ferryJourney.Ferry == null)
                FerryManager.AddFerry(timetable, ferryJourney);

            var ferry = ferryJourney.Ferry;

            var time = FerryModule.TimeReady(timetable, destination);
            destination.AddBoat(time, ferry);
        }
    }
}
