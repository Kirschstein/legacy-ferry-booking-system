using System;
using System.Collections.Generic;
using System.IO;
using ServiceStack;

namespace FerryLegacy
{
    public class Ports
    {
        private readonly List<Port> _ports = new List<Port>();

        public Ports()
        {
			var reader = new StreamReader(Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "data", "ports.txt"));
            _ports = reader.ReadToEnd().FromJson<List<Port>>();
        }

        public List<Port> All()
        {
            return _ports;
        }
    }
}