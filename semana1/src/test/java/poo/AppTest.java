package poo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void probarFunciones() {
        // para límite < 1, se debe lanzar una excepción de tipo
        // IllegalArgumentException:
        assertThrows(IllegalArgumentException.class, () -> App.sumatoria(-5));
        // la sumatoria de los 5 primeros naturales debe ser 15
        assertEquals(App.sumatoria(5), 15);
        // para límite < 0, lanzar una excepción de tipo IllegalArgumentException:
        assertThrows(IllegalArgumentException.class, () -> App.factorial(-1));
        // para límite > 44, lanzar una excepción de tipo IllegalArgumentException:
        assertThrows(IllegalArgumentException.class, () -> App.factorial(45));
        // 5! == 120
        assertEquals(App.factorial(5), 120);
        // para el limite < 0, lanzar una excepción de tipo IllegalArgumentException:
        assertThrows(IllegalArgumentException.class, () -> App.fibonacci(0, 1));
        // assertArrayEquals(App.fibonacci(0), fibonacci);
        int[] fibonacci = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 };
        assertArrayEquals(App.fibonacci(10, 1), fibonacci);
    }
}