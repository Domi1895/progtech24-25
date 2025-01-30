package hu.nye.progtech.connectfour.command;

import hu.nye.progtech.connectfour.db.HighScoreDTO;
import hu.nye.progtech.connectfour.db.HighScoreManager;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HighScoreCommandTest {

    @Test
    void testIsApplicable() {
        HighScoreManager mockManager = mock(HighScoreManager.class);
        HighScoreCommand highScoreCommand = new HighScoreCommand(mockManager);
        assertTrue(highScoreCommand.isApplicable("highscore"));
        assertFalse(highScoreCommand.isApplicable("load"));
    }

    @Test
    void testExecute() {
        HighScoreManager mockManager = mock(HighScoreManager.class);

        // Simuláljuk, hogy a getHighScores metódus visszaad egy listát HighScore objektumokkal
        List<HighScoreDTO> mockScores = Arrays.asList(
                new HighScoreDTO("Player1", 5),
                new HighScoreDTO("Player2", 3)
        );
        when(mockManager.getHighScores()).thenReturn(mockScores);

        HighScoreCommand highScoreCommand = new HighScoreCommand(mockManager);
        highScoreCommand.execute();

        // Ellenőrizzük, hogy a metódus hívva lett
        verify(mockManager).getHighScores();
    }
}