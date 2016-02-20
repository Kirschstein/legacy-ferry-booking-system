using System;
using System.Collections.Generic;
using System.Linq;

namespace FerryLegacy
{
    public class PortModel
    {
        public int Id { get; set; }
        public string Name { get; set; }

        private readonly Dictionary<int, TimeSpan> _boatAvailability = new Dictionary<int, TimeSpan>();
        private readonly List<Ferry> _boats = new List<Ferry>();

        public PortModel(Port port)
        {
            Id = port.Id;
            Name = port.Name;
        }

        public void AddBoat(TimeSpan available, Ferry boat)
        {
            if (boat != null)
            {
                _boats.Add(boat);
                _boatAvailability.Add(boat.Id, available);
            }
        }

        public Ferry GetNextAvailable(TimeSpan time)
        {
            var available = _boatAvailability.FirstOrDefault(x => time >= x.Value);
            if (available.Key == 0) return null;
            _boatAvailability.Remove(available.Key);
            var boat = _boats.Single(x => x.Id == available.Key);
            _boats.Remove(boat);
            return boat;
        }
    }
}