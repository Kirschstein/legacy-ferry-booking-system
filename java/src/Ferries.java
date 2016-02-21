import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ferries {
	private final List<Ferry> _ferries = new ArrayList<Ferry>();

	public Ferries()
        {
            String file = "src/Data/ferries.txt";
			try {
				String json = readFile(file);
				JSONArray arr = new JSONArray(json);
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					Ferry f = new Ferry();
					f.Id = obj.getInt("Id");
					f.Name = obj.getString("Name");
					f.Passengers = obj.getInt("Passengers");
					f.Vehicles = obj.getInt("Vehicles");
					f.Weight = obj.getDouble("Weight");
					f.HomePortId = obj.getInt("HomePortId");
					_ferries.add(f);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

	public List<Ferry> All() {
		return _ferries;
	}

	private static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
}
