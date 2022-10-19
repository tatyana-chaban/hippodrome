import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    Horse horse = new Horse("Horse", 1, 1);
    Horse horseDefault = new Horse("Horse", 1);

    @Test
    void ifNameIsNullThrowException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0, 0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest()
    @ValueSource(strings = {"", " ", "    "})
    void ifNameIsBlankThrowException(String str) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(str, 0, 0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void ifSecondArgIsNegativeThrowException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -1, 0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void ifThirdArgIsNegativeThrowException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 0, -1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void testGetName() {
        String expected = "Horse";
        String result = horse.getName();

        assertEquals(expected, result);
    }

    @Test
    void testGetSpeed() {
        double expected = 1;
        double result = horse.getSpeed();

        assertEquals(expected, result);
    }

    @Test
    void testGetDistance() {
        double expected = 1;
        double result = horse.getDistance();

        assertEquals(expected, result);
    }

    @Test
    void testGetDistanceDefault() {
        double expected = 0;
        double result = horseDefault.getDistance();

        assertEquals(expected, result);
    }

    @Test
    void testVerifyGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.8})
    void testMove(double num) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(num);

            double expected = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
            horse.move();
            double result = horse.getDistance();

            assertEquals(expected, result);
        }
    }
}
