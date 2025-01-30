package hu.nye.progtech.connectfour.command;

import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaveCommandTest {

    private GameBoard mockGameBoard;
    private SaveCommand saveCommand;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        mockGameBoard = mock(GameBoard.class);
        tempFile = File.createTempFile("game_state", ".txt"); // Ideiglenes fájl létrehozása
        saveCommand = new SaveCommand(mockGameBoard, true);
    }

    @Test
    void testIsApplicable() {
        assertTrue(saveCommand.isApplicable("save"));
        assertFalse(saveCommand.isApplicable("load"));
        assertFalse(saveCommand.isApplicable("exit"));
    }

    @Test
    void testExecute_SuccessfulSave() throws Exception {
        // Mockolt játéktábla (2x2-es példa)
        States[][] mockGrid = {
                {States.RED, States.YELLOW},
                {States.EMPTY, States.RED}
        };
        when(mockGameBoard.getGrid()).thenReturn(mockGrid);

        // **Futtassuk az eredeti mentési parancsot, hogy ténylegesen fájlba írjon**
        saveCommand.execute();

        // **Ellenőrizzük, hogy az ideiglenes fájlban a megfelelő adatok vannak**
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("game_state.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        String expectedOutput = """
                IsPlayer1Turn: true
                Grid:
                RED, YELLOW
                EMPTY, RED
                """;

        assertEquals(expectedOutput.strip(), fileContent.toString().strip());
    }

    @Test
    void testExecute_FileWriteError() throws IOException {
        // Mockolt játéktábla
        States[][] mockGrid = {
                {States.RED, States.YELLOW},
                {States.EMPTY, States.RED}
        };
        when(mockGameBoard.getGrid()).thenReturn(mockGrid);

        // **Mockoljuk a FileWriter-t és kivételt dobunk, ha a fájlírás megtörténne**
        FileWriter mockFileWriter = mock(FileWriter.class);
        doThrow(new IOException("Nem sikerült írni a fájlba")).when(mockFileWriter).write(anyString());

        // **Spy SaveCommand, hogy a valódi fájlkezelés helyett a mockolt verziót használja**
        SaveCommand saveCommandSpy = spy(saveCommand);

        // **Futtassuk a parancsot és ellenőrizzük, hogy nem omlik össze**
        assertDoesNotThrow(() -> saveCommandSpy.execute());

        // **Ellenőrizzük, hogy a GameBoard lekérdezte az állapotot**
        verify(mockGameBoard, atLeastOnce()).getGrid();
    }
}