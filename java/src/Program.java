import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Program {
	public static TimeTableService _timeTableService;
	private static JourneyBookingService _bookingService;
	public static Ports _ports;
	private static FerryAvailabilityService _ferryService;
	private static PrintStream out;

	public static void WireUp() {
		TimeTables timeTables = new TimeTables();
		Ferries ferries = new Ferries();
		Bookings bookings = new Bookings();
		_ports = new Ports();
		_ferryService = new FerryAvailabilityService(_ports, ferries,
				timeTables, new PortManager(_ports, ferries));
		_bookingService = new JourneyBookingService(timeTables, bookings,
				_ferryService);
		_timeTableService = new TimeTableService(timeTables, bookings,
				_ferryService);
	}

	public static void main(String[] args) {
		Start(System.out);
		CommandLoop();
	}

	public static void MainWithTestData(PrintStream ps) {
		Start(ps);
		TestCommands();
	}

	public static void Start(PrintStream ps) {
		out = ps;
		WireUp();

		out.println("Welcome to the Ferry Finding System");
		out.println("=======");
		out.println("Ferry Time Table");

		List<Port> ports = _ports.All();
		List<TimeTableViewModelRow> timeTable = _timeTableService
				.GetTimeTable(ports);

		DisplayTimetable(ports, timeTable);
	}

	private static void TestCommands() {
		DoCommand("help");
		DoCommand("list ports");
		DoCommand("search 2 3 00:00");
		DoCommand("search 2 3 00:00");
		DoCommand("book 10 2");
		DoCommand("search 2 3 00:00");
		DoCommand("book 10 10");
		DoCommand("book 10 1");
		DoCommand("search 1 2 01:00");
		DoCommand("book 4 2");
		DoCommand("book 6 8");
		DoCommand("search 1 2 01:00");
		DoCommand("search 1 3 01:00");
		DoCommand("search 1 3 01:30");
		DoCommand("book 5 16");
		DoCommand("book 16 16");
		DoCommand("search 1 3 00:00");
		DoCommand("list bookings");
	}

	public static void DisplayTimetable(List<Port> ports, List<TimeTableViewModelRow> rows)
        {
            for (Port port : ports)
            {
                PrintPortHeader(port.Name);
                List<TimeTableViewModelRow> items = new ArrayList<TimeTableViewModelRow>();
                for (TimeTableViewModelRow x : rows)
                	if (x.OriginPort.equals(port.Name))
                		items.add(x);
        		Collections.sort(items, new Comparator<TimeTableViewModelRow>() {
        			@Override
        			public int compare(TimeTableViewModelRow tt1, TimeTableViewModelRow tt2) {
        				return tt1.StartTime.compareTo(tt2.StartTime);
        			}
        		});

                for (TimeTableViewModelRow item : items)
                {
                    out.printf("| %-8s | %-13s | %-13s | %-18s | %-8s |",
                        item.StartTime, item.DestinationPort, item.JourneyLength,
                        item.FerryName, item.ArrivalTime);
                    out.println();
                }
            }
        }

	private static void CommandLoop() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			line = br.readLine();
			while (line != "quit") {
				DoCommand(line);

				line = (br.readLine()).toLowerCase();
			}
		} catch (IOException e) {
		}
	}

	private static void DoCommand(String command) {
		if (command.startsWith("search"))
			Search(command);
		else if (command.startsWith("book"))
			Book(command);
		else if (command.startsWith("list ports")) {
			out.println("Ports:");
			out.println("------");
			for (Port port : _ports.All()) {
				out.printf("%d - %s", port.Id, port.Name);
				out.println();
			}
			out.println();
		} else if (command.startsWith("list bookings")) {
			List<Booking> bookings = _bookingService.GetAllBookings();
			out.println("Bookings:");
			out.println("---------");
			for (Booking b : bookings) {
				out.printf("journey %d - passengers %d", b.JourneyId,
						b.Passengers);
			}
			out.println();
		} else {
			out.println("Commands are: [search x y hh:mm] book, or list bookings");
			out.println("  search x y hh:mm");
			out.println("  book x y");
			out.println("  list bookings");
			out.println("  list ports");
			out.println();
			out.println("Book is [book x y]");
			out.println("where x - journey id");
			out.println("where y - number of passenger");
			out.println();
			out.println("Search is [search x y hh:mm]");
			out.println("where: x - origin port id");
			out.println("where: y - destinationg port id");
			out.println("where: hh:mm - time to search after");
		}
	}

	private static void Book(String line) {
		try {
			String parts[] = line.split(" ");
			int journeyId = Integer.parseInt(parts[1]);
			int passengers = Integer.parseInt(parts[2]);

			if (_bookingService.CanBook(journeyId, passengers)) {
				Booking booking = new Booking();
				booking.JourneyId = journeyId;
				booking.Passengers = passengers;
				_bookingService.Book(booking);

				out.println("Booked");
			} else {
				out.println("Cannot book that journey");
			}
		} catch (Exception e) {
			out.println("Book is [book x y]");
			out.println("where x - journey id");
			out.println("where y - number of passenger");
		}
	}

	private static void Search(String line) {
		try {
			String parts[] = line.split(" ");
			int originPortId = Integer.parseInt(parts[1]);
			int destinationPortId = Integer.parseInt(parts[2]);
			String mins[] = parts[3].split(":");
			long time = Long.parseLong(mins[0])*60 + Long.parseLong(mins[1]);

			List<AvailableCrossing> search = _timeTableService
					.GetAvailableCrossings(time, originPortId,
							destinationPortId);

			for (AvailableCrossing result : search) {
				out.printf(
						"[%02d:%02d] %s to %s -  %s (JourneyId : %d, spaces left %d)",
						result.SetOff / 60, result.SetOff % 60,
						result.OriginPort, result.DestinationPort,
						result.FerryName,
						result.JourneyId, result.SeatsLeft
						);
				out.println();
			}
		} catch (Exception e) {
			out.println("Search is [search x y hh:mm]");
			out.println("where: x - origin port id");
			out.println("where: y - destinationg port id");
			out.println("where: hh:mm - time to search after");
		}
	}

	private static void PrintPortHeader(String portName) {
		out.println();
		out.println("Departures from " + portName);
		out.println();
		out.println(" --------------------------------------------------------------------------");
		out.printf("| %-8s | %-13s | %-13s | %-18s | %-8s |", "Time",
				"Destination", "Journey Time", "Ferry", "Arrives");
		out.println();
		out.println(" --------------------------------------------------------------------------");
	}
}
