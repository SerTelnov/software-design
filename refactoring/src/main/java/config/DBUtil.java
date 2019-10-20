package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static final String URL_SQL = "jdbc:sqlite:test.db";

    public static void initDB() throws SQLException {
        try (Connection c = getConnection();
             Statement stmt = c.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";

            stmt.executeUpdate(sql);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_SQL);
    }
}
