package hu.nye.progtech.connectfour.player;

public class PlayerNameValidator {

    public static void validate(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A név nem lehet üres!");
        }
        if (!name.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("A név csak betűket tartalmazhat!");
        }
    }
}