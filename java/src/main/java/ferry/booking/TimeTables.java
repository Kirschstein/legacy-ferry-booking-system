package ferry.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import static ferry.booking.Util.readFileToString;

public class TimeTables {

    private final List<TimeTableEntry> entries = new ArrayList<>();

    public TimeTables() {
        try {
            String json = readFileToString("/timetable.txt");
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                TimeTableEntry tte = new TimeTableEntry();
                tte.id = obj.getInt("Id");
                tte.timeTableId = obj.getInt("TimeTableId");
                tte.originId = obj.getInt("OriginId");
                tte.destinationId = obj.getInt("DestinationId");
                tte.time = obj.getLong("Time");
                tte.journeyTime = obj.getLong("JourneyTime");
                entries.add(tte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TimeTable> all() {
        List<TimeTable> result = new ArrayList<TimeTable>();
        List<TimeTableEntry> timeTableEntries = new ArrayList<TimeTableEntry>();
        for (TimeTableEntry entry : entries) {
            if (entry.timeTableId == 1) {
                timeTableEntries.add(entry);
            }
        }
        addOrigin(timeTableEntries, 1);
        TimeTable timeTable = new TimeTable();
        timeTable.id = 1;
        timeTable.entries = timeTableEntries;
        result.add(timeTable);

        List<TimeTableEntry> timeTableEntries2 = new ArrayList<TimeTableEntry>();
        for (TimeTableEntry entry : entries) {
            if (entry.timeTableId == 2) {
                timeTableEntries2.add(entry);
            }
        }
        addOrigin(timeTableEntries2, 2);
        TimeTable timeTable2 = new TimeTable();
        timeTable2.id = 2;
        timeTable2.entries = timeTableEntries2;
        result.add(timeTable2);

        List<TimeTableEntry> timeTableEntries3 = new ArrayList<TimeTableEntry>();
        for (TimeTableEntry entry : entries) {
            if (entry.timeTableId == 3) {
                timeTableEntries3.add(entry);
            }
        }
        addOrigin(timeTableEntries3, 3);
        TimeTable timeTable3 = new TimeTable();
        timeTable3.id = 3;
        timeTable3.entries = timeTableEntries3;
        result.add(timeTable3);
        return result;
    }

    private void addOrigin(List<TimeTableEntry> entries, int origin) {
        for (TimeTableEntry timeTableEntry : entries) {
            timeTableEntry.originId = origin;
        }
    }
}
