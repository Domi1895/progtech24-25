package hu.nye.progtech.connectfour.move.aiPlayer;

import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AiMoveTest {

    @Test
    void testGetMove_ValidColumns() {
        AiMove aiMove = new AiMove();
        GameBoard mockGameBoard = mock(GameBoard.class);

        // Mockoljuk a getColumns() metódust, hogy mindig 7-et adjon vissza
        when(mockGameBoard.getColumns()).thenReturn(7);

        // A getGrid() visszaad egy 6x7-es táblát, ahol az összes oszlop üres
        States[][] grid = new States[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grid[i][j] = States.EMPTY;
            }
        }

        when(mockGameBoard.getGrid()).thenReturn(grid);

        // Ellenőrizzük, hogy az AI érvényes oszlopot választ
        int move = aiMove.getMove(mockGameBoard);
        assertTrue(move >= 0 && move < 7, "A generált oszlopnak a [0, 6] tartományban kell lennie.");
    }

    @Test
    void testGetMove_InvalidColumns() {
        AiMove aiMove = new AiMove();
        GameBoard mockGameBoard = mock(GameBoard.class);

        // Mockoljuk a getColumns() metódust úgy, hogy először 0-t, majd -1-et adjon vissza
        when(mockGameBoard.getColumns()).thenReturn(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> aiMove.getMove(mockGameBoard));
        assertEquals("A columns értéke nem lehet nulla vagy negatív.", exception.getMessage());

        // Ezután próbáljuk -1-et
        when(mockGameBoard.getColumns()).thenReturn(-1);

        exception = assertThrows(IllegalArgumentException.class, () -> aiMove.getMove(mockGameBoard));
        assertEquals("A columns értéke nem lehet nulla vagy negatív.", exception.getMessage());
    }
}
