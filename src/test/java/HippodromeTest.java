import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    @Test
    public void ConstructorWithNullListOfHorses() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void ConstructorWithEmptyListOfHorses() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horses.add(new Horse("Horse-" + i, 2.5));
        }
        assertEquals(horses, new Hippodrome(horses).getHorses());
    }

    @Test
    void move() {
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        new Hippodrome(horses).move();
        horses.forEach(mockHorse -> verify(mockHorse).move());
    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("Horse-1", 2.0, 4.0);
        Horse horse2 = new Horse("Horse-2", 2.5, 5.0);
        Horse horse3 = new Horse("Horse-3", 3.0, 6.0);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        assertEquals(horse3, hippodrome.getWinner());
    }
}
