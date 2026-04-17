package com.tracker;

import com.tracker.config.DatabaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * Entry point of the JavaFX Application.
 * Bootstraps the database connection and loads the primary Login view.
 */
public class Main extends Application {

    /**
     * JavaFX start method called once the system is ready to render UI.
     * @param stage The primary stage assigned to this application
     * @throws IOException If the designated FXML views fail to load
     */
    @Override
    public void start(Stage stage) throws IOException {

        DatabaseConfig.initializeDatabase();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/Login.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);

        URL cssResource = getClass().getResource("/com/tracker/css/styles.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        }

        stage.setTitle("Plateforme Tracker");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Standard main function serving as the standalone program entry.
     * @param args Command line arguments passed into launch
     */
    public static void main(String[] args) {
        launch(args);
    }
}