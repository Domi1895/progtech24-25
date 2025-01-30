package hu.nye.progtech.connectfour.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerNameProvider {

    private static final Logger logger = LoggerFactory.getLogger(PlayerNameProvider.class);
    private final InputProvider inputProvider;

    public PlayerNameProvider(InputProvider inputProvider) {
        this.inputProvider = inputProvider;
    }

    public PlayerName promptPlayerName(String promptMessage) {
        PlayerName playerName = null;
        while (playerName == null) {
            // Logoljuk a prompt üzenetet
            logger.info(promptMessage);
            final String input = inputProvider.getInput().trim();
            try {
                PlayerNameValidator.validate(input); // Validáljuk a nevet
                playerName = new PlayerName(input); // Ha valid, létrehozzuk a PlayerName objektumot
                logger.info("Játékos neve: {}", input); // Ha sikerült, logoljuk a játékos nevét
            } catch (IllegalArgumentException e) {
                logger.error("Hiba: {}", e.getMessage()); // Hiba, ha érvénytelen név
            }
        }
        return playerName;
    }
}