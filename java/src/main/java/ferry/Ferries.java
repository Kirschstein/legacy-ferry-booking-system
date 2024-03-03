package ferry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static util.Util.readFileToString;

public class Ferries {

    private final List<Ferry> ferries = new ArrayList<>();

    public Ferries() {
        loadFerriesFromFile("/ferries.txt");
    }

    private void loadFerriesFromFile(String filePath) {
        try {
            String json = readFileToString(filePath);
            parseFerriesFromJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseFerriesFromJson(String json) {
        JSONArray arr = new JSONArray(json);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Ferry f = createFerryFromJson(obj);
            ferries.add(f);
        }
    }

    private Ferry createFerryFromJson(JSONObject obj) {
        Ferry f = new Ferry();
        f.setId(obj.getInt("Id"));
        f.setName(obj.getString("Name"));
        f.setPassengers(obj.getInt("Passengers"));
        f.setVehicles(obj.getInt("Vehicles"));
        f.setWeight(obj.getDouble("Weight"));
        f.setHomePortId(obj.getInt("HomePortId"));
        return f;
    }

    public List<Ferry> all() {
        return ferries;
    }
}
