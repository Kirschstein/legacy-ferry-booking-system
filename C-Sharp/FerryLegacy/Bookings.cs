using System.Collections.Generic;

namespace FerryLegacy
{
    public class Bookings
    {
        private readonly List<Booking> _bookings = new List<Booking>();

        public void Add(Booking booking)
        {
            _bookings.Add(booking);
        }

        public IEnumerable<Booking> All()
        {
            return _bookings;
        }
    }
}