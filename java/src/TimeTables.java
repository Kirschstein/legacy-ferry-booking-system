import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TimeTables {
	private final List<TimeTableEntry> _entries = new ArrayList<TimeTableEntry>();

	public TimeTables() {
		try {
			String json = readFile("src/Data/timetable.txt");
			JSONArray arr = new JSONArray(json);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				TimeTableEntry tte = new TimeTableEntry();
				tte.Id = obj.getInt("Id");
				tte.TimeTableId = obj.getInt("TimeTableId");
				tte.OriginId = obj.getInt("OriginId");
				tte.DestinationId = obj.getInt("DestinationId");
				tte.Time = obj.getLong("Time");
				tte.JourneyTime = obj.getLong("JourneyTime");
				_entries.add(tte);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<TimeTable> All() {
		List<TimeTable> result = new ArrayList<TimeTable>();
		List<TimeTableEntry> timeTableEntries = new ArrayList<TimeTableEntry>();
		for (TimeTableEntry entry : _entries)
			if (entry.TimeTableId == 1)
				timeTableEntries.add(entry);
		AddOrigin(timeTableEntries, 1);
		TimeTable timeTable = new TimeTable();
		timeTable.Id = 1;
		timeTable.Entries = timeTableEntries;
		result.add(timeTable);

		List<TimeTableEntry> timeTableEntries2 = new ArrayList<TimeTableEntry>();
		for (TimeTableEntry entry : _entries)
			if (entry.TimeTableId == 2)
				timeTableEntries2.add(entry);
		AddOrigin(timeTableEntries2, 2);
		TimeTable timeTable2 = new TimeTable();
		timeTable2.Id = 2;
		timeTable2.Entries = timeTableEntries2;
		result.add(timeTable2);

		List<TimeTableEntry> timeTableEntries3 = new ArrayList<TimeTableEntry>();
		for (TimeTableEntry entry : _entries)
			if (entry.TimeTableId == 3)
				timeTableEntries3.add(entry);
		AddOrigin(timeTableEntries3, 3);
		TimeTable timeTable3 = new TimeTable();
		timeTable3.Id = 3;
		timeTable3.Entries = timeTableEntries3;
		result.add(timeTable3);
		return result;
	}

	private void AddOrigin(List<TimeTableEntry> entries, int origin) {
		for (TimeTableEntry timeTableEntry : entries) {
			timeTableEntry.OriginId = origin;
		}
	}

	private static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
}
