package hu.nye.progtech.connectfour.board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testDisplayBoard() {
        // Set up the board with the STANDARD_6x7 configuration
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);

        // Call displayBoard to write to the output stream
        gameBoard.displayBoard();

        // Capture output
        String output = outputStream.toString().trim();

        // Normalize line endings to prevent OS-specific issues
        output = output.replace("\r\n", "\n");

        // Expected number of rows (6)
        String[] lines = output.split("\n");
        assertTrue(lines.length == 6, "Expected 6 rows, but got " + lines.length);

        // Expected format of each row (7 columns of ".")
        String expectedRow = ". . . . . . .";
        for (String line : lines) {
            assertTrue(line.trim().equals(expectedRow), "Unexpected row format: " + line);
        }
    }

    @Test
    void testGetGrid() {
        // Set up the board with the STANDARD_6x7 configuration
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);

        // Get the grid
        States[][] grid = gameBoard.getGrid();

        // Check the dimensions of the grid
        assertEquals(6, grid.length); // 6 rows
        assertEquals(7, grid[0].length); // 7 columns

        // Check that all cells are initialized to EMPTY
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                assertEquals(States.EMPTY, grid[i][j]);
            }
        }
    }

    @Test
    void testGetColumns() {
        // Set up the board with the STANDARD_6x7 configuration
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);

        // Test the number of columns
        assertEquals(7, gameBoard.getColumns()); // Expecting 7 columns
    }

    @Test
    void testGetRows() {
        // Set up the board with the STANDARD_6x7 configuration
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);

        // Test the number of rows
        assertEquals(6, gameBoard.getRows()); // Expecting 6 rows
    }

    @Test
    void testBoardInitialization() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        States[][] grid = gameBoard.getGrid();
        assertEquals(6, grid.length);
        assertEquals(7, grid[0].length);
    }

    @Test
    void testGameBoardConstructorWithNullConfig() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new GameBoard(null);
        });
        assertEquals("A BoardConfig objektum nem lehet null!", exception.getMessage());
    }
}
