package hu.nye.progtech.connectfour.db;

public class HighScoreDTO {
    private final String playerName;
    private final int wins;

    public HighScoreDTO(String playerName, int wins) {
        this.playerName = playerName;
        this.wins = wins;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public String toString() {
        return playerName + ": " + wins + " wins";
    }
}
