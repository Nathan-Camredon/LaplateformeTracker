package com.tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("ressources/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        String css = Main.class.getResource("css/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Tracker JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}