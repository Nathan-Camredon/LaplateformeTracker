package com.tracker.controller;

import com.tracker.model.Student;
import com.tracker.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import java.util.function.Consumer;

public class SearchController {

    @FXML
    private TextField idField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;

    private StudentService studentService = new StudentService();
    private Consumer<List<Student>> onSearchCallback;

    public void setOnSearchCallback(Consumer<List<Student>> callback) {
        this.onSearchCallback = callback;
    }

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
