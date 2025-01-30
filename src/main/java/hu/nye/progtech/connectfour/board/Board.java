package hu.nye.progtech.connectfour.board;

public class Board {

    private final GameBoard gameBoard;

    public Board(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Játékos lépésének végrehajtása.
     * A lépés során egy korongot ejtünk le a megadott oszlopba.
     *
     * @param column az oszlop, ahová a korongot ejteni szeretnénk
     * @param piece  a játékos szimbóluma (States.RED vagy States.YELLOW)
     * @return true, ha a lépés sikeres volt, false, ha nem
     */
    public boolean dropPiece(int column, States state) {
        // Ellenőrzés: a grid inicializálva van-e
        final States[][] grid = gameBoard.getGrid();
        if (grid == null) {
            return false; // Ha a grid nincs inicializálva, a lépés nem sikeres
        }

        // Ellenőrzés: az oszlop indexe érvényes-e
        if (column < 0 || column >= grid[0].length) {
            return false; // Érvénytelen oszlop index
        }

        // Korong elhelyezése
        for (int i = grid.length - 1; i >= 0; i--) {
            if (grid[i][column] == States.EMPTY) {
                grid[i][column] = state;
                return true; // Sikeres lépés
            }
        }

        // Az oszlop tele van
        return false;
    }
}