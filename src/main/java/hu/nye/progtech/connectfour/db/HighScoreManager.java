package hu.nye.progtech.connectfour.db;

import java.util.List;

public class HighScoreManager {

    private final HighScoreRepository repository;

    public HighScoreManager(HighScoreRepository repository) {
        this.repository = repository;
    }

    public void addOrUpdatePlayer(String playerName, int wins) {
        repository.addOrUpdatePlayer(playerName, wins);
    }

    public List<HighScoreDTO> getHighScores() {
        return repository.getHighScores();
    }
}