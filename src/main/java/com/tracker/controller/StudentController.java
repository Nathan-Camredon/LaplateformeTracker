package com.tracker.controller;

import com.tracker.model.Student;
import com.tracker.model.StudentRequest;
import com.tracker.service.StudentService;
import com.tracker.ui.StudentRow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StudentController {

    @FXML private VBox studentList;

    private StudentRequest studentRequest = new StudentRequest();
    private StudentService studentService = new StudentService();

    // État du tri
    private String currentSortColumn = "id";
    private boolean isAscending = true;

    /**
     * Rafraîchit l'affichage avec la liste triée.
     */
    public void refreshFromDatabase() {
        List<Student> students = studentService.getAllStudents(currentSortColumn, isAscending);
        
        studentList.getChildren().clear();
        for (Student s : students) {
            // Utilisation du nouveau composant StudentRow
            studentList.getChildren().add(new StudentRow(s, () -> handleEdit(s), () -> handleDelete(s)));
        }
    }

    public void refreshList(List<Student> students) {
        studentList.getChildren().clear();
        for (Student s : students) {
            studentList.getChildren().add(new StudentRow(s, () -> handleEdit(s), () -> handleDelete(s)));
        }
    }

    private void handleSort(String column) {
        if (currentSortColumn.equals(column)) {
            isAscending = !isAscending;
        } else {
            currentSortColumn = column;
            isAscending = true;
        }
        refreshFromDatabase();
    }

    @FXML private void handleSortId() { handleSort("id"); }
    @FXML private void handleSortPrenom() { handleSort("prenom"); }
    @FXML private void handleSortNom() { handleSort("nom"); }
    @FXML private void handleSortAge() { handleSort("age"); }
    @FXML private void handleSortMoyenne() { handleSort("moyenne"); }

    private void handleDelete(Student student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous supprimer " + student.getFirstName() + " ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Suppression");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (studentRequest.DeleteStudent(student.getId())) {
                refreshFromDatabase();
            }
        }
    }

    private void handleEdit(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/AddStudentPopup.fxml"));
            Parent root = loader.load();

            AddStudentController controller = loader.getController();
            controller.setStudent(student);
            controller.setOnSuccessCallback(this::refreshFromDatabase);

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(studentList.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
