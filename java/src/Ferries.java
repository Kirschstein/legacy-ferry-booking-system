import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ferries {

    private final List<Ferry> ferries = new ArrayList<>();

    public Ferries() {
        String file = "src/Data/ferries.txt";
        try {
            String json = readFile(file);
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

    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
}
