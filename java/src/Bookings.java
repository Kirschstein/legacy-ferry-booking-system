import java.util.ArrayList;
import java.util.List;

public class Bookings {
	private final List<Booking> _bookings = new ArrayList<Booking>();

	public void Add(Booking booking) {
		_bookings.add(booking);
	}

	public List<Booking> All() {
		return _bookings;
	}
}
