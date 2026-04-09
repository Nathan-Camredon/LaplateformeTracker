package com.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // Charge le fichier .env
    private static final Dotenv dotenv = Dotenv.load();

    /**
     * Établit une connexion à la base de données PostgreSQL.
     * @return Un objet Connection ouvert.
     * @throws SQLException Si la connexion échoue.
     */
    public static Connection getConnection() throws SQLException {
        // Récupère l'URL de la base de données depuis le fichier .env
        String rawUrl = dotenv.get("DATABASE_URL");
        
        if (rawUrl == null) {
            throw new SQLException("La variable DATABASE_URL est manquante dans le fichier .env");
        }

        // JDBC nécessite le préfixe "jdbc:" pour identifier le protocole
        String jdbcUrl = rawUrl.replace("postgresql://", "jdbc:postgresql://");

        // Établit la connexion avec les identifiants par défaut de ton .env
        return DriverManager.getConnection(jdbcUrl, "postgres", "root");
    }
}
