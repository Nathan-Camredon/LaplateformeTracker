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
