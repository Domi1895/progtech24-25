package hu.nye.progtech.connectfour.player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

class ConsoleInputProviderTest {

    @Test
    void testGetInput() {
        // A szimulált bemeneti adat
        String simulatedInput = "Hello, World!";

        // Redirectáljuk a System.in-t a szimulált bemenetre
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Létrehozzuk a ConsoleInputProvider objektumot
        Scanner scanner = new Scanner(System.in);
        ConsoleInputProvider consoleInputProvider = new ConsoleInputProvider(scanner);

        // A getInput metódus hívása
        String input = consoleInputProvider.getInput();

        // Ellenőrizzük, hogy a visszakapott bemenet megegyezik a várt értékkel
        assertEquals(simulatedInput, input);
    }
}
