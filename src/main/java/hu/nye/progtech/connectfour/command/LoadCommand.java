package hu.nye.progtech.connectfour.command;

import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(LoadCommand.class);
    private final GameBoard gameBoard;

    public LoadCommand(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void execute() {
        try (BufferedReader reader = createBufferedReader()) { // A metódust itt hívjuk meg
            // Az első két sort figyelmen kívül hagyjuk
            reader.readLine(); // IsPlayer1Turn sort átugorjuk
            reader.readLine(); // Grid: sort átugorjuk

            String line;
            int rowIndex = 0;

            while ((line = reader.readLine()) != null) {
                if (rowIndex >= gameBoard.getGrid().length) {
                    logger.error("Túl sok sor van a fájlban a játék méretéhez képest.");
                    return; // Kilépünk, mert túl sok a sor
                }

                final String[] columns = line.split(", ");

                if (columns.length != gameBoard.getGrid()[0].length) {
                    logger.error("Hiba a {}. sorban: {} oszlop van, de {} elvárt.", rowIndex + 1, columns.length, gameBoard.getGrid()[0].length);
                    return; // Kilépünk, mert az oszlopszám hibás
                }

                for (int colIndex = 0; colIndex < columns.length; colIndex++) {
                    switch (columns[colIndex]) {
                        case "RED":
                            gameBoard.getGrid()[rowIndex][colIndex] = States.RED;
                            break;
                        case "YELLOW":
                            gameBoard.getGrid()[rowIndex][colIndex] = States.YELLOW;
                            break;
                        case "EMPTY":
                            gameBoard.getGrid()[rowIndex][colIndex] = States.EMPTY;
                            break;
                        default:
                            logger.error("Ismeretlen állapot a fájlban: {}", columns[colIndex]);
                            return; // Kilépünk, mert ismeretlen állapot van a fájlban
                    }
                }
                rowIndex++;
            }

            logger.info("Játékállapot sikeresen betöltve.");
        } catch (IOException e) {
            logger.error("Hiba történt a fájl betöltésekor: {}", e.getMessage());
        }
    }

    // Új metódus hozzáadása a BufferedReader létrehozásához
    protected BufferedReader createBufferedReader() throws IOException {
        return new BufferedReader(new FileReader("game_state.txt"));
    }

    @Override
    public boolean isApplicable(String input) {
        return "load".equalsIgnoreCase(input);
    }
}