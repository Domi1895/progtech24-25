package hu.nye.progtech.connectfour.player;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class PlayerNameProviderTest {

    @Test
    void testPromptPlayerNameValid() {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);
        Mockito.when(inputProvider.getInput()).thenReturn("John");

        PlayerNameProvider playerNameProvider = new PlayerNameProvider(inputProvider);
        PlayerName playerName = playerNameProvider.promptPlayerName("Enter name:");

        assertEquals("John", playerName.getName());
    }

    @Test
    void testPromptPlayerNameInvalid() {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);
        Mockito.when(inputProvider.getInput()).thenReturn("123").thenReturn("John");

        PlayerNameProvider playerNameProvider = new PlayerNameProvider(inputProvider);
        PlayerName playerName = playerNameProvider.promptPlayerName("Enter name:");

        assertEquals("John", playerName.getName());
    }
}
