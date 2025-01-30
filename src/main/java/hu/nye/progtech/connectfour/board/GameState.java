package hu.nye.progtech.connectfour.board;

import java.io.Serializable;

public class GameState implements Serializable {

    private final States[][] grid; // Tábla állapota
    private final boolean isPlayer1Turn; // Aktuális játékos

    public GameState(States[][] grid, boolean isPlayer1Turn) {
        this.grid = deepCopyGrid(grid); // Mély másolat készítése
        this.isPlayer1Turn = isPlayer1Turn;
    }

    public States[][] getGrid() {
        return deepCopyGrid(grid); // Mély másolat visszaadása
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    private States[][] deepCopyGrid(States[][] original) {
        final States[][] copy = new States[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
}