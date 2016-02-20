using System;
using System.Collections.Generic;
using System.Linq;

namespace FerryLegacy
{
    public class Program
    {
        private static TimeTableService _timeTableService;
        private static JourneyBookingService _bookingService;
        private static Ports _ports;
        private static FerryAvailabilityService _ferryService;

        private static void WireUp()
        {
            var timeTables = new TimeTables();
            var ferries = new Ferries();
            var bookings = new Bookings();
            _ports = new Ports();
            _ferryService = new FerryAvailabilityService(_ports, ferries, timeTables, new PortManager(_ports, ferries));
            _bookingService = new JourneyBookingService(timeTables, bookings, _ferryService);
            _timeTableService = new TimeTableService(timeTables, bookings, _ferryService);
        }

        public static void Main(string[] args)
        {
            Start();
            CommandLoop();
        }

        public static void MainWithTestData()
        {
            Start();
            TestCommands();
        }

        public static void Start()
        {
            WireUp();

            Console.WriteLine("Welcome to the Ferry Finding System");
            Console.WriteLine("=======");
            Console.WriteLine("Ferry Time Table");

            var ports = _ports.All();
            var timeTable = _timeTableService.GetTimeTable(ports);

            DisplayTimetable(ports, timeTable);
        }


        private static void TestCommands()
        {
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

        private static void DisplayTimetable(List<Port> ports, List<TimeTableViewModelRow> rows)
        {
            foreach (var port in ports)
            {
                PrintPortHeader(port.Name);
                var items = rows.Where(x => x.OriginPort == port.Name).OrderBy(x => x.StartTime);

                foreach (var item in items)
                {
                    Console.WriteLine("| {0} | {1} | {2} | {3} | {4} |",
                        item.StartTime.PadRight(8),
                        item.DestinationPort.PadRight(13),
                        item.JourneyLength.PadRight(13),
                        item.FerryName.PadRight(18),
                        item.ArrivalTime.PadRight(8));
                }
            }
        }

        private static void CommandLoop()
        {
            var line = Console.ReadLine() ?? "";
            while (line != "quit")
            {
                DoCommand(line);

                line = (Console.ReadLine() ?? "").ToLower();
            }
        }

        private static void DoCommand(string command)
        {
            if (command.StartsWith("search"))
                Search(command);
            else if (command.StartsWith("book"))
                Book(command);
            else if (command.StartsWith("list ports"))
            {
                Console.WriteLine("Ports:");
                Console.WriteLine("------");
                foreach (var port in _ports.All())
                {
                    Console.WriteLine("{0} - {1}", port.Id, port.Name);
                }
                Console.WriteLine();
            }
            else if (command.StartsWith("list bookings"))
            {
                var bookings = _bookingService.GetAllBookings();
                Console.WriteLine("Bookings:");
                Console.WriteLine("---------");
                foreach (var b in bookings)
                {
                    Console.WriteLine("journey {0} - passengers {1}", b.JourneyId, b.Passengers);
                }
                Console.WriteLine();
            }
            else
            {
                Console.WriteLine("Commands are: [search x y hh:mm] book, or list bookings");
                Console.WriteLine("  search x y hh:mm");
                Console.WriteLine("  book x y");
                Console.WriteLine("  list bookings");
                Console.WriteLine("  list ports");
                Console.WriteLine();
                Console.WriteLine("Book is [book x y]");
                Console.WriteLine("where x - journey id");
                Console.WriteLine("where y - number of passenger");
                Console.WriteLine();
                Console.WriteLine("Search is [search x y hh:mm]");
                Console.WriteLine("where: x - origin port id");
                Console.WriteLine("where: y - destinationg port id");
                Console.WriteLine("where: hh:mm - time to search after");
            }
        }

        private static void Book(string line)
        {
            try
            {
                var parts = line.Split(' ');
                var journeyId = Convert.ToInt32(parts[1]);
                var passengers = Convert.ToInt32(parts[2]);

                if (_bookingService.CanBook(journeyId, passengers))
                {
                    _bookingService.Book(new Booking
                    {
                        JourneyId = journeyId,
                        Passengers = passengers
                    });

                    Console.WriteLine("Booked");
                }
                else
                {
                    Console.WriteLine("Cannot book that journey");
                }
            }
            catch (Exception)
            {
                Console.WriteLine("Book is [book x y]");
                Console.WriteLine("where x - journey id");
                Console.WriteLine("where y - number of passenger");
            }
        }

        private static void Search(string line)
        {
            try
            {
                var parts = line.Split(' ');
                var originPortId = int.Parse(parts[1]);
                var destinationPortId = int.Parse(parts[2]);
                var time = TimeSpan.Parse(parts[3]);

                var search = _timeTableService.GetAvailableCrossings(time, originPortId, destinationPortId);

                foreach (var result in search)
                {
                    Console.WriteLine("[{2}] {0} to {5} -  {1} (JourneyId : {3}, spaces left {4})",
                        result.OriginPort,
                        result.FerryName,
                        result.SetOff.ToString("hh':'mm"),
                        result.JourneyId,
                        result.SeatsLeft,
                        result.DestinationPort);
                }
            }
            catch (Exception)
            {
                Console.WriteLine("Search is [search x y hh:mm]");
                Console.WriteLine("where: x - origin port id");
                Console.WriteLine("where: y - destinationg port id");
                Console.WriteLine("where: hh:mm - time to search after");
            }
        }

        private static void PrintPortHeader(string portName)
        {
            Console.WriteLine();
            Console.WriteLine("Departures from " + portName);
            Console.WriteLine();
            Console.WriteLine(" --------------------------------------------------------------------------");
            Console.WriteLine("| {0} | {1} | {2} | {3} | {4} |",
                "Time".PadRight(8),
                "Destination".PadRight(13),
                "Journey Time".PadRight(13),
                "Ferry".PadRight(18),
                "Arrives".PadRight(8));
            Console.WriteLine(" --------------------------------------------------------------------------");
        }
    }
}
