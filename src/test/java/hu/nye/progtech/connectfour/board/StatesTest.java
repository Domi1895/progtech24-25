package hu.nye.progtech.connectfour.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatesTest {

    @Test
    void testStatesSymbols() {
        assertEquals('.', States.EMPTY.getSymbol());
        assertEquals('Y', States.YELLOW.getSymbol());
        assertEquals('R', States.RED.getSymbol());
    }
}