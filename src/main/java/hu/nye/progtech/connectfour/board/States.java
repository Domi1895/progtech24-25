package hu.nye.progtech.connectfour.board;

public enum States {

    EMPTY('.'), YELLOW('Y'), // Ember (sárga)
    RED('R');     // AI (piros)

    private final char symbol;

    // Konstruktor
    States(char symbol) {
        this.symbol = symbol;
    }

    // Getter
    public char getSymbol() {
        return symbol;
    }
}