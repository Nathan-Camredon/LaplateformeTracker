package com.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

        try {
            // The URI class expects a standard scheme like 'postgresql://'
            // If the URL already starts with 'jdbc:postgresql://', we remove the 'jdbc:' part for parsing
            String uriString = rawUrl.startsWith("jdbc:") ? rawUrl.substring(5) : rawUrl;
            java.net.URI uri = new java.net.URI(uriString);

            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            String userInfo = uri.getUserInfo();

            // Construct the JDBC URL without the credentials part
            String jdbcUrl = "jdbc:postgresql://" + host + (port != -1 ? ":" + port : "") + path;

            if (userInfo != null && userInfo.contains(":")) {
                String[] credentials = userInfo.split(":", 2);
                return DriverManager.getConnection(jdbcUrl, credentials[0], credentials[1]);
            } else if (userInfo != null) {
                return DriverManager.getConnection(jdbcUrl, userInfo, null);
            } else {
                return DriverManager.getConnection(jdbcUrl);
            }

        } catch (Exception e) {
            System.err.println("Failed to parse or connect with URL: " + rawUrl);
            // If URI parsing fails, try the cleanUrl fallback as a last resort
            String cleanUrl = rawUrl.replace("postgresql://", "jdbc:postgresql://");
            return DriverManager.getConnection(cleanUrl);
        }
    }

    /**
     * Initializes the database schema automatically.
     */
    public static void initializeDatabase() {
        String[] queries = {
            // Table User
            "CREATE TABLE IF NOT EXISTS \"User\" (" +
            "login VARCHAR(255) PRIMARY KEY, " +
            "password VARCHAR(255) NOT NULL)",

            // Table Student
            "CREATE TABLE IF NOT EXISTS \"Student\" (" +
            "id SERIAL PRIMARY KEY, " +
            "first_name VARCHAR(255) NOT NULL, " +
            "last_name VARCHAR(255) NOT NULL, " +
            "age INTEGER NOT NULL)",

            // Ensure Email column exists (for backward compatibility)
            "ALTER TABLE \"Student\" ADD COLUMN IF NOT EXISTS email VARCHAR(255) UNIQUE",

            // Table Grade
            "CREATE TABLE IF NOT EXISTS \"Grade\" (" +
            "id SERIAL PRIMARY KEY, " +
            "value INTEGER NOT NULL, " +
            "subject VARCHAR(255), " +
            "studentid INTEGER REFERENCES \"Student\"(id) ON DELETE CASCADE)"
        };

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            for (String query : queries) {
                stmt.execute(query);
            }
            System.out.println("✅ Database schema initialized successfully.");
            
        } catch (SQLException e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
