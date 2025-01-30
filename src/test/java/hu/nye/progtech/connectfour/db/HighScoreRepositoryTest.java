package hu.nye.progtech.connectfour.db;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreRepositoryTest {

    private HighScoreRepository highScoreRepository;

    @BeforeAll
    static void loadJdbcDriver() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    @BeforeEach
    void setUpDatabase() {
        highScoreRepository = new HighScoreRepository("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        try (Statement stmt = highScoreRepository.getConnection().createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS high_scores (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "player_name VARCHAR(255) NOT NULL UNIQUE, " +
                    "wins INT NOT NULL)");
        } catch (SQLException e) {
            fail("Nem sikerült létrehozni az adatbázis táblát: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDownDatabase() {
        if (highScoreRepository != null) {
            highScoreRepository.close();
        }
    }

    @Test
    void testGetHighScoresSorting() {
        highScoreRepository.addOrUpdatePlayer("Alice", 5);
        highScoreRepository.addOrUpdatePlayer("Bob", 8);
        highScoreRepository.addOrUpdatePlayer("Charlie", 3);

        List<HighScoreDTO> highScores = highScoreRepository.getHighScores();

        assertEquals(3, highScores.size());
        assertEquals("Bob", highScores.get(0).getPlayerName());  // Highest score first
        assertEquals(8, highScores.get(0).getWins());
        assertEquals("Alice", highScores.get(1).getPlayerName());
        assertEquals(5, highScores.get(1).getWins());
        assertEquals("Charlie", highScores.get(2).getPlayerName());
        assertEquals(3, highScores.get(2).getWins());
    }

    @Test
    void testConstructorCreatesDatabaseAndTable() {
        // Próbálunk létrehozni egy példányt
        assertDoesNotThrow(() -> {
            HighScoreRepository repository = new HighScoreRepository("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
            assertNotNull(repository.getConnection()); // Ellenőrizzük, hogy a kapcsolat létrejött-e

            // Ellenőrizzük, hogy a high_scores tábla létezik
            try (Statement stmt = repository.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM high_scores")) {
                assertNotNull(rs);
            } catch (SQLException e) {
                fail("A tábla nem létezik vagy nem érhető el: " + e.getMessage());
            }

            repository.close(); // Ne hagyjuk nyitva az adatbázist
        });
    }

    @Test
    void testCloseWithNullConnection() {
        HighScoreRepository repo = new HighScoreRepository("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        try {
            repo.getConnection().close(); // Kézzel bezárjuk a kapcsolatot
        } catch (SQLException e) {
            fail("Unexpected error while closing connection manually.");
        }

        assertDoesNotThrow(repo::close, "Closing a repository with a null connection should not throw an exception.");
    }

    @Test
    void testGetHighScoresWithInvalidSQL() {
        try (Statement stmt = highScoreRepository.getConnection().createStatement()) {
            assertThrows(SQLException.class, () -> {
                stmt.executeQuery("SELECT wrong_column FROM high_scores");
            }, "Querying a non-existing column should throw SQLException.");
        } catch (SQLException e) {
            fail("Unexpected error during setup: " + e.getMessage());
        }
    }

    @Test
    void testDefaultConstructorCreatesDatabaseConnection() {
        assertDoesNotThrow(() -> {
            HighScoreRepository repository = new HighScoreRepository();
            assertNotNull(repository.getConnection(), "Database connection should not be null.");
            repository.close();
        }, "Default constructor should not throw an exception.");
    }

    @Test
    void testCreateTableFailsWithInvalidSQL() {
        try (Statement stmt = highScoreRepository.getConnection().createStatement()) {
            assertThrows(SQLException.class, () -> {
                stmt.execute("CREATE TABLE high_scores (wrong_column INVALID_TYPE)");
            }, "Invalid SQL should throw SQLException.");
        } catch (SQLException e) {
            fail("Unexpected error during setup: " + e.getMessage());
        }
    }

    @Test
    void testCloseAlreadyClosedConnection() {
        highScoreRepository.close(); // Első zárás
        assertDoesNotThrow(() -> highScoreRepository.close(), "Closing an already closed connection should not throw an exception.");
    }

    @Test
    void testCreateTable() {
        try (Statement stmt = highScoreRepository.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM high_scores");
            assertNotNull(rs, "The table high_scores should exist.");
        } catch (SQLException e) {
            fail("Table creation failed: " + e.getMessage());
        }
    }

    @Test
    void testAddOrUpdatePlayerWithClosedConnection() {
        highScoreRepository.close(); // Lezárjuk a kapcsolatot, hogy szimuláljuk a hibát

        assertThrows(RuntimeException.class, () -> {
            highScoreRepository.addOrUpdatePlayer("TestPlayer", 10);
        }, "Should throw RuntimeException when trying to insert into a closed connection.");
    }

    @Test
    void testGetHighScoresWhenEmpty() {
        List<HighScoreDTO> scores = highScoreRepository.getHighScores();
        assertTrue(scores.isEmpty(), "High scores should be empty initially.");
    }

    @Test
    void testConstructorWithInvalidDatabaseUrl() {
        assertThrows(RuntimeException.class, () -> {
            new HighScoreRepository("invalid-url"); // Rossz adatbázis URL szimulációja
        });
    }

    @Test
    void testCloseMultipleTimes() {
        highScoreRepository.close(); // Első hívás
        assertDoesNotThrow(() -> highScoreRepository.close(), "Multiple close() calls should not cause an exception.");
    }

    @Test
    void testDatabaseConnectionClose() {
        highScoreRepository.close();
        assertDoesNotThrow(() -> highScoreRepository.close());
    }

    @Test
    void testSQLExceptionHandlingOnInsert() {
        try {
            Connection mockConn = highScoreRepository.getConnection();
            mockConn.close(); // Force connection to be closed

            assertThrows(RuntimeException.class, () ->
                    highScoreRepository.addOrUpdatePlayer("Dave", 7));
        } catch (SQLException e) {
            fail("Unexpected SQL error: " + e.getMessage());
        }
    }
}