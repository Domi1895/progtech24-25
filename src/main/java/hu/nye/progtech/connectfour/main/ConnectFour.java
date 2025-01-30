package hu.nye.progtech.connectfour.main;

import hu.nye.progtech.connectfour.board.BoardConfig;
import hu.nye.progtech.connectfour.board.GameBoard;

import hu.nye.progtech.connectfour.command.CommandRegistry;
import hu.nye.progtech.connectfour.command.ExitCommand;
import hu.nye.progtech.connectfour.command.HighScoreCommand;
import hu.nye.progtech.connectfour.command.LoadCommand;
import hu.nye.progtech.connectfour.command.SaveCommand;

import hu.nye.progtech.connectfour.db.HighScoreRepository;
import hu.nye.progtech.connectfour.db.HighScoreManager;
import hu.nye.progtech.connectfour.game.GamePlay;

import hu.nye.progtech.connectfour.player.ConsoleInputProvider;
import hu.nye.progtech.connectfour.player.InputProvider;
import hu.nye.progtech.connectfour.player.PlayerName;
import hu.nye.progtech.connectfour.player.PlayerNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Scanner;

public class ConnectFour {

    private static final Logger logger = LoggerFactory.getLogger(ConnectFour.class);

    public static void main(String[] args) {

        // Scanner object creation
        final Scanner scanner = new Scanner(System.in);

        // InputProvider and PlayerNameProvider creation
        final InputProvider inputProvider = new ConsoleInputProvider(scanner);
        final PlayerNameProvider playerNameProvider = new PlayerNameProvider(inputProvider);

        // Initialize HighScoreRepository and inject into HighScoreManager
        final HighScoreRepository highScoreRepository = new HighScoreRepository();
        final HighScoreManager highScoreManager = new HighScoreManager(highScoreRepository);

        // Prompt for player name
        final PlayerName player1 = playerNameProvider.promptPlayerName("Add meg az 1. játékos nevét:");

        // AI player is automatically "AI"
        final PlayerName player2 = new PlayerName("AI");

        logger.info("Üdvözöllek, {}!", player1.getName());

        // Create game board
        final GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);

        // Create CommandRegistry with commands
        final CommandRegistry commandRegistry = new CommandRegistry(Arrays.asList(
                new SaveCommand(gameBoard, true), // true: initially player 1's turn
                new LoadCommand(gameBoard),
                new HighScoreCommand(highScoreManager),
                new ExitCommand()
        ));

        // Create GamePlay object, passing CommandRegistry
        final GamePlay gamePlay = new GamePlay(player1, player2, gameBoard, scanner, commandRegistry, highScoreManager);

        // Start the game
        gamePlay.startGame();
    }
}