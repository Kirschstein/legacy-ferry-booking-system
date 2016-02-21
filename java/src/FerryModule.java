public class FerryModule {
	public static long TimeReady(TimeTableEntry timetable, PortModel destination) {
		if (timetable == null)
			return 0;
		if (destination == null)
			throw new NullPointerException("destination");

		long arrivalTime = timetable.Time + timetable.JourneyTime;
		int turnaroundTime = FerryManager.GetFerryTurnaroundTime(destination);
		long timeReady = arrivalTime + turnaroundTime;
		return timeReady;
	}
}
