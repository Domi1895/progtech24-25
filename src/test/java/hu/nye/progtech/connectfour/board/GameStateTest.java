package hu.nye.progtech.connectfour.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void testGameStateInitialization() {
        // Initialize a 6x7 grid with EMPTY states
        States[][] grid = new States[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grid[i][j] = States.EMPTY;
            }
        }

        // Create a GameState instance
        GameState gameState = new GameState(grid, true);

        // Verify the grid and player turn
        assertNotNull(gameState.getGrid());
        assertEquals(6, gameState.getGrid().length);
        assertTrue(gameState.isPlayer1Turn());
    }

    @Test
    void testDeepCopyGrid() {
        // Initialize a grid
        States[][] grid = new States[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grid[i][j] = States.EMPTY;
            }
        }

        // Create a GameState instance
        GameState gameState = new GameState(grid, true);

        // Modify the original grid
        grid[0][0] = States.RED;

        // Verify that the GameState grid is unaffected
        assertEquals(States.EMPTY, gameState.getGrid()[0][0]);
    }
}