package hu.nye.progtech.connectfour.game;

import hu.nye.progtech.connectfour.board.Board;
import hu.nye.progtech.connectfour.board.BoardConfig;
import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;
import hu.nye.progtech.connectfour.command.CommandRegistry;
import hu.nye.progtech.connectfour.db.HighScoreManager;
import hu.nye.progtech.connectfour.player.PlayerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GamePlayTest {

    private GameBoard mockGameBoard;
    private PlayerName mockPlayer1;
    private PlayerName mockPlayer2;
    private CommandRegistry mockCommandRegistry;
    private HighScoreManager mockHighScoreManager;
    private Scanner scanner;
    private GamePlay gamePlay;

    @BeforeEach
    void setUp() {
        mockGameBoard = mock(GameBoard.class);
        mockPlayer1 = mock(PlayerName.class);
        mockPlayer2 = mock(PlayerName.class);
        mockCommandRegistry = mock(CommandRegistry.class);
        mockHighScoreManager = mock(HighScoreManager.class);

        when(mockPlayer1.getName()).thenReturn("Player1");
        when(mockPlayer2.getName()).thenReturn("Player2");

        // Létrehozunk egy üres játéktáblát
        States[][] emptyGrid = new States[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                emptyGrid[i][j] = States.EMPTY;
            }
        }
        when(mockGameBoard.getGrid()).thenReturn(emptyGrid);

        // **Előre definiált több bemenet biztosítása**
        String simulatedInput = "invalid\nexit\n"; // Első egy érvénytelen bemenet, majd "exit"
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(inputStream); // **Valódi Scanner példány!**

        gamePlay = new GamePlay(mockPlayer1, mockPlayer2, mockGameBoard, scanner, mockCommandRegistry, mockHighScoreManager);
    }

    @Test
    void testGameBoardInitialization() {
        // A STANDARD_6x7 enum értéke
        BoardConfig config = BoardConfig.STANDARD_6x7;  // 6 sor, 7 oszlop
        GameBoard gameBoard = new GameBoard(config);

        // Ellenőrizzük, hogy a tábla mérete helyes
        assertEquals(6, gameBoard.getRows(), "A sorok száma helytelen.");
        assertEquals(7, gameBoard.getColumns(), "Az oszlopok száma helytelen.");

        // Ellenőrizzük, hogy a tábla minden mezője üres
        States[][] grid = gameBoard.getGrid();
        for (int i = 0; i < gameBoard.getRows(); i++) {
            for (int j = 0; j < gameBoard.getColumns(); j++) {
                assertEquals(States.EMPTY, grid[i][j], "A tábla nem üres az inicializálás után.");
            }
        }
    }

    @Test
    void testGameBoardInitialization_InvalidConfig() {
        // Ellenőrizzük, hogy a GameBoard kivételt dob, ha a BoardConfig null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new GameBoard(null));
        assertEquals("A BoardConfig objektum nem lehet null!", exception.getMessage(), "A kivétel üzenetének egyeznie kell.");
    }

    @Test
    void testStartGame_ExitOnCommand() {
        when(mockCommandRegistry.processCommand("invalid")).thenReturn(false);
        when(mockCommandRegistry.processCommand("exit")).thenReturn(true);

        gamePlay.startGame();

        verify(mockCommandRegistry).processCommand("exit");
    }

    @Test
    void testGetGrid() {
        BoardConfig config = BoardConfig.STANDARD_6x7;
        GameBoard gameBoard = new GameBoard(config);

        // Ellenőrizzük, hogy a getGrid() metódus nem null
        assertNotNull(gameBoard.getGrid(), "A grid nem lehet null.");

        // Ellenőrizzük, hogy a grid megfelelő méretű
        assertEquals(6, gameBoard.getGrid().length, "A táblának 6 sorral kell rendelkeznie.");
        assertEquals(7, gameBoard.getGrid()[0].length, "A táblának 7 oszloppal kell rendelkeznie.");
    }

    @Test
    void testStartGame_DrawCondition() {
        when(mockCommandRegistry.processCommand(anyString())).thenReturn(false);

        Board mockBoard = mock(Board.class);
        when(mockBoard.dropPiece(anyInt(), any())).thenReturn(true);

        WinChecker mockWinChecker = mock(WinChecker.class);
        when(mockWinChecker.isBoardFull()).thenReturn(true); // A tábla tele van

        String simulatedInput = "2\n"; // Egy lépés után döntetlen
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        scanner = new Scanner(inputStream);

        gamePlay = new GamePlay(mockPlayer1, mockPlayer2, mockGameBoard, scanner, mockCommandRegistry, mockHighScoreManager);
        gamePlay.startGame();

        verify(mockGameBoard, atLeastOnce()).displayBoard(); // Döntetlen után még egyszer kiíródik a tábla
    }
}