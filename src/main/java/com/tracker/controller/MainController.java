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
import javafx.stage.FileChooser;
import com.tracker.service.ExportService;
import com.tracker.model.Student;
import javafx.scene.control.Alert;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * UI Controller responsible for the primary application window.
 * Manages navigation between major views such as the Dashboard and sets up global modals.
 */
public class MainController implements Initializable {

    @FXML
    private StackPane contentArea;

    private StudentController studentController;
    private final ExportService exportService = new ExportService();

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
            stage.setTitle("Add Student");
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
            stage.setTitle("Advanced Search");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contentArea.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Triggers the CSV export process.
     * Opens a file chooser and writes the currently displayed student data to the selected file.
     */
    @FXML
    private void handleExport() {
        if (studentController == null || studentController.getDisplayedStudents() == null || studentController.getDisplayedStudents().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Export Warning");
            alert.setHeaderText(null);
            alert.setContentText("No students currently displayed to export.");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Exported CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("students_export.csv");

        File file = fileChooser.showSaveDialog(contentArea.getScene().getWindow());

        if (file != null) {
            try {
                String csvData = exportService.exportStudentsToCSV(studentController.getDisplayedStudents());
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.write(csvData);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Success");
                alert.setHeaderText(null);
                alert.setContentText("Data successfully exported to: " + file.getName());
                alert.showAndWait();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Error");
                alert.setHeaderText("Failed to save file");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
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
