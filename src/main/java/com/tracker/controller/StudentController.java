package com.tracker.controller;

import com.tracker.model.Student;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import java.util.List;

public class StudentController {

    @FXML
    private VBox studentList;

    /**
     * Updates the UI list with the provided students
     */
    public void refreshList(List<Student> students) {
        studentList.getChildren().clear();

        for (Student student : students) {
            studentList.getChildren().add(createStudentRow(student));
        }
    }

    /**
     * Creates a stylized HBox row for a student (matching Dashboard.fxml mockup)
     */
    private HBox createStudentRow(Student student) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(50.0);
        row.setPadding(new Insets(0, 40, 0, 40));

        Label idLabel = new Label(String.valueOf(student.getId()));
        idLabel.setPrefWidth(60);
        idLabel.setStyle("-fx-text-fill: white;");

        Label fnLabel = new Label(student.getFirstName());
        fnLabel.setPrefWidth(120);
        HBox.setHgrow(fnLabel, Priority.ALWAYS);
        fnLabel.setStyle("-fx-text-fill: white;");

        Label lnLabel = new Label(student.getLastName());
        lnLabel.setPrefWidth(120);
        HBox.setHgrow(lnLabel, Priority.ALWAYS);
        lnLabel.setStyle("-fx-text-fill: white;");

        Label ageLabel = new Label(String.valueOf(student.getAge()));
        ageLabel.setPrefWidth(60);
        ageLabel.setStyle("-fx-text-fill: white;");

        Label avgLabel = new Label(String.valueOf(student.getAverage()));
        avgLabel.setPrefWidth(80);
        avgLabel.setStyle("-fx-text-fill: white;");

        // Action Buttons
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        actions.setPrefWidth(100);

        Button editBtn = createIconButton("M3,17.25 V21 H6.75 L17.81,9.94 L14.06,6.19 L3,17.25 M20.71,7.04 L16.96,3.29 L15.13,5.12 L18.88,8.87 L20.71,7.04 Z", "pen-icon");
        Button deleteBtn = createIconButton("M6,19 c0,1.1 0.9,2 2,2 h8 c1.1,0 2,-0.9 2,-2 V7 H6 v12 z M19,4 h-3.5 l-1,-1 h-5 l-1,1 H5 v2 h14 V4 z", "trash-icon");

        actions.getChildren().addAll(editBtn, deleteBtn);
        row.getChildren().addAll(idLabel, fnLabel, lnLabel, ageLabel, avgLabel, actions);

        return row;
    }

    private Button createIconButton(String svgPath, String cssClass) {
        Button btn = new Button();
        btn.getStyleClass().add("icon-button");
        SVGPath icon = new SVGPath();
        icon.setContent(svgPath);
        icon.getStyleClass().add(cssClass);
        icon.setScaleX(0.8);
        icon.setScaleY(0.8);
        btn.setGraphic(icon);
        return btn;
    }
}
