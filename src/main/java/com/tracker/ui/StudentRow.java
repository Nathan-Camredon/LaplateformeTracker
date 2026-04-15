package com.tracker.ui;

import com.tracker.model.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.SVGPath;

/**
 * Composant personnalisé représentant une ligne d'étudiant dans le tableau de bord.
 */
public class StudentRow extends HBox {

    public StudentRow(Student student, Runnable onEdit, Runnable onDelete) {
        super(10);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10, 40, 10, 40));

        // Création des colonnes de texte
        Label idLabel = createLabel(String.valueOf(student.getId()), 60);
        Label fnLabel = createLabel(student.getFirstName(), 120);
        HBox.setHgrow(fnLabel, Priority.ALWAYS);
        Label lnLabel = createLabel(student.getLastName(), 120);
        HBox.setHgrow(lnLabel, Priority.ALWAYS);
        Label ageLabel = createLabel(String.valueOf(student.getAge()), 60);
        Label avgLabel = createLabel(String.format("%.2f", student.getAverage()), 80);

        // Boutons d'action
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        actions.setPrefWidth(100);

        Button editBtn = createIconButton("M3,17.25 V21 H6.75 L17.81,9.94 L14.06,6.19 L3,17.25 M20.71,7.04 L16.96,3.29 L15.13,5.12 L18.88,8.87 L20.71,7.04 Z", "pen-icon");
        editBtn.setOnAction(e -> onEdit.run());
        
        Button deleteBtn = createIconButton("M6,19 c0,1.1 0.9,2 2,2 h8 c1.1,0 2,-0.9 2,-2 V7 H6 v12 z M19,4 h-3.5 l-1,-1 h-5 l-1,1 H5 v2 h14 V4 z", "trash-icon");
        deleteBtn.setOnAction(e -> onDelete.run());

        actions.getChildren().addAll(editBtn, deleteBtn);
        this.getChildren().addAll(idLabel, fnLabel, lnLabel, ageLabel, avgLabel, actions);
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
