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

            /* Redirect */
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/MainView.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) userIdField.getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Connexion failure");
        }
    }
}
