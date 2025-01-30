package hu.nye.progtech.connectfour.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardConfigTest {

    @Test
    void testBoardConfigValues() {
        BoardConfig config = BoardConfig.STANDARD_6x7;

        // Verify rows and columns
        assertEquals(6, config.getRows());
        assertEquals(7, config.getColumns());
    }
}