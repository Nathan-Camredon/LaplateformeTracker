package com.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.net.URI;

public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.load();

    /**
     * Se connecte à la base de données PostgreSQL en utilisant l'URL du fichier .env
     */
    public static Connection getConnection() throws SQLException {
        String rawUrl = dotenv.get("DATABASE_URL");
        if (rawUrl == null) {
            throw new SQLException("DATABASE_URL est manquante dans le fichier .env");
        }

        try {
            // On prépare l'URL pour la lecture (on retire 'jdbc:' si présent)
            String uriString = rawUrl.startsWith("jdbc:") ? rawUrl.substring(5) : rawUrl;
            URI uri = new URI(uriString);

            // On extrait les informations de l'URL
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            String userInfo = uri.getUserInfo();

            // Construction de l'URL JDBC standard
            String jdbcUrl = "jdbc:postgresql://" + host + (port != -1 ? ":" + port : "") + path;

            if (userInfo != null && userInfo.contains(":")) {
                String[] auth = userInfo.split(":");
                return DriverManager.getConnection(jdbcUrl, auth[0], auth[1]);
            }
            return DriverManager.getConnection(jdbcUrl);

        } catch (Exception e) {
            // En cas d'erreur de lecture, on tente une conversion simple
            String fallbackUrl = rawUrl.replace("postgresql://", "jdbc:postgresql://");
            return DriverManager.getConnection(fallbackUrl);
        }
    }

    /**
     * Initialise automatiquement les tables si elles n'existent pas encore.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {

            // Table des utilisateurs
            stmt.execute("CREATE TABLE IF NOT EXISTS \"User\" (" +
                        "login VARCHAR(255) PRIMARY KEY, " +
                        "password VARCHAR(255) NOT NULL)");

            // Table des étudiants
            stmt.execute("CREATE TABLE IF NOT EXISTS \"Student\" (" +
                        "id SERIAL PRIMARY KEY, " +
                        "first_name VARCHAR(255) NOT NULL, " +
                        "last_name VARCHAR(255) NOT NULL, " +
                        "age INTEGER NOT NULL, " +
                        "email VARCHAR(255) UNIQUE)");

            // Table des notes
            stmt.execute("CREATE TABLE IF NOT EXISTS \"Grade\" (" +
                        "id SERIAL PRIMARY KEY, " +
                        "value INTEGER NOT NULL, " +
                        "subject VARCHAR(255), " +
                        "studentid INTEGER REFERENCES \"Student\"(id) ON DELETE CASCADE)");

            // Ajout d'un utilisateur par défaut si la table est vide
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM \"User\"");
            if (rs.next() && rs.getInt(1) == 0) {
                // Mot de passe 'admin' haché en SHA-256
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
