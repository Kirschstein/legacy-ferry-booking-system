package ferry.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortModel {

    public int id;
    public String name;

    private final Map<Integer, Long> boatAvailability = new HashMap<>();
    private final List<Ferry> boats = new ArrayList<>();

    public PortModel(Port port) {
        id = port.id;
        name = port.name;
    }

    public void addBoat(long available, Ferry boat) {
        if (boat != null) {
            boats.add(boat);
            boatAvailability.put(boat.id, available);
        }
    }

    public Ferry getNextAvailable(long time) {
        for (Map.Entry<Integer, Long> entry : boatAvailability.entrySet()) {
            if (time >= entry.getValue()) {
                boatAvailability.remove(entry.getKey());
                for (Ferry boat : boats) {
                    if (boat.id == entry.getKey()) {
                        boats.remove(boat);
                        return boat;
                    }
                }
                return null;
            }
        }
        return null;
    }
}
