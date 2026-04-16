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

public class StudentRow extends javafx.scene.layout.VBox {

    private boolean isExpanded = false;
    private javafx.scene.layout.VBox detailsArea;

    public StudentRow(Student student, Runnable onEdit, Runnable onDelete) {
        super();
        this.setStyle("-fx-background-color: transparent;");

        // --- Main Row ---
        HBox mainRow = new HBox(10);
        mainRow.setAlignment(Pos.CENTER_LEFT);
        mainRow.setPadding(new Insets(10, 40, 10, 40));




        // Création des colonnes de texte
        Label idLabel = createLabel(String.valueOf(student.getId()), 60);
        Label fnLabel = createLabel(student.getFirstName(), 120);
        HBox.setHgrow(fnLabel, Priority.ALWAYS);
        Label lnLabel = createLabel(student.getLastName(), 120);
        HBox.setHgrow(lnLabel, Priority.ALWAYS);
        Label ageLabel = createLabel(String.valueOf(student.getAge()), 60);
        Label avgLabel = createLabel(String.format("%.2f", student.getAverage()), 80);




        // Buttons
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        actions.setPrefWidth(100);

        Button editBtn = createIconButton("M3,17.25 V21 H6.75 L17.81,9.94 L14.06,6.19 L3,17.25 M20.71,7.04 L16.96,3.29 L15.13,5.12 L18.88,8.87 L20.71,7.04 Z", "pen-icon");
        editBtn.setOnAction(e -> { e.consume(); onEdit.run(); });
        
        Button deleteBtn = createIconButton("M6,19 c0,1.1 0.9,2 2,2 h8 c1.1,0 2,-0.9 2,-2 V7 H6 v12 z M19,4 h-3.5 l-1,-1 h-5 l-1,1 H5 v2 h14 V4 z", "trash-icon");
        deleteBtn.setOnAction(e -> { e.consume(); onDelete.run(); });

        actions.getChildren().addAll(editBtn, deleteBtn);
        mainRow.getChildren().addAll(idLabel, fnLabel, lnLabel, ageLabel, avgLabel, actions);






        // --- Details Sub-menu (Grades) ---
        detailsArea = new javafx.scene.layout.VBox(5);
        detailsArea.setPadding(new Insets(10, 40, 10, 100)); // Indented
        detailsArea.setStyle("-fx-background-color: rgba(255, 255, 255, 0.05); -fx-border-color: rgba(255,255,255,0.1); -fx-border-width: 1 0 0 0;");
        detailsArea.setVisible(false);
        detailsArea.setManaged(false);

        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Notes de l'étudiant :");
        title.setStyle("-fx-text-fill: #aaaaaa; -fx-font-style: italic;");
        
        Button addGradeBtn = new Button("+");
        addGradeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        addGradeBtn.setOnAction(e -> showAddGradeForm(student.getId()));
        
        headerBox.getChildren().addAll(title, addGradeBtn);
        detailsArea.getChildren().add(headerBox);

        // Toggle on click
        mainRow.setOnMouseClicked(e -> toggleDetails(student.getId()));
        this.getChildren().addAll(mainRow, detailsArea);
    }

    private void showAddGradeForm(int studentId) {
        // Supprime l'ancien formulaire s'il existe déjà
        detailsArea.getChildren().removeIf(node -> "addForm".equals(node.getId()));

        HBox addForm = new HBox(10);
        addForm.setId("addForm");
        addForm.setAlignment(Pos.CENTER_LEFT);
        addForm.setPadding(new Insets(5, 0, 5, 0));

        TextField subjectField = new TextField();
        subjectField.setPromptText("Matière (ex: Math)");
        subjectField.setPrefWidth(120);

        TextField valueField = new TextField();
        valueField.setPromptText("Note (0-20)");
        valueField.setPrefWidth(80);

        Button saveBtn = new Button("Sauvegarder");
        saveBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        saveBtn.setOnAction(e -> {
            try {
                int val = Integer.parseInt(valueField.getText());
                String sub = subjectField.getText();
                if (!sub.isEmpty()) {
                    new GradeRequest().addGrade(val, sub, studentId);
                    loadGrades(studentId); // Rafraîchir la liste
                }
            } catch (NumberFormatException ex) {
                // Ignore invalid input or display error
            }
        });

        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 3px;");
        cancelBtn.setOnAction(e -> detailsArea.getChildren().remove(addForm));

        addForm.getChildren().addAll(subjectField, valueField, saveBtn, cancelBtn);
        detailsArea.getChildren().add(1, addForm); // Ajoute juste sous le header
    }

    private void toggleDetails(int studentId) {
        isExpanded = !isExpanded;
        detailsArea.setVisible(isExpanded);
        detailsArea.setManaged(isExpanded);

        if (isExpanded) {
            loadGrades(studentId);
        }
    }

    private void loadGrades(int studentId) {
        // Enlève l'ancien contenu (sauf le header et l'éventuel form)
        detailsArea.getChildren().removeIf(node -> "gradeItem".equals(node.getId()) || "noGrades".equals(node.getId()));
        
        List<Grade> grades = new GradeRequest().getGradesByStudentId(studentId);
        
        if (grades.isEmpty()) {
            Label noGrades = new Label("• Aucune note trouvée.");
            noGrades.setId("noGrades");
            noGrades.setStyle("-fx-text-fill: white;");
            detailsArea.getChildren().add(noGrades);
        } else {
            for (Grade g : grades) {
                Label gLabel = new Label("• " + g.getSubject() + " : " + g.getValue());
                gLabel.setId("gradeItem");
                gLabel.setStyle("-fx-text-fill: white;");
                detailsArea.getChildren().add(gLabel);
            }
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
