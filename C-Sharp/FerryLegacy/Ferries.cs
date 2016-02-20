using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using ServiceStack;

namespace FerryLegacy
{
    public class Ferries
    {
        private readonly List<Ferry> _ferries = new List<Ferry>();

        public Ferries()
        {
            var file = AppDomain.CurrentDomain.BaseDirectory + "\\data\\ferries.txt";
            var reader = new StreamReader(file);
           _ferries = reader.ReadToEnd().FromJson<IEnumerable<Ferry>>().ToList();
        }

        public List<Ferry> All()
        {
            return _ferries;
        }
    }
}