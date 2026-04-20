package com.tracker.ui;

import com.tracker.model.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.SVGPath;

import com.tracker.model.Grade;
import com.tracker.model.GradeRequest;
import javafx.scene.control.TextField;
import java.util.List;

/**
 * Custom JavaFX VBox Component representing a single interactive student row.
 * Includes expandable dynamic sub-menus for displaying and modulating grades.
 */
public class StudentRow extends javafx.scene.layout.VBox {

    private boolean isExpanded = false;
    private javafx.scene.layout.VBox detailsArea;
    private Label avgLabel;

    /**
     * Generates a new styled student row mapped to a specific student object.
     * @param student The model data driving this view
     * @param onEdit A callback executable triggered when the edit button is clicked
     * @param onDelete A callback executable triggered when the delete button is clicked
     */
    public StudentRow(Student student, Runnable onEdit, Runnable onDelete) {
        super();
        this.setStyle("-fx-background-color: transparent;");

        HBox mainRow = new HBox(10);
        mainRow.setAlignment(Pos.CENTER_LEFT);
        mainRow.setPadding(new Insets(10, 40, 10, 40));

        Label idLabel = createLabel(String.valueOf(student.getId()), 60);
        Label fnLabel = createLabel(student.getFirstName(), 180);
        Label lnLabel = createLabel(student.getLastName(), 180);
        Label ageLabel = createLabel(String.valueOf(student.getAge()), 60);
        avgLabel = createLabel(String.format("%.2f", student.getAverage()), 80);

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        actions.setPrefWidth(100);

        Button editBtn = createIconButton("M3,17.25 V21 H6.75 L17.81,9.94 L14.06,6.19 L3,17.25 M20.71,7.04 L16.96,3.29 L15.13,5.12 L18.88,8.87 L20.71,7.04 Z", "pen-icon");
        editBtn.setOnAction(e -> { e.consume(); onEdit.run(); });

        Button deleteBtn = createIconButton("M6,19 c0,1.1 0.9,2 2,2 h8 c1.1,0 2,-0.9 2,-2 V7 H6 v12 z M19,4 h-3.5 l-1,-1 h-5 l-1,1 H5 v2 h14 V4 z", "trash-icon");
        deleteBtn.setOnAction(e -> { e.consume(); onDelete.run(); });

        actions.getChildren().addAll(editBtn, deleteBtn);
        mainRow.getChildren().addAll(idLabel, fnLabel, lnLabel, ageLabel, avgLabel, actions);

        detailsArea = new javafx.scene.layout.VBox(5);
        detailsArea.setPadding(new Insets(10, 40, 10, 100));
        detailsArea.setStyle("-fx-background-color: #0b1d35; -fx-border-color: rgba(255,255,255,0.1); -fx-border-width: 1 0 0 0;");
        detailsArea.setVisible(false);
        detailsArea.setManaged(false);

        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Student Grades:");
        title.setStyle("-fx-text-fill: #aaaaaa; -fx-font-style: italic;");

        Button addGradeBtn = new Button("+");
        addGradeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        addGradeBtn.setOnAction(e -> showAddGradeForm(student.getId()));

        headerBox.getChildren().addAll(title, addGradeBtn);
        detailsArea.getChildren().add(headerBox);

        mainRow.setOnMouseClicked(e -> toggleDetails(student.getId()));
        this.getChildren().addAll(mainRow, detailsArea);
    }

    /**
     * Injects an interactive sub-form for adding a new grade specific to this student.
     * @param studentId The unique structural identifier of the attached student
     */
    private void showAddGradeForm(int studentId) {

        detailsArea.getChildren().removeIf(node -> "addForm".equals(node.getId()));

        HBox addForm = new HBox(10);
        addForm.setId("addForm");
        addForm.setAlignment(Pos.CENTER_LEFT);
        addForm.setPadding(new Insets(5, 0, 5, 0));

        TextField subjectField = new TextField();
        subjectField.setPromptText("Subject (e.g. Math)");
        subjectField.setPrefWidth(120);

        TextField valueField = new TextField();
        valueField.setPromptText("Grade (0-20)");
        valueField.setPrefWidth(80);

        Button saveBtn = new Button("Save");
        saveBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        saveBtn.setOnAction(e -> {
            try {
                int val = Integer.parseInt(valueField.getText());
                String sub = subjectField.getText();
                if (!sub.isEmpty()) {
                    if (new GradeRequest().addGrade(val, sub, studentId)) {
                        loadGrades(studentId);
                        subjectField.clear();
                        valueField.clear();
                        subjectField.requestFocus();
                    }
                }
            } catch (NumberFormatException ex) {

            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3px;");
        cancelBtn.setOnAction(e -> detailsArea.getChildren().remove(addForm));

        addForm.getChildren().addAll(subjectField, valueField, saveBtn, cancelBtn);
        detailsArea.getChildren().add(1, addForm);
    }

    /**
     * Expands or collapses the detailed notes menu positioned below the row.
     * Automatically triggers a database query for grades if expanding.
     * @param studentId The unique identifier of the attached student
     */
    private void toggleDetails(int studentId) {
        isExpanded = !isExpanded;
        detailsArea.setVisible(isExpanded);
        detailsArea.setManaged(isExpanded);

        if (isExpanded) {
            loadGrades(studentId);
        }
    }

    /**
     * Queries the DB to load and render the student's grades within the active detailed view.
     * Updates the main row's display average based on fetched entries.
     * @param studentId The student to filter grades against
     */
    private void loadGrades(int studentId) {

        detailsArea.getChildren().removeIf(node -> "gradeItem".equals(node.getId()) || "noGrades".equals(node.getId()));

        List<Grade> grades = new GradeRequest().getGradesByStudentId(studentId);

        if (grades.isEmpty()) {
            Label noGrades = new Label("• No grades found.");
            noGrades.setId("noGrades");
            noGrades.setStyle("-fx-text-fill: white;");
            detailsArea.getChildren().add(noGrades);
            avgLabel.setText("0,00");
        } else {
            double sum = 0;
            for (Grade g : grades) {
                sum += g.getValue();

                HBox gradeLine = new HBox(10);
                gradeLine.setAlignment(Pos.CENTER_LEFT);
                gradeLine.setId("gradeItem");

                Label gLabel = new Label("• " + g.getSubject() + " : " + g.getValue());
                gLabel.setStyle("-fx-text-fill: white;");
                gLabel.setPrefWidth(200);

                Button delBtn = new Button("✖");
                delBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff6b6b; -fx-cursor: hand; -fx-font-weight: bold;");
                delBtn.setOnAction(e -> {
                    new GradeRequest().deleteGrade(g.getId());
                    loadGrades(studentId);
                });

                gradeLine.getChildren().addAll(gLabel, delBtn);
                detailsArea.getChildren().add(gradeLine);
            }
            double newAvg = sum / grades.size();
            avgLabel.setText(String.format("%.2f", newAvg));
        }
    }

    private Label createLabel(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setStyle("-fx-text-fill: white;");
        return label;
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
