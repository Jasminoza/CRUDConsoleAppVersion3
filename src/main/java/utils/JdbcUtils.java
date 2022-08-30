package utils;

import com.typesafe.config.ConfigFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUtils {
    private static Connection connectionToPostgreSQL;

    private JdbcUtils() {
    }

    public static synchronized PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static synchronized PreparedStatement getPreparedStatement(String sql, int statementCode) throws SQLException {
        return getConnection().prepareStatement(sql, statementCode);
    }

    private static synchronized Connection getConnection() {
        if (connectionToPostgreSQL == null) {
            try {
                String jdbcDriver = ConfigFactory.load().getString("db.jdbcDriver");
                String databaseUrl = ConfigFactory.load().getString("db.url");
                String user = ConfigFactory.load().getString("db.user");
                String password = ConfigFactory.load().getString("db.password");
                Class.forName(jdbcDriver);
                connectionToPostgreSQL = DriverManager.getConnection(databaseUrl, user, password);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connectionToPostgreSQL;
    }
}