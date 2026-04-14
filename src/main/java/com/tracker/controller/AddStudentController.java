package com.tracker.controller;

import com.tracker.model.Student;
import com.tracker.model.StudentRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField emailField;
    @FXML
    private Label errorLabel;

    private StudentRequest studentRequest = new StudentRequest();
    private Runnable onSuccessCallback;

    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    @FXML
    private void handleSave() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String ageStr = ageField.getText();
        String email = emailField.getText();

        // Basic validation: all fields must be filled
        if (firstName.isEmpty() || lastName.isEmpty() || ageStr.isEmpty() || email.isEmpty()) {
            showError("Veuillez remplir tous les champs !");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);

            Student newStudent = new Student(0, firstName, lastName, age, email);
            boolean success = studentRequest.CreateStudent(
                    newStudent.getFirstName(), 
                    newStudent.getLastName(), 
                    newStudent.getAge(), 
                    newStudent.getEmail()
            );

            if (success) {
                if (onSuccessCallback != null) {
                    onSuccessCallback.run();
                }
                closePopup();
            } else {
                showError("Erreur lors de l'ajout dans la base de données.");
            }

        } catch (NumberFormatException e) {
            showError("L'âge et la moyenne doivent être des nombres.");
        }
    }

    @FXML
    private void handleCancel() {
        closePopup();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void closePopup() {
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }
}
