package ferry.booking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static ferry.booking.Util.readFileToString;

public class Ferries {

    private final List<Ferry> ferries = new ArrayList<>();

    public Ferries() {
        String file = "/ferries.txt";
        try {
            String json = readFileToString(file);
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Ferry f = new Ferry();
                f.id = obj.getInt("Id");
                f.name = obj.getString("Name");
                f.passengers = obj.getInt("Passengers");
                f.vehicles = obj.getInt("Vehicles");
                f.weight = obj.getDouble("Weight");
                f.homePortId = obj.getInt("HomePortId");
                ferries.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ferry> all() {
        return ferries;
    }

}
