package hu.nye.progtech.connectfour.game;

import hu.nye.progtech.connectfour.board.BoardConfig;
import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WinCheckerTest {

    @Test
    void testCheckWinHorizontal() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        gameBoard.getGrid()[2][0] = States.YELLOW;
        gameBoard.getGrid()[2][1] = States.YELLOW;
        gameBoard.getGrid()[2][2] = States.YELLOW;
        gameBoard.getGrid()[2][3] = States.YELLOW;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertTrue(winChecker.checkWin(States.YELLOW));
    }

    @Test
    void testCheckWinVertical() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        gameBoard.getGrid()[0][0] = States.RED;
        gameBoard.getGrid()[1][0] = States.RED;
        gameBoard.getGrid()[2][0] = States.RED;
        gameBoard.getGrid()[3][0] = States.RED;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertTrue(winChecker.checkWin(States.RED));
    }

    @Test
    void testCheckWinDiagonal() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        gameBoard.getGrid()[0][0] = States.YELLOW;
        gameBoard.getGrid()[1][1] = States.YELLOW;
        gameBoard.getGrid()[2][2] = States.YELLOW;
        gameBoard.getGrid()[3][3] = States.YELLOW;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertTrue(winChecker.checkWin(States.YELLOW));
    }

    @Test
    void testCheckWinNoWinner() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        gameBoard.getGrid()[0][0] = States.RED;
        gameBoard.getGrid()[0][1] = States.YELLOW;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertFalse(winChecker.checkWin(States.RED));
        assertFalse(winChecker.checkWin(States.YELLOW));
    }

    @Test
    void testIsBoardFull() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        // Fill the board to make it full
        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getColumns(); col++) {
                gameBoard.getGrid()[row][col] = States.RED;
            }
        }

        WinChecker winChecker = new WinChecker(gameBoard);
        assertTrue(winChecker.isBoardFull());
    }

    @Test
    void testCheckWinDiagonalReverse() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        gameBoard.getGrid()[3][0] = States.YELLOW;
        gameBoard.getGrid()[2][1] = States.YELLOW;
        gameBoard.getGrid()[1][2] = States.YELLOW;
        gameBoard.getGrid()[0][3] = States.YELLOW;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertTrue(winChecker.checkWin(States.YELLOW));
    }

    @Test
    void testNoWinnerOnNotFullBoard() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        // Részben megtelt tábla, nincs győztes
        gameBoard.getGrid()[0][0] = States.RED;
        gameBoard.getGrid()[1][0] = States.YELLOW;
        gameBoard.getGrid()[0][1] = States.RED;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertFalse(winChecker.checkWin(States.RED));
        assertFalse(winChecker.checkWin(States.YELLOW));
    }

    @Test
    void testCheckWinEdgeRows() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        gameBoard.getGrid()[5][0] = States.RED;
        gameBoard.getGrid()[5][1] = States.RED;
        gameBoard.getGrid()[5][2] = States.RED;
        gameBoard.getGrid()[5][3] = States.RED;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertTrue(winChecker.checkWin(States.RED));
    }

    @Test
    void testCheckWinEmptyBoard() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);

        WinChecker winChecker = new WinChecker(gameBoard);
        assertFalse(winChecker.checkWin(States.RED));
        assertFalse(winChecker.checkWin(States.YELLOW));
    }

    @Test
    void testNoWinnerInAlternatingMoves() {
        GameBoard gameBoard = new GameBoard(BoardConfig.STANDARD_6x7);
        gameBoard.getGrid()[0][0] = States.RED;
        gameBoard.getGrid()[1][0] = States.YELLOW;
        gameBoard.getGrid()[2][0] = States.RED;
        gameBoard.getGrid()[3][0] = States.YELLOW;

        WinChecker winChecker = new WinChecker(gameBoard);
        assertFalse(winChecker.checkWin(States.RED));
        assertFalse(winChecker.checkWin(States.YELLOW));
    }
}
