package com.tracker.controller;

import com.tracker.service.LoginCheck;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller supervising the JavaFX Login page interactions.
 * Connects the UI securely with the LoginCheck service.
 */
public class LoginController {

    @FXML
    private TextField userIdField;

    @FXML
    private PasswordField passwordField;

    private LoginCheck loginService = new LoginCheck();

    /**
     * Processes form inputs and delegates authentication checks to the backing service.
     * Proceeds to the Main application view upon a successful check.
     */
    @FXML
    private void handleLogin() {

        String login = userIdField.getText();
        String password = passwordField.getText();

        boolean success = loginService.checkLogin(login, password);

        if (success) {
            System.out.println("Connexion success !");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/MainView.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) userIdField.getScene().getWindow();
                Scene scene = new Scene(root, 1000, 700);

                String css = getClass().getResource("/com/tracker/css/styles.css").toExternalForm();
                if (css != null) {
                    scene.getStylesheets().add(css);
                }

                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Connexion failure");
        }
    }
}
