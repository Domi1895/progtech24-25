package hu.nye.progtech.connectfour.player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerNameValidatorTest {

    @Test
    void testValidName() {
        assertDoesNotThrow(() -> PlayerNameValidator.validate("Player"));
    }

    @Test
    void testInvalidNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> PlayerNameValidator.validate(" "));
    }

    @Test
    void testInvalidNameWithNumbers() {
        assertThrows(IllegalArgumentException.class, () -> PlayerNameValidator.validate("Player1"));
    }

    @Test
    void testInvalidNameNull() {
        assertThrows(IllegalArgumentException.class, () -> PlayerNameValidator.validate(null));
    }
}
