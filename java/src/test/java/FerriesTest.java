import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FerriesTest {

    @Test
    void testFerriesConstructor() {
        // Arrange
        Ferries ferries = new Ferries();

        // Act
        List<Ferry> allFerries = ferries.all();

        // Assert
        assertFalse(allFerries.isEmpty());
        assertEquals(3, allFerries.size());

        Ferry firstFerry = allFerries.get(0);
        assertEquals(1, firstFerry.id);
        assertEquals("Ferry A", firstFerry.name);
        assertEquals(100, firstFerry.passengers);
        assertEquals(10, firstFerry.vehicles);
        assertEquals(1000.0, firstFerry.weight, 0.01);
        assertEquals(1, firstFerry.homePortId);

        Ferry secondFerry = allFerries.get(1);
        assertEquals(2, secondFerry.id);
        assertEquals("Ferry B", secondFerry.name);
        assertEquals(200, secondFerry.passengers);
        assertEquals(20, secondFerry.vehicles);
        assertEquals(2000.0, secondFerry.weight, 0.01);
        assertEquals(2, secondFerry.homePortId);

        Ferry thirdFerry = allFerries.get(2);
        assertEquals(3, thirdFerry.id);
        assertEquals("Ferry C", thirdFerry.name);
        assertEquals(300, thirdFerry.passengers);
        assertEquals(30, thirdFerry.vehicles);
        assertEquals(3000.0, thirdFerry.weight, 0.01);
        assertEquals(3, thirdFerry.homePortId);
    }

    @Test
    void testFerriesAdd() {
        // Arrange
        Ferries ferries = new Ferries();
        Ferry newFerry = new Ferry(4, "Ferry D", 400, 40, 4000.0, 4);

        // Act
        ferries.add(newFerry);
        List<Ferry> allFerries = ferries.all();

        // Assert
        assertEquals(4, allFerries.size());
        Ferry addedFerry = allFerries.get(3);
        assertEquals(4, addedFerry.id);
        assertEquals("Ferry D", addedFerry.name);
        assertEquals(400, addedFerry.passengers);
        assertEquals(40, addedFerry.vehicles);
        assertEquals(4000.0, addedFerry.weight, 0.01);
        assertEquals(4, addedFerry.homePortId);
    }
}