package hu.nye.progtech.connectfour.game;

import hu.nye.progtech.connectfour.board.Board;
import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;
import hu.nye.progtech.connectfour.command.CommandRegistry;
import hu.nye.progtech.connectfour.db.HighScoreManager;
import hu.nye.progtech.connectfour.move.aiPlayer.AiMove;
import hu.nye.progtech.connectfour.player.PlayerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GamePlay {

    private static final Logger logger = LoggerFactory.getLogger(GamePlay.class);
    private final GameBoard gameBoard;
    private final PlayerName player1;
    private final PlayerName player2;
    private final Scanner scanner;
    private final CommandRegistry commandRegistry;
    private final HighScoreManager highScoreManager;
    private boolean isPlayer1Turn;

    public GamePlay(PlayerName player1, PlayerName player2, GameBoard gameBoard, Scanner scanner, CommandRegistry commandRegistry, HighScoreManager highScoreManager) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameBoard = gameBoard;
        this.scanner = scanner;
        this.commandRegistry = commandRegistry;
        this.highScoreManager = highScoreManager;
        this.isPlayer1Turn = true;

        initializeGameState("game_state.txt");
    }

    public void startGame() {
        final WinChecker winChecker = new WinChecker(gameBoard);
        final Board board = new Board(gameBoard);

        while (true) {
            gameBoard.displayBoard();
            logger.info("{}'s turn:", isPlayer1Turn ? player1.getName() : player2.getName());

            if (!scanner.hasNextLine()) {
                logger.warn("Nincs több bemenet, a játék kilép.");
                break;
            }

            final String input;
            final int move;

            if (isPlayer1Turn) {
                input = scanner.nextLine().trim();
                if (!input.matches("\\d+")) {
                    if (commandRegistry.processCommand(input)) {
                        continue;
                    } else {
                        logger.warn("Ismeretlen parancs. Az elérhető parancsok: save, load, exit, highscore.");
                        continue;
                    }
                }
                try {
                    move = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    logger.warn("Érvénytelen bemenet, próbálkozz újra!");
                    continue;
                }
            } else {
                AiMove aiMove = new AiMove();
                move = aiMove.getMove(gameBoard);
                logger.info("AI lépése: {}", move + 1);
            }

            if (!board.dropPiece(move, isPlayer1Turn ? States.YELLOW : States.RED)) {
                logger.warn("Az oszlop tele van, próbálj egy másik oszlopot!");
                continue;
            }

            if (winChecker.checkWin(isPlayer1Turn ? States.YELLOW : States.RED)) {
                gameBoard.displayBoard();
                logger.info("{} nyert!", isPlayer1Turn ? player1.getName() : player2.getName());
                highScoreManager.addOrUpdatePlayer(isPlayer1Turn ? player1.getName() : player2.getName(), 1);
                break;
            }

            if (winChecker.isBoardFull()) {
                gameBoard.displayBoard();
                logger.info("Döntetlen! A tábla tele van.");
                break;
            }

            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    private void initializeGameState(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            logger.info("Korábbi játékállapot betöltése a fájlból...");

            reader.readLine();
            reader.readLine();

            String line;
            int rowIndex = 0;

            while ((line = reader.readLine()) != null) {
                final String[] cells = line.split(", ");
                for (int colIndex = 0; colIndex < cells.length; colIndex++) {
                    switch (cells[colIndex]) {
                        case "RED":
                            gameBoard.getGrid()[rowIndex][colIndex] = States.RED;
                            break;
                        case "YELLOW":
                            gameBoard.getGrid()[rowIndex][colIndex] = States.YELLOW;
                            break;
                        case "EMPTY":
                        default:
                            gameBoard.getGrid()[rowIndex][colIndex] = States.EMPTY;
                            break;
                    }
                }
                rowIndex++;
            }

            logger.info("Játékállapot sikeresen betöltve.");
        } catch (IOException e) {
            logger.error("Fájl nem található vagy hiba történt a betöltés során. Üres pályával indul a játék.");
        }
    }
}