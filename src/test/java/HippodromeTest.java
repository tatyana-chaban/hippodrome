import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    List<Horse> horses = generatedHorses();
    Hippodrome hippodrome = new Hippodrome(horses);

    @Test
    void ifHorsesIsNullThrowException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void ifHorsesIsBlankThrowException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void testGetHorses() {
        List<Horse> result = hippodrome.getHorses();
        assertArrayEquals(horses.toArray(), result.toArray());
    }

    @Test
    void testMoveAllHorses() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            horses.add(horse);
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse ho : horses) {
            Mockito.verify(ho).move();
        }
    }

    @Test
    void testGetWinner() {
        Horse winner = new Horse("Winner", Integer.MAX_VALUE, Integer.MAX_VALUE);
        horses.add(winner);

        assertEquals(winner, hippodrome.getWinner());
    }

    private List<Horse> generatedHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String name = "Horse" + i;
            horses.add(new Horse(name, i, i));
        }
        return horses;
    }


}