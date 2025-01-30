package hu.nye.progtech.connectfour.db;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HighScoreManagerTest {

    private HighScoreRepository repository;
    private HighScoreManager highScoreManager;

    @BeforeEach
    void setUp() {
        repository = mock(HighScoreRepository.class);
        highScoreManager = new HighScoreManager(repository);
    }

    @Test
    void testAddOrUpdatePlayer() {
        doNothing().when(repository).addOrUpdatePlayer("Alice", 5);

        highScoreManager.addOrUpdatePlayer("Alice", 5);

        verify(repository, times(1)).addOrUpdatePlayer("Alice", 5);
    }

    @Test
    void testGetHighScores() {
        List<HighScoreDTO> mockScores = Arrays.asList(
                new HighScoreDTO("Alice", 5),
                new HighScoreDTO("Bob", 3)
        );

        when(repository.getHighScores()).thenReturn(mockScores);

        List<HighScoreDTO> result = highScoreManager.getHighScores();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getPlayerName());
        assertEquals(5, result.get(0).getWins());
        assertEquals("Bob", result.get(1).getPlayerName());
        assertEquals(3, result.get(1).getWins());
    }
}