package hu.nye.progtech.connectfour.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCommandTest {

    @Test
    void testIsApplicable() {
        DefaultCommand defaultCommand = new DefaultCommand();
        assertTrue(defaultCommand.isApplicable("anything"));
    }

    @Test
    void testExecute() {
        DefaultCommand defaultCommand = new DefaultCommand();
        defaultCommand.execute();
        // Ellenőrizd, hogy nem történt kivétel
    }
}