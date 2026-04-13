package com.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class to manage database connection using environment variables.
 */
public class DatabaseConfig {
    // Load and configure Dotenv to read .env file
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    /**
     * Establishes a connection to the PostgreSQL database.
     * @return java.sql.Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        // Load credentials from .env file
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        
        // JDBC URL for PostgreSQL
        String jdbcUrl = "jdbc:postgresql://localhost:5432/tracker_db";

        // Check if environment variables are correctly loaded
        if (user == null || password == null) {
            throw new SQLException("Database credentials missing in .env file");
        }

        // Configure connection properties
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        // Establish and return the connection
        return DriverManager.getConnection(jdbcUrl, props);
    }
}
