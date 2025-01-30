package hu.nye.progtech.connectfour.game;

import hu.nye.progtech.connectfour.board.GameBoard;
import hu.nye.progtech.connectfour.board.States;

public class WinChecker {

    private final GameBoard gameBoard;

    public WinChecker(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Ellenőrzi, hogy van-e győztes a játékban.
     *
     * @param piece a játékos szimbóluma ('X' vagy 'O')
     * @return true, ha a játékos nyert, false, ha nem
     */
    public boolean checkWin(States piece) {
        final States[][] grid = gameBoard.getGrid();

        // Ellenőrizze vízszintesen, függőlegesen és átlósan
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                // Ha találunk egy korongot, nézze meg, hogy van-e 4 egymás melletti
                if (grid[row][col] == piece) {
                    // Ellenőrizzük vízszintesen, függőlegesen és átlósan
                    if (checkDirection(row, col, 1, 0, piece) || // vízszintes
                            checkDirection(row, col, 0, 1, piece) || // függőleges
                            checkDirection(row, col, 1, 1, piece) || // átlós balról jobbra
                            checkDirection(row, col, 1, -1, piece)) { // átlós jobbról balra
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy a tábla teljesen tele van-e.
     *
     * @return true, ha a tábla teljesen tele van, false, ha nem
     */
    public boolean isBoardFull() {
        final States[][] grid = gameBoard.getGrid();
        for (int i = 0; i < grid[0].length; i++) {
            if (grid[0][i] == States.EMPTY) {
                return false; // Ha van legalább egy üres hely az első sorban, akkor nem tele
            }
        }
        return true;
    }

    /**
     * Ellenőrzi, hogy a megadott irányban (dx, dy) van-e 4 egymás melletti azonos színű korong.
     *
     * @param row   kiinduló sor
     * @param col   kiinduló oszlop
     * @param dx    az irány a sorokban (-1, 0, 1)
     * @param dy    az irány az oszlopokban (-1, 0, 1)
     * @param piece a játékos szimbóluma (States.YELLOW vagy States.RED)
     * @return true, ha van 4 egymás melletti azonos korong
     */
    private boolean checkDirection(int row, int col, int dx, int dy, States piece) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            final int r = row + i * dx;
            final int c = col + i * dy;
            if (r >= 0 && r < gameBoard.getGrid().length && c >= 0 && c < gameBoard.getGrid()[r].length) {
                if (gameBoard.getGrid()[r][c] == piece) {
                    count++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return count == 4;
    }
}