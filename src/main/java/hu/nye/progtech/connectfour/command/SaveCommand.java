package hu.nye.progtech.connectfour.command;

import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(SaveCommand.class);
    private final GameBoard gameBoard;
    private final boolean isPlayer1Turn;

    public SaveCommand(GameBoard gameBoard, boolean isPlayer1Turn) {
        this.gameBoard = gameBoard;
        this.isPlayer1Turn = isPlayer1Turn;
    }

    @Override
    public void execute() {
        final GameState gameState = new GameState(gameBoard.getGrid(), isPlayer1Turn);

        // A játékállapot szöveges reprezentációja
        final StringBuilder gameStateText = new StringBuilder();

        gameStateText.append("IsPlayer1Turn: ").append(isPlayer1Turn).append("\n");
        gameStateText.append("Grid:\n");

        // Képzeljük el, hogy a `gameBoard.getGrid()` egy kétdimenziós tömb,
        // és minden mezőt karakterekkel reprezentálunk ('Y', 'R' vagy ' ' üres mező).
        for (int i = 0; i < gameBoard.getGrid().length; i++) {
            for (int j = 0; j < gameBoard.getGrid()[i].length; j++) {
                gameStateText.append(gameBoard.getGrid()[i][j] == null ? " " : gameBoard.getGrid()[i][j].toString());
                if (j < gameBoard.getGrid()[i].length - 1) {
                    gameStateText.append(", ");
                }
            }
            gameStateText.append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_state.txt"))) {
            writer.write(gameStateText.toString());
            logger.info("Játékállapot sikeresen elmentve.");
        } catch (IOException e) {
            logger.error("Hiba történt a mentés során: {}", e.getMessage());
        }
    }

    @Override
    public boolean isApplicable(String input) {
        return "save".equalsIgnoreCase(input);
    }
}