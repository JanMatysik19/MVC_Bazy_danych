package Zadanie.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static final String TABLE = "tasks";
    private static final String DB_FILE = "tasks.db";

    private static final String URL = "jdbc:sqlite:%s".formatted(DB_FILE);


    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println("Could not load the SQLite driver.");
            throw new RuntimeException(e);
        }
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        createTables();
    }

    private static void createTables() {
        var sql = """
            CREATE TABLE IF NOT EXISTS %s(
                Id INTEGER PRIMARY KEY AUTOINCREMENT,
                Content VARCHAR(120) NOT NULL,
                Status VARCHAR(12) NOT NULL
            );
        """.formatted(TABLE);

        try (var conn = getConnection(); var stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Could not create the tables.");
            throw new RuntimeException(e);
        }
    }
}
