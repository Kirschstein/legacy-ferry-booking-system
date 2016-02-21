import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortModel {
	public int Id;
	public String Name;

	private final Map<Integer, Long> _boatAvailability = new HashMap<Integer, Long>();
	private final List<Ferry> _boats = new ArrayList<Ferry>();

	public PortModel(Port port) {
		Id = port.Id;
		Name = port.Name;
	}

	public void AddBoat(long available, Ferry boat) {
		if (boat != null) {
			_boats.add(boat);
			_boatAvailability.put(boat.Id, available);
		}
	}

	public Ferry GetNextAvailable(long time) {
		for (Map.Entry<Integer, Long> entry : _boatAvailability.entrySet()) {
			if (time >= entry.getValue()) {
				_boatAvailability.remove(entry.getKey());
				for (Ferry boat : _boats)
					if (boat.Id == entry.getKey()) {
						_boats.remove(boat);
						return boat;
					}
				return null;
			}
		}
		return null;
	}
}
