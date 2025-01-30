package hu.nye.progtech.connectfour.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoreRepository {
    private static final String DEFAULT_DATABASE_URL = "jdbc:sqlite:highscores.db";
    private final Connection conn;

    public HighScoreRepository() {
        this(DEFAULT_DATABASE_URL);
    }

    public HighScoreRepository(String databaseUrl) {
        try {
            this.conn = DriverManager.getConnection(databaseUrl);
            createTable();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    private void createTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS high_scores (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "player_name VARCHAR(255) NOT NULL UNIQUE, " +
                "wins INT NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Failed to create high_scores table: " + e.getMessage());
        }
    }

    public void addOrUpdatePlayer(String playerName, int wins) {
        String sqlInsert = "INSERT INTO high_scores (player_name, wins) VALUES (?, ?)";
        String sqlUpdate = "UPDATE high_scores SET wins = wins + ? WHERE player_name = ?";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                insertStmt.setString(1, playerName);
                insertStmt.setInt(2, wins);
                insertStmt.executeUpdate();
            } catch (SQLException e) {
                // Ha a beszúrás sikertelen (mert a játékos már létezik), akkor frissítjük
                try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
                    updateStmt.setInt(1, wins); // Hozzáadjuk az új nyeréseket a meglévőhöz
                    updateStmt.setString(2, playerName);
                    updateStmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback(); // Ha hiba történik, visszavonjuk a módosításokat
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Failed to rollback transaction", rollbackEx);
            }
            throw new RuntimeException("Failed to insert/update player", e);
        } finally {
            try {
                conn.setAutoCommit(true); // Visszaállítjuk az autocommit módot
            } catch (SQLException ex) {
                throw new RuntimeException("Failed to reset auto-commit", ex);
            }
        }
    }

    public List<HighScoreDTO> getHighScores() {
        List<HighScoreDTO> highScores = new ArrayList<>();
        final String sql = "SELECT player_name, wins FROM high_scores ORDER BY wins DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                highScores.add(new HighScoreDTO(rs.getString("player_name"), rs.getInt("wins")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving high scores: " + e.getMessage());
        }
        return highScores;
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }

    // **ÚJ: Adatbáziskapcsolat elérhetővé tétele teszteléshez**
    public Connection getConnection() {
        return conn;
    }
}