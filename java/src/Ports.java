import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ports {
	private final List<Port> _ports = new ArrayList<Port>();

	public Ports() {
		try {
			String json = readFile("src/Data/ports.txt");
			JSONArray arr = new JSONArray(json);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				Port p = new Port();
				p.Id = obj.getInt("Id");
				p.Name = obj.getString("Name");
				_ports.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Port> All() {
		return _ports;
	}

	private static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
}
