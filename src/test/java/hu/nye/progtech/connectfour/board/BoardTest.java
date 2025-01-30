package hu.nye.progtech.connectfour.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private GameBoard gameBoardMock;
    private Board board;

    @BeforeEach
    void setUp() {
        // Mock a GameBoard osztály
        gameBoardMock = mock(GameBoard.class);

        // Az üres táblát visszaadjuk, a teszteléshez szükséges
        States[][] grid = new States[6][7]; // 6 sor, 7 oszlop
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grid[i][j] = States.EMPTY; // Minden cella üres
            }
        }

        when(gameBoardMock.getGrid()).thenReturn(grid);

        // Board példányosítása a GameBoard mock-jával
        board = new Board(gameBoardMock);
    }

    @Test
    void testDropPieceSuccess() {
        // Teszteljük, hogy a lépés sikeres, ha az oszlop nincs tele
        boolean result = board.dropPiece(0, States.RED);
        assertTrue(result, "A lépésnek sikeresnek kell lennie");

        // Ellenőrizzük, hogy a korong valóban a helyére került
        States[][] grid = gameBoardMock.getGrid();
        assertEquals(States.RED, grid[5][0], "A korongot a legalsó helyre kell dobni");
    }

    @Test
    void testDropPieceColumnFull() {
        // Töltsük fel az oszlopot (0-ás oszlop)
        States[][] grid = gameBoardMock.getGrid();
        for (int i = 0; i < 6; i++) {
            grid[i][0] = States.RED; // Az oszlop tele van RED korongokkal
        }

        // Most próbáljunk meg egy új lépést tenni ebbe az oszlopba
        boolean result = board.dropPiece(0, States.YELLOW);
        assertFalse(result, "Ha az oszlop tele van, a lépésnek nem sikerülhet");
    }

    @Test
    void testDropPieceInvalidColumn() {
        // Túl magas oszlopot próbálunk (7-es oszlop nem létezik)
        boolean result = board.dropPiece(7, States.RED);
        assertFalse(result, "Az érvénytelen oszlopba tett lépés nem lehet sikeres");

        // Túl alacsony oszlopot próbálunk (negatív index)
        result = board.dropPiece(-1, States.RED);
        assertFalse(result, "A negatív oszlopba tett lépés nem lehet sikeres");
    }

    @Test
    void testDropPieceMultipleMoves() {
        // Töltsük fel az oszlopot lépésenként
        board.dropPiece(1, States.RED); // 1. lépés
        board.dropPiece(1, States.YELLOW); // 2. lépés

        States[][] grid = gameBoardMock.getGrid();

        // Ellenőrizzük, hogy az első és második helyen megfelelő korongok találhatók
        assertEquals(States.RED, grid[5][1], "Az első korongot a 6. sorba kell helyezni.");
        assertEquals(States.YELLOW, grid[4][1], "A második korongot az 5. sorba kell helyezni.");
    }

    @Test
    void testDropPieceWithUninitializedGrid() {
        // Mock egy hibás GameBoard-hoz
        GameBoard gameBoardMock = mock(GameBoard.class);
        when(gameBoardMock.getGrid()).thenReturn(null);

        Board board = new Board(gameBoardMock);

        // Lépés kísérlete
        boolean result = board.dropPiece(0, States.RED);

        // Várható: nem sikeres
        assertFalse(result, "Uninitialized grid should not allow moves.");
    }
}
