package hu.nye.progtech.connectfour.command;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CommandRegistryTest {

    @Test
    void testProcessCommandWithKnownCommand() {
        Command mockCommand = mock(Command.class);
        when(mockCommand.isApplicable("test")).thenReturn(true);

        CommandRegistry registry = new CommandRegistry(Arrays.asList(mockCommand));
        assertTrue(registry.processCommand("test"));

        verify(mockCommand).execute();
    }

    @Test
    void testProcessCommandWithUnknownCommand() {
        Command mockCommand = mock(Command.class);
        when(mockCommand.isApplicable("unknown")).thenReturn(false);

        CommandRegistry registry = new CommandRegistry(Arrays.asList(mockCommand));
        assertFalse(registry.processCommand("unknown"));
    }
}