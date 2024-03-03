package journey;

import booking.Booking;
import booking.Bookings;
import ferry.Ferry;
import ferry.FerryAvailabilityService;
import timetable.TimeTable;
import timetable.TimeTableEntry;
import timetable.TimeTables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JourneyBookingService {

    private TimeTables timeTables;
    private Bookings bookings;
    private final FerryAvailabilityService ferryService;

    public JourneyBookingService(TimeTables timeTables, Bookings bookings, FerryAvailabilityService ferryService) {
        this.timeTables = timeTables;
        this.bookings = bookings;
        this.ferryService = ferryService;
    }

    public boolean canBook(int journeyId, int passengers) {
        List<TimeTable> timetables = timeTables.all();
        List<TimeTableEntry> allEntries = new ArrayList<TimeTableEntry>();
        for (TimeTable tt : timetables) {
            allEntries.addAll(tt.entries);
        }
        Collections.sort(allEntries, new Comparator<TimeTableEntry>() {

            @Override
            public int compare(TimeTableEntry tte1, TimeTableEntry tte2) {
                return Long.compare(tte1.time, tte2.time);
            }
        });

        for (TimeTableEntry timetable : allEntries) {
            Ferry ferry = ferryService.nextFerryAvailableFrom(timetable.originId, timetable.time);

            if (timetable.id == journeyId) {
                List<Booking> journeyBookings = new ArrayList<>();
                for (Booking x : bookings.all()) {
                    if (x.journeyId == journeyId) {
                        journeyBookings.add(x);
                    }
                }
                int pax = 0;
                for (Booking x : bookings.all()) {
                    pax += x.passengers;
                }
                int seatsLeft = ferry.getPassengers() - pax;
                return seatsLeft >= passengers;
            }
        }
        return false;
    }

    public void book(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getAllBookings() {
        return bookings.all();
    }
}
