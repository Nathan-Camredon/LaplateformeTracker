package com.tracker.controller;

import com.tracker.model.Student;
import com.tracker.model.StudentRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * UI Controller for the Add and Edit Student popup modal.
 * Interacts directly with the StudentRequest backend service to persist changes.
 */
public class AddStudentController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField ageField;
    @FXML private TextField emailField;
    @FXML private Label errorLabel;
    @FXML private Label titleLabel;
    @FXML private Button submitButton;

    private StudentRequest studentRequest = new StudentRequest();
    private Runnable onSuccessCallback;
    private Student existingStudent;

    /**
     * Registers a callback to be executed upon a successful creation or modification of a student.
     * @param callback The Runnable task to execute
     */
    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    /**
     * Pre-populates the form fields using an existing Student object.
     * Alters the visual context indicating an "Edit" rather than "Add".
     * @param student The existing Student object to edit
     */
    public void setStudent(Student student) {
        this.existingStudent = student;

        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getLastName());
        ageField.setText(String.valueOf(student.getAge()));
        emailField.setText(student.getEmail());

        if (titleLabel != null) titleLabel.setText("Modifier l'Étudiant");
        if (submitButton != null) submitButton.setText("Mettre à jour");
    }

    /**
     * Triggers the saving mechanism, validating the inputs and routing them to the persistence layer.
     */
    @FXML
    private void handleSave() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String ageStr = ageField.getText();
        String email = emailField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || ageStr.isEmpty() || email.isEmpty()) {
            showError("Merci de remplir tous les champs !");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            boolean success;

            if (existingStudent != null) {
                success = studentRequest.Update(existingStudent.getId(), firstName, lastName, age, email);
            } else {
                success = studentRequest.CreateStudent(firstName, lastName, age, email);
            }

            if (success) {
                if (onSuccessCallback != null) onSuccessCallback.run();
                closePopup();
            } else {
                showError("Erreur lors de l'enregistrement.");
            }

        } catch (NumberFormatException e) {
            showError("L'âge doit être un nombre.");
        }
    }

    /**
     * Closes the active popup window without saving changes.
     */
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
