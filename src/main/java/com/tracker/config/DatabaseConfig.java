package com.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // Loads the .env file
    private static final Dotenv dotenv = Dotenv.load();

    /**
     * Establishes a connection to the PostgreSQL database.
     * @return An open Connection object.
     * @throws SQLException If the connection fails.
     */
    public static Connection getConnection() throws SQLException {
        String rawUrl = dotenv.get("DATABASE_URL");
        if (rawUrl == null) {
            throw new SQLException("DATABASE_URL is missing in .env file");
        }

        // Standard JDBC format: jdbc:postgresql://host:port/database
        // We handle URLs like: postgresql://user:pass@host:port/db
        try {
            String cleanUrl = rawUrl.replace("postgresql://", "jdbc:postgresql://");
            
            // If the URL contains credentials (user:password@), we try a direct connection
            // The PostgreSQL JDBC driver usually handles this format if correctly prefixed.
            return DriverManager.getConnection(cleanUrl);
        } catch (SQLException e) {
            // Fallback: If it fails, let's try to be even more explicit or provide a better error
            System.err.println("JDBC Connection attempt failed with URL: " + rawUrl);
            throw e; 
        }
    }
}
