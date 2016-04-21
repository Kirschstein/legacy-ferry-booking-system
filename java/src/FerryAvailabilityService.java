import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FerryAvailabilityService {
	private final Ports _ports;
	private final Ferries _ferries;
	private final TimeTables _timeTables;
	private final PortManager _portManager;

	public FerryAvailabilityService(Ports ports, Ferries ferries,
			TimeTables timeTables, PortManager portManager) {
		_ports = ports;
		_ferries = ferries;
		_timeTables = timeTables;
		_portManager = portManager;
	}

	public Ferry NextFerryAvailableFrom(int portId, long time) {
		List<PortModel> ports = _portManager.PortModels();
		List<TimeTableEntry> allEntries = new ArrayList<TimeTableEntry>();
		for (TimeTable tt : _timeTables.All())
			allEntries.addAll(tt.Entries);
		Collections.sort(allEntries, new Comparator<TimeTableEntry>() {
			@Override
			public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
				return Long.compare(tte1.Time, tte2.Time);
			}
		});

		for (TimeTableEntry entry : allEntries) {
			FerryJourney ferry = FerryManager.CreateFerryJourney(ports, entry);
			if (ferry != null) {
				BoatReady(entry, ferry.Destination, ferry);
			}
			if (entry.OriginId == portId) {
				if (entry.Time >= time) {
					if (ferry != null) {
						return ferry.Ferry;
					}
				}
			}
		}

		return null;
	}

	private static void BoatReady(TimeTableEntry timetable,
			PortModel destination, FerryJourney ferryJourney) {
		if (ferryJourney.Ferry == null)
			FerryManager.AddFerry(timetable, ferryJourney);

		Ferry ferry = ferryJourney.Ferry;

		long time = FerryModule.TimeReady(timetable, destination);
		destination.AddBoat(time, ferry);
	}
}
