package com.tracker.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static Connection getConnection() throws SQLException {
        // Chargement des infos depuis le .env
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        
        // On construit l'URL JDBC propre (localhost et tracker_db)
        String jdbcUrl = "jdbc:postgresql://localhost:5432/tracker_db";

        if (user == null || password == null) {
            throw new SQLException("Identifiants de base de données manquants dans le fichier .env");
        }

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        return DriverManager.getConnection(jdbcUrl, props);
    }
}
