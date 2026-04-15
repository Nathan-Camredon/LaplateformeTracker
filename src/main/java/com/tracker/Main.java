package com.tracker;

import com.tracker.config.DatabaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        // Initialisation de la base de données au démarrage
        DatabaseConfig.initializeDatabase();

        // Chargement de l'interface de Connexion (Login)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/Login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);

        // Chargement du style CSS
        URL cssResource = getClass().getResource("/com/tracker/css/styles.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        }

        stage.setTitle("Plateforme Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}