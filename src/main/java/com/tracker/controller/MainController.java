package com.tracker.controller;

import com.tracker.model.Student;
import com.tracker.service.StudentService;
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
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private StackPane contentArea;

    private StudentController studentController;
    private StudentService studentService = new StudentService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load the Dashboard by default
        loadView("Dashboard.fxml");
        
        // Initial data load
        if (studentController != null) {
            List<Student> allStudents = studentService.getAllStudents("id", true);
            studentController.refreshList(allStudents);
        }
    }

    /**
     * Opens the Add Student Popup as a modal dialog
     */
    @FXML
    private void handleAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/AddStudentPopup.fxml"));
            Parent root = loader.load();
            
            AddStudentController addController = loader.getController();
            
            // Refresh the dashboard list after a successful addition
            addController.setOnSuccessCallback(() -> {
                if (studentController != null) {
                    List<Student> allStudents = studentService.getAllStudents("id", true);
                    studentController.refreshList(allStudents);
                }
            });

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Étudiant");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contentArea.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Error opening add student popup: " + e.getMessage());
        }
    }

    /**
     * Opens the Advanced Search Popup as a modal dialog
     */
    @FXML
    private void handleAdvancedSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/SearchPopup.fxml"));
            Parent root = loader.load();
            
            SearchController searchController = loader.getController();
            
            // Link the search results to the dashboard
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
            System.err.println("Error opening search popup: " + e.getMessage());
        }
    }

    /**
     * Helper method to load different FXML views into the center area
     */
    public void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/" + fxmlFile));
            Node node = loader.load();
            
            // Store the controller if we loaded the dashboard
            if (fxmlFile.equals("Dashboard.fxml")) {
                this.studentController = loader.getController();
            }
            
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            System.err.println("Error loading view " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
