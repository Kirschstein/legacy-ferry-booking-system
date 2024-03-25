import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookingsTest {

    @Test
    void testBookingsAdd() {
        Bookings bookings = new Bookings();
        Booking booking = new Booking();

        bookings.add(booking);

        assertTrue(bookings.all().contains(booking));
    }

    @Test
    void testBookingsAll() {
        Bookings bookings = new Bookings();
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();

        bookings.add(booking1);
        bookings.add(booking2);

        List<Booking> allBookings = bookings.all();

        assertTrue(allBookings.contains(booking1));
        assertTrue(allBookings.contains(booking2));
        assertEquals(2, allBookings.size());
    }
}