package com.tracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * UI Controller responsible for the primary application window.
 * Manages navigation between major views such as the Dashboard and sets up global modals.
 */
public class MainController implements Initializable {

    @FXML
    private StackPane contentArea;

    private StudentController studentController;

    /**
     * Initializes the standard Dashboard view upon successful login.
     * @param url The location used to resolve relative paths
     * @param rb The resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadView("Dashboard.fxml");

        if (studentController != null) {
            studentController.refreshFromDatabase();
        }
    }

    /**
     * Handles the opening of the "Add Student" modal popup.
     */
    @FXML
    private void handleAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/AddStudentPopup.fxml"));
            Parent root = loader.load();

            AddStudentController controller = loader.getController();

            controller.setOnSuccessCallback(() -> {
                if (studentController != null) {
                    studentController.refreshFromDatabase();
                }
            });

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Étudiant");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contentArea.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the opening of the "Advanced Search" modal popup.
     */
    @FXML
    private void handleAdvancedSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/SearchPopup.fxml"));
            Parent root = loader.load();

            SearchController searchController = loader.getController();

            searchController.setOnSearchCallback(results -> {
                if (studentController != null) {
                    studentController.refreshList(results);
                }
            });

            Stage stage = new Stage();
            stage.setTitle("Recherche Avancée");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contentArea.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dynamically swaps the central content area with a specified FXML view.
     * @param fxmlFile The filename of the view to render
     */
    public void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/" + fxmlFile));
            Node node = loader.load();

            if (fxmlFile.equals("Dashboard.fxml")) {
                this.studentController = loader.getController();
            }

            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes user logout logic, returning the user to the Login screen.
     */
    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/tracker/ressources/Login.fxml"));
            Stage stage = (Stage) contentArea.getScene().getWindow();

            stage.getScene().setRoot(root);

            stage.setWidth(1000);
            stage.setHeight(700);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
