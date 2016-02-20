using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using ServiceStack;

namespace FerryLegacy
{
    public class TimeTables
    {
        private readonly List<TimeTableEntry> _entries = new List<TimeTableEntry>();

        public TimeTables()
        {
            var reader = new StreamReader(AppDomain.CurrentDomain.BaseDirectory + "\\data\\timetable.txt");
            _entries = reader.ReadToEnd().FromJson<List<TimeTableEntry>>();
        }

        public List<TimeTable> All()
        {
            var result = new List<TimeTable>();
            List<TimeTableEntry> timeTableEntries = _entries.Where(x => x.TimeTableId == 1).ToList();
            AddOrigin(timeTableEntries, 1);
            var timeTable = new TimeTable
            {
                Id = 1,
                Entries = timeTableEntries
            };
            result.Add(timeTable);
            List<TimeTableEntry> timeTableEntries2 = _entries.Where(x => x.TimeTableId == 2).ToList();
            AddOrigin(timeTableEntries2, 2);
            var timeTable2 = new TimeTable
            {
                Id = 2,
                Entries = timeTableEntries2
            };
            result.Add(timeTable2);
            List<TimeTableEntry> timeTableEntries3 = _entries.Where(x => x.TimeTableId == 3).ToList();
            AddOrigin(timeTableEntries3, 3);
            var timeTable3 = new TimeTable
            {
                Id = 3,
                Entries = timeTableEntries3
            };
            result.Add(timeTable3);
            return result;
        }

        private void AddOrigin(List<TimeTableEntry> entries, int origin )
        {
            foreach (var timeTableEntry in entries)
            {
                timeTableEntry.OriginId = origin;
            }
        }
    }
}