package hu.nye.progtech.connectfour.move.aiPlayer;

import java.util.Random;
import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;

public class AiMove {

    private final Random random = new Random();

    public int getMove(GameBoard gameBoard) {
        int columns = gameBoard.getColumns();
        if (columns <= 0) {
            throw new IllegalArgumentException("A columns értéke nem lehet nulla vagy negatív.");
        }

        // Próbáljunk olyan oszlopot találni, amely még nincs tele
        int move;
        do {
            move = random.nextInt(columns);
        } while (isColumnFull(gameBoard, move));

        return move;
    }

    private boolean isColumnFull(GameBoard gameBoard, int column) {
        // Az első sor ellenőrzése: ha itt már van valami, az oszlop tele van
        return gameBoard.getGrid()[0][column] != States.EMPTY;
    }
}