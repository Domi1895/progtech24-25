package hu.nye.progtech.connectfour.board;

public enum BoardConfig {

    //sorok, oszlopok, üres sorok
    STANDARD_6x7(6, 7);  // Alapértelmezett táblaméret (6 sor, 7 oszlop)

    private final int rows;
    private final int columns;

    // Konstruktor
    BoardConfig(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    // Getterek
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}