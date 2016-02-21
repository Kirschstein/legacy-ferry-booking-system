import java.util.ArrayList;
import java.util.List;

public class PortManager {
	private final Ports _ports;
	private final Ferries _ferries;

	public PortManager(Ports ports, Ferries ferries) {
		_ports = ports;
		_ferries = ferries;
	}

	public List<PortModel> PortModels() {
		List<PortModel> ports = new ArrayList<PortModel>();
		for (Port port : _ports.All())
			ports.add(new PortModel(port));
		for (Ferry ferry : _ferries.All()) {
			for (PortModel port : ports)
				if (port.Id == ferry.HomePortId)
					port.AddBoat(10, ferry);
		}
		return ports;
	}
}
