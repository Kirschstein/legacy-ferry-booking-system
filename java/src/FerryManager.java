import java.util.List;

public class FerryManager {
	public static FerryJourney CreateFerryJourney(List<PortModel> ports,
			TimeTableEntry timetable) {
		if (ports == null)
			return null;

		if (timetable == null)
			return null;

		FerryJourney fj = new FerryJourney();
		for (PortModel port : ports) {
			if (port.Id == timetable.OriginId)
				fj.Origin = port;
			if (port.Id == timetable.DestinationId)
				fj.Destination = port;
		}
		return fj;
	}

	public static void AddFerry(TimeTableEntry timetable, FerryJourney journey) {
		journey.Ferry = journey.Origin.GetNextAvailable(timetable.Time);
	}

	public static int GetFerryTurnaroundTime(PortModel destination) {
		if (destination.Id == 3)
			return 25;
		if (destination.Id == 2)
			return 20;
		return 15;
	}
}
