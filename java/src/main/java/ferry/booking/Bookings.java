package ferry.booking;

import java.util.ArrayList;
import java.util.List;

public class Bookings {

    private final List<Booking> bookings = new ArrayList<>();

    public void add(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> all() {
        return bookings;
    }
}
