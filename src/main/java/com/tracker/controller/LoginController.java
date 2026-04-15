package com.tracker.controller;

import com.tracker.service.LoginCheck;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField userIdField;

    @FXML
    private PasswordField passwordField;

    private LoginCheck loginService = new LoginCheck();

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
                
                // On applique le CSS à la nouvelle scène
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
