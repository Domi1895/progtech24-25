package hu.nye.progtech.connectfour.move.humanPlayer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

class HumanMoveTest {

    @Test
    void testGetMove() {
        // Bemutatjuk a bemeneti adatot
        String simulatedInput = "5\n";

        // Redirectáljuk a System.in-t egy ByteArrayInputStream-re
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Létrehozzuk a HumanMove objektumot
        Scanner scanner = new Scanner(System.in);
        HumanMove humanMove = new HumanMove(scanner);

        // A getMove metódus hívása
        int move = humanMove.getMove();

        // Ellenőrizzük, hogy a visszakapott érték megegyezik a várt 5-tel
        assertEquals(5, move);
    }
}
