package com.tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Find and load the MainView.fxml resource from the classpath
        var fxmlUrl = Main.class.getResource("/com/tracker/ressources/MainView.fxml");
        if (fxmlUrl == null) {
            throw new IOException("Cannot find MainView.fxml at /com/tracker/ressources/MainView.fxml");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

        // Load and apply the global stylesheet
        var cssUrl = Main.class.getResource("/com/tracker/css/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Warning: styles.css not found at /com/tracker/css/styles.css");
        }

        stage.setTitle("Tracker JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}