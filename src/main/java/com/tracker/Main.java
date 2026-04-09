package com.tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point of the JavaFX Tracker application.
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML layout file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        
        // Load the external CSS stylesheet
        String css = Main.class.getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        // Window setup
        stage.setTitle("Tracker JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Launch the application
        launch();
    }
}
