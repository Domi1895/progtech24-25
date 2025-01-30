package hu.nye.progtech.connectfour.player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerNameTest {

    @Test
    void testConstructorAndGetter() {
        PlayerName playerName = new PlayerName("Player1");
        assertEquals("Player1", playerName.getName());
    }

    @Test
    void testNameCreation() {
        PlayerName playerName = new PlayerName("Player1");
        assertEquals("Player1", playerName.getName());
    }

    @Test
    void testEqualsSameObject() {
        PlayerName playerName1 = new PlayerName("Player1");
        PlayerName playerName2 = new PlayerName("Player1");
        assertEquals(playerName1, playerName2);
    }

    @Test
    void testEqualsDifferentObject() {
        PlayerName playerName1 = new PlayerName("Player1");
        PlayerName playerName2 = new PlayerName("Player2");
        assertNotEquals(playerName1, playerName2);
    }

    @Test
    void testHashCode() {
        PlayerName playerName1 = new PlayerName("Player1");
        PlayerName playerName2 = new PlayerName("Player1");
        assertEquals(playerName1.hashCode(), playerName2.hashCode());
    }

    @Test
    void testToString() {
        PlayerName playerName = new PlayerName("Player1");
        assertEquals("Player1", playerName.toString());
    }
}
