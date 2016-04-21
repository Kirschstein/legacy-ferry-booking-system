import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimeTableService {
	private final TimeTables _timeTables;
	private final Bookings _bookings;
	private final FerryAvailabilityService _ferryService;

	public TimeTableService(TimeTables timeTables, Bookings bookings,
			FerryAvailabilityService ferryService) {
		_timeTables = timeTables;
		_bookings = bookings;
		_ferryService = ferryService;
	}

	public List<TimeTableViewModelRow> GetTimeTable(List<Port> ports)
        {
            List<TimeTable> timetables = _timeTables.All();
            List<TimeTableEntry> allEntries = new ArrayList<TimeTableEntry>();
    		for (TimeTable tt : timetables)
    			allEntries.addAll(tt.Entries);
    		Collections.sort(allEntries, new Comparator<TimeTableEntry>() {
    			@Override
    			public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
    				return Long.compare(tte1.Time, tte2.Time);
    			}
    		});

            List<TimeTableViewModelRow> rows = new ArrayList<TimeTableViewModelRow>();

            for (TimeTableEntry timetable : allEntries)
            {
                Port origin = null;
                Port destination = null;
                for (Port x : ports) {
                	if (x.Id == timetable.OriginId)
                		origin = x;
                	if (x.Id == timetable.DestinationId)
                		destination = x;
                }
                String destinationName = destination.Name;
                String originName = origin.Name;
                Ferry ferry = _ferryService.NextFerryAvailableFrom(origin.Id, timetable.Time);
                long arrivalTime = timetable.Time + timetable.JourneyTime;
                TimeTableViewModelRow row = new TimeTableViewModelRow();
                row.DestinationPort = destinationName;
           		row.FerryName = ferry == null ? "" : ferry.Name;
   				row.JourneyLength = String.format("%02d:%02d", timetable.JourneyTime / 60, timetable.JourneyTime % 60);
   				row.OriginPort = originName;
                row.StartTime = String.format("%02d:%02d", timetable.Time / 60, timetable.Time % 60);
                row.ArrivalTime = String.format("%02d:%02d", arrivalTime / 60, arrivalTime % 60);
                rows.add(row);
            }
            return rows;
        }

	public List<AvailableCrossing> GetAvailableCrossings(long time, int fromPort, int toPort)
        {
            List<Port> ports = new Ports().All();
            List<TimeTable> timetables = _timeTables.All();
            List<TimeTableEntry> allEntries = new ArrayList<TimeTableEntry>();
    		for (TimeTable tt : timetables)
    			allEntries.addAll(tt.Entries);
    		Collections.sort(allEntries, new Comparator<TimeTableEntry>() {
    			@Override
    			public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
    				return Long.compare(tte1.Time, tte2.Time);
    			}
    		});

            List<AvailableCrossing>result = new ArrayList<AvailableCrossing>();

            for (TimeTableEntry timetable : allEntries)
            {
                Port origin = null;
                Port destination = null;
                for (Port x : ports) {
                	if (x.Id == timetable.OriginId)
                		origin = x;
                	if (x.Id == timetable.DestinationId)
                		destination = x;
                }
                Ferry ferry = _ferryService.NextFerryAvailableFrom(timetable.OriginId, timetable.Time);

                if (toPort == destination.Id && fromPort == origin.Id)
                {
                    if (timetable.Time >= time)
                    {
                        List<Booking> bookings = new ArrayList<Booking>();
                        for (Booking x : _bookings.All())
                        	if (x.JourneyId == timetable.Id)
                        		bookings.add(x);
                        int pax = 0;
                        for (Booking x : bookings)
                        	pax += x.Passengers;
                        int seatsLeft = ferry.Passengers - pax;
                        if (seatsLeft > 0)
                        {
                        	AvailableCrossing crossing = new AvailableCrossing();
                        	crossing.Arrive = timetable.Time + timetable.JourneyTime;
                        	crossing.FerryName = ferry.Name;
                            int pax2 = 0;
                            for (Booking x : bookings)
                            	pax2 += x.Passengers;
                        	crossing.SeatsLeft = ferry.Passengers - pax2;
                        	crossing.SetOff = timetable.Time;
                        	crossing.OriginPort = origin.Name;
                        	crossing.DestinationPort = destination.Name;
                        	crossing.JourneyId = timetable.Id;
                            result.add(crossing);
                        }
                    }
                }
            }
            return result;
        }
}
