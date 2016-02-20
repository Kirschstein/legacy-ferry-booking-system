namespace FerryLegacy
{
    public class Ferry
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public int Passengers { get; set; }
        public int Vehicles { get; set; }
        public decimal Weight { get; set; }
        public int HomePortId { get; set; }

        public Ferry()
        {
            HomePortId = 1;
        }
    }
}