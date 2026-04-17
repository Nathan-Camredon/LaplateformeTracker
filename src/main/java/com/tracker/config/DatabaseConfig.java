package com.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.net.URI;

/**
 * Configuration class for managing database connections.
 * Handles loading environment variables and establishing JDBC connections.
 */
public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.load();

    /**
     * Establishes a connection to the PostgreSQL database using the environment variable string.
     * @return Connection a JDBC Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        String rawUrl = dotenv.get("DATABASE_URL");
        if (rawUrl == null) {
            throw new SQLException("DATABASE_URL est manquante dans le fichier .env");
        }

        try {

            String uriString = rawUrl.startsWith("jdbc:") ? rawUrl.substring(5) : rawUrl;
            URI uri = new URI(uriString);

            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            String userInfo = uri.getUserInfo();

            String jdbcUrl = "jdbc:postgresql://" + host + (port != -1 ? ":" + port : "") + path;

            if (userInfo != null && userInfo.contains(":")) {
                String[] auth = userInfo.split(":");
                return DriverManager.getConnection(jdbcUrl, auth[0], auth[1]);
            }
            return DriverManager.getConnection(jdbcUrl);

        } catch (Exception e) {

            String fallbackUrl = rawUrl.replace("postgresql://", "jdbc:postgresql://");
            return DriverManager.getConnection(fallbackUrl);
        }
    }

    /**
     * Initializes the database by creating necessary tables if they do not exist
     * and injecting a default administrator user.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS \"User\" (" +
                        "login VARCHAR(255) PRIMARY KEY, " +
                        "password VARCHAR(255) NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS \"Student\" (" +
                        "id SERIAL PRIMARY KEY, " +
                        "first_name VARCHAR(255) NOT NULL, " +
                        "last_name VARCHAR(255) NOT NULL, " +
                        "age INTEGER NOT NULL, " +
                        "email VARCHAR(255) UNIQUE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS \"Grade\" (" +
                        "id SERIAL PRIMARY KEY, " +
                        "value INTEGER NOT NULL, " +
                        "subject VARCHAR(255), " +
                        "studentid INTEGER REFERENCES \"Student\"(id) ON DELETE CASCADE)");

            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM \"User\"");
            if (rs.next() && rs.getInt(1) == 0) {

                String hashedAdmin = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
                stmt.execute("INSERT INTO \"User\" (login, password) VALUES ('admin', '" + hashedAdmin + "')");
                System.out.println("👤 Utilisateur admin créé par défaut.");
            }

            System.out.println("✅ Base de données prête.");

        } catch (SQLException e) {
            System.err.println("❌ Erreur d'initialisation : " + e.getMessage());
        }
    }
}
