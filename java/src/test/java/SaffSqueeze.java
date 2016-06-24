import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import ferry.booking.Program;
import org.junit.Test;

public class SaffSqueeze {

    @Test
    public void first_saff() {
        String expectedOutput = "Welcome to the Ferry Finding System\n" + "=======\n" + "Ferry Time Table\n" + "\n"
                + "Departures from Port Ellen\n" + "\n"
                + " --------------------------------------------------------------------------\n"
                + "| Time     | Destination   | Journey Time  | Ferry              | Arrives  |\n"
                + " --------------------------------------------------------------------------\n"
                + "| 00:00    | Mos Eisley    | 00:30         | Titanic            | 00:30    |\n"
                + "| 00:10    | Tarsonis      | 00:45         | Hyperion           | 00:55    |\n"
                + "| 00:20    | Mos Eisley    | 00:30         | Millenium Falcon   | 00:50    |\n"
                + "| 00:40    | Mos Eisley    | 00:30         | Golden Hind        | 01:10    |\n"
                + "| 01:00    | Mos Eisley    | 00:30         | Enterprise         | 01:30    |\n"
                + "| 01:10    | Tarsonis      | 00:45         | Hood               | 01:55    |\n"
                + "| 01:20    | Mos Eisley    | 00:30         | Tempest            | 01:50    |\n"
                + "| 01:40    | Mos Eisley    | 00:30         | Dreadnaught        | 02:10    |\n" + "\n"
                + "Departures from Mos Eisley\n" + "\n"
                + " --------------------------------------------------------------------------\n"
                + "| Time     | Destination   | Journey Time  | Ferry              | Arrives  |\n"
                + " --------------------------------------------------------------------------\n"
                + "| 00:10    | Port Ellen    | 00:30         | Enterprise         | 00:40    |\n"
                + "| 00:30    | Port Ellen    | 00:30         | Tempest            | 01:00    |\n"
                + "| 00:40    | Tarsonis      | 00:35         | Black Pearl        | 01:15    |\n"
                + "| 00:50    | Port Ellen    | 00:30         | Titanic            | 01:20    |\n"
                + "| 01:10    | Port Ellen    | 00:30         | Millenium Falcon   | 01:40    |\n"
                + "| 01:30    | Port Ellen    | 00:30         | Golden Hind        | 02:00    |\n"
                + "| 01:40    | Tarsonis      | 00:35         | Defiant            | 02:15    |\n"
                + "| 01:50    | Port Ellen    | 00:30         | Enterprise         | 02:20    |\n" + "\n"
                + "Departures from Tarsonis\n" + "\n"
                + " --------------------------------------------------------------------------\n"
                + "| Time     | Destination   | Journey Time  | Ferry              | Arrives  |\n"
                + " --------------------------------------------------------------------------\n"
                + "| 00:25    | Port Ellen    | 00:45         | Dreadnaught        | 01:10    |\n"
                + "| 00:40    | Mos Eisley    | 00:35         | Defiant            | 01:15    |\n"
                + "| 01:25    | Port Ellen    | 00:45         | Hyperion           | 02:10    |\n"
                + "| 01:40    | Mos Eisley    | 00:35         | Black Pearl        | 02:15    |\n" + "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        Program.start(ps);

        assertEquals(expectedOutput, baos.toString());
    }
}
