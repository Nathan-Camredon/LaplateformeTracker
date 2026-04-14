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

public class DatabaseConfig {
    // Loads the .env file
    private static final Dotenv dotenv = Dotenv.load();

    /**
     * Establishes a connection to the PostgreSQL database.
     * @return An open Connection object.
     * @throws SQLException If the connection fails.
     */
    public static Connection getConnection() throws SQLException {
        // Retrieves the database URL from the .env file
        String rawUrl = dotenv.get("DATABASE_URL");
        
        if (rawUrl == null) {
            throw new SQLException("The DATABASE_URL variable is missing in the .env file");
        }

        // JDBC requires the "jdbc:" prefix to identify the protocol
        String jdbcUrl = rawUrl.replace("postgresql://", "jdbc:postgresql://");

        // Establishes the connection with the default credentials from your .env
        return DriverManager.getConnection(jdbcUrl, "postgres", "root");
    }
}
