import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {
    private static Horse horse;

    @BeforeAll
    static void init() {
        horse = new Horse("Name", 2.5, 5.0);
    }

    @Test
    public void ConstructorWithNullName() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.5));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void ConstructorWithEmptyOrWhitespaceName(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 2.5));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void ConstructorWithNegativeSpeed() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Name", -2.5));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void ConstructorWithNegativeDistance() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Name", 2.5, -5.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        assertEquals("Name", horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(2.5, horse.getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(5.0, horse.getDistance());
    }

    @Test
    void getDefaultDistance() {
        Horse localHorse = new Horse("Name", 2.5);
        assertEquals(0, localHorse.getDistance());
    }

    @Test
    void moveRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.4, 0.6, 0.8})
    void moveDistance(double randomValue) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            double distance = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
            horse.move();
            assertEquals(distance, horse.getDistance());
        }
    }
}
