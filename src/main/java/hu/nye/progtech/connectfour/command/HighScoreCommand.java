package hu.nye.progtech.connectfour.command;

import hu.nye.progtech.connectfour.db.HighScoreDTO;
import hu.nye.progtech.connectfour.db.HighScoreManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HighScoreCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(HighScoreCommand.class);
    private final HighScoreManager highScoreManager;

    public HighScoreCommand(HighScoreManager highScoreManager) {
        this.highScoreManager = highScoreManager;
    }

    @Override
    public void execute() {
        logger.info("High Scores:");
        for (HighScoreDTO score : highScoreManager.getHighScores()) {
            logger.info(score.toString()); // HighScore osztály már tartalmaz egy megfelelő toString() metódust
        }
    }

    @Override
    public boolean isApplicable(String input) {
        return "highscore".equalsIgnoreCase(input);
    }
}