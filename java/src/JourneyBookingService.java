import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JourneyBookingService {
	private TimeTables _timeTables;
	private Bookings _bookings;
	private final FerryAvailabilityService _ferryService;

	public JourneyBookingService(TimeTables timeTables, Bookings bookings,
			FerryAvailabilityService ferryService) {
		_timeTables = timeTables;
		_bookings = bookings;
		_ferryService = ferryService;
	}

	public boolean CanBook(int journeyId, int passengers)
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

            for (TimeTableEntry timetable : allEntries)
            {
                Ferry ferry = _ferryService.NextFerryAvailableFrom(timetable.OriginId, timetable.Time);

                if (timetable.Id == journeyId)
                {
                    List<Booking> bookings = new ArrayList<Booking>();
                    for (Booking x : _bookings.All())
                    	if (x.JourneyId == journeyId)
                    		bookings.add(x);
                    int pax = 0;
                    for (Booking x : _bookings.All())
                    	pax += x.Passengers;
                    int seatsLeft = ferry.Passengers - pax;
                    return seatsLeft >= passengers;
                }
            }
            return false;
        }

	public void Book(Booking booking) {
		_bookings.Add(booking);
	}

	public List<Booking> GetAllBookings() {
		return _bookings.All();
	}
}
