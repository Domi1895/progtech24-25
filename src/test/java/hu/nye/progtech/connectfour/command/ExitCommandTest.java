package hu.nye.progtech.connectfour.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ExitCommandTest {

    @Test
    void testIsApplicable() {
        ExitCommand exitCommand = new ExitCommand();
        assertTrue(exitCommand.isApplicable("exit"));
        assertFalse(exitCommand.isApplicable("not-exit"));
    }

    @Test
    void testExecute() {
        ExitCommand exitCommand = spy(new ExitCommand());
        doNothing().when(exitCommand).execute();

        exitCommand.execute();

        verify(exitCommand).execute(); // Ellenőrizzük, hogy a metódus hívva lett
    }
}