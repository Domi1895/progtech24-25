package hu.nye.progtech.connectfour.command;

import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.StringReader;

class LoadCommandTest {

    @Test
    void testIsApplicable() {
        GameBoard mockGameBoard = mock(GameBoard.class);
        LoadCommand loadCommand = new LoadCommand(mockGameBoard);
        assertTrue(loadCommand.isApplicable("load"));
        assertFalse(loadCommand.isApplicable("save"));
    }

    @Test
    void testExecute() throws Exception {
        // Mock a GameBoard-hoz
        GameBoard mockGameBoard = mock(GameBoard.class);

        // Mock grid setup
        States[][] mockGrid = new States[2][2]; // Kisebb grid példának
        mockGrid[0][0] = States.EMPTY;
        mockGrid[0][1] = States.EMPTY;
        mockGrid[1][0] = States.EMPTY;
        mockGrid[1][1] = States.EMPTY;

        when(mockGameBoard.getGrid()).thenReturn(mockGrid);

        // Mock az olvasáshoz (StringReader használatával szimuláljuk a fájlt)
        String mockFileContent = "IsPlayer1Turn: true\nGrid:\nRED, YELLOW\nEMPTY, RED\n";
        BufferedReader mockReader = new BufferedReader(new StringReader(mockFileContent));

        // LoadCommand példányosítása, most a tesztált osztályba beépített reader-t használjuk
        LoadCommand loadCommand = spy(new LoadCommand(mockGameBoard));

        // A FileReader helyettesítése StringReader-rel
        doReturn(mockReader).when(loadCommand).createBufferedReader();

        // Metódus tesztelése
        loadCommand.execute();

        // Ellenőrzés
        verify(mockGameBoard, atLeastOnce()).getGrid();
        assert mockGrid[0][0] == States.RED;
        assert mockGrid[0][1] == States.YELLOW;
        assert mockGrid[1][0] == States.EMPTY;
        assert mockGrid[1][1] == States.RED;
    }

    @Test
    void testExecute_ValidFile() throws Exception {
        GameBoard mockGameBoard = mock(GameBoard.class);

        States[][] mockGrid = new States[2][2]; // Kisebb grid példának
        when(mockGameBoard.getGrid()).thenReturn(mockGrid);

        String mockFileContent = "IsPlayer1Turn: true\nGrid:\nRED, YELLOW\nEMPTY, RED\n";
        BufferedReader mockReader = new BufferedReader(new StringReader(mockFileContent));

        LoadCommand loadCommand = spy(new LoadCommand(mockGameBoard));
        doReturn(mockReader).when(loadCommand).createBufferedReader();

        loadCommand.execute();

        verify(mockGameBoard, atLeastOnce()).getGrid();
        assertEquals(States.RED, mockGrid[0][0]);
        assertEquals(States.YELLOW, mockGrid[0][1]);
        assertEquals(States.EMPTY, mockGrid[1][0]);
        assertEquals(States.RED, mockGrid[1][1]);
    }
}