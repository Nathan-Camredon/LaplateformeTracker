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

/**
 * Controller supervising the dynamic list and interactions of Student rows within the Dashboard.
 * Handles database refreshes, sorting, modifications, and deletions directly on the UI grid.
 */
public class StudentController {

    @FXML private VBox studentList;
    private List<Student> displayedStudents;


    private StudentRequest studentRequest = new StudentRequest();
    private StudentService studentService = new StudentService();

    private String currentSortColumn = "id";
    private boolean isAscending = true;

    /**
     * Re-pulls the complete list of students from the database and redraws the UI.
     * Respects the current UI sort state.
     */
    public void refreshFromDatabase() {
        this.displayedStudents = studentService.getAllStudents(currentSortColumn, isAscending);

        studentList.getChildren().clear();
        for (Student s : displayedStudents) {
            studentList.getChildren().add(new StudentRow(s, () -> handleEdit(s), () -> handleDelete(s)));
        }
    }

    /**
     * Fully replaces the visual student list using a provided List of Student objects.
     * Useful for rendering explicit search results or filters without relying on a full database pull.
     * @param students The List of students to render
     */
    public void refreshList(List<Student> students) {
        this.displayedStudents = students;
        studentList.getChildren().clear();
        for (Student s : students) {
            studentList.getChildren().add(new StudentRow(s, () -> handleEdit(s), () -> handleDelete(s)));
        }
    }

    /**
     * @return The list of students currently displayed in the UI.
     */
    public List<Student> getDisplayedStudents() {
        return displayedStudents;
    }

    /**
     * Internal handler to abstract the sorting logic, automatically toggling ascending/descending
     * states if the same column is clicked consecutively.
     * @param column The SQL column name representing the clicked UI header
     */
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
    @FXML private void handleSortPrenom() { handleSort("first_name"); }
    @FXML private void handleSortNom() { handleSort("last_name"); }
    @FXML private void handleSortAge() { handleSort("age"); }
    @FXML private void handleSortMoyenne() { handleSort("grade"); }

    /**
     * Prompts the user for confirmation and deletes the targeted student from the database.
     * Refreshes the view dynamically upon completion.
     * @param student The specific Student object to delete
     */
    private void handleDelete(Student student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + student.getFirstName() + " ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Deletion");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            if (studentRequest.DeleteStudent(student.getId())) {
                refreshFromDatabase();
            }
        }
    }

    /**
     * Spawns the AddStudentPopup configured in "Edit" mode with the target's data.
     * @param student The target Student object to edit
     */
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
