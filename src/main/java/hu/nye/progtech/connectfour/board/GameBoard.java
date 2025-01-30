package hu.nye.progtech.connectfour.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameBoard {

    private static final Logger logger = LoggerFactory.getLogger(GameBoard.class);

    private final int rows;
    private final int columns;
    private final States[][] grid;

    public GameBoard(BoardConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("A BoardConfig objektum nem lehet null!");
        }
        this.rows = config.getRows();
        this.columns = config.getColumns();
        this.grid = new States[rows][columns];
        initializeBoard();
    }

    /**
     * A tábla megjelenítése konzolon.
     * Ezt most nem logoljuk, hanem csak a ténylegesen szükséges esetekben.
     */
    public void displayBoard() {
        // Ellenőrizze, hogy a grid nem null, ha valami miatt mégis null lenne
        if (grid == null) {
            logger.error("A tábla nem lett inicializálva!");
            return;
        }

        // A tábla megjelenítése, de nem logoljuk minden egyes lépésnél
        //Azért maradt System.out.println mert ez a metódus kifejezetten a tábla vizuális megjelenítéséért felelős a konzolon
        for (States[] row : grid) {
            final StringBuilder rowDisplay = new StringBuilder();
            for (States cell : row) {
                rowDisplay.append(cell.getSymbol()).append(" ");  // Az enum megfelelő szimbólumát jeleníti meg
            }
            System.out.println(rowDisplay.toString().trim()); // Az eredeti konzol kimenet itt marad
        }
    }

    public States[][] getGrid() {
        return grid;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    /**
     * Tábla inicializálása üres elemekkel.
     */
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = States.EMPTY;  // Az üres mezőket a States.EMPTY enum értékkel inicializáljuk
            }
        }
    }
}