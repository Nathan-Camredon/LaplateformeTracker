package com.tracker.controller;

import com.tracker.model.Student;
import com.tracker.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controller administrating the generic and advanced searching UI popup.
 */
public class SearchController {

    @FXML
    private TextField idField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;

    private StudentService studentService = new StudentService();
    private Consumer<List<Student>> onSearchCallback;

    /**
     * Assigns a consumer callback which accepts the search results array returned by the service.
     * @param callback A consumer processing a List of matching Students
     */
    public void setOnSearchCallback(Consumer<List<Student>> callback) {
        this.onSearchCallback = callback;
    }

    /**
     * Extracts values from the search fields and delegates evaluation to the StudentService.
     */
    @FXML
    private void handleSearch() {
        String idStr = idField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        List<Student> results = studentService.advancedSearch(idStr, firstName, lastName);

        if (onSearchCallback != null) {
            onSearchCallback.accept(results);
        }

        closePopup();
    }

    @FXML
    private void handleCancel() {
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}
