package com.tracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private StackPane contentArea;

    private StudentController studentController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Charge le tableau de bord par défaut au démarrage
        loadView("Dashboard.fxml");
        
        if (studentController != null) {
            studentController.refreshFromDatabase();
        }
    }

    /**
     * Ouvre la fenêtre d'ajout d'étudiant.
     */
    @FXML
    private void handleAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/AddStudentPopup.fxml"));
            Parent root = loader.load();
            
            AddStudentController controller = loader.getController();
            
            // On rafraîchit la liste si l'ajout est réussi
            controller.setOnSuccessCallback(() -> {
                if (studentController != null) {
                    studentController.refreshFromDatabase();
                }
            });

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Étudiant");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contentArea.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre la fenêtre de recherche avancée.
     */
    @FXML
    private void handleAdvancedSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/SearchPopup.fxml"));
            Parent root = loader.load();
            
            SearchController searchController = loader.getController();
            
            // Lie les résultats de recherche au tableau de bord
            searchController.setOnSearchCallback(results -> {
                if (studentController != null) {
                    studentController.refreshList(results);
                }
            });

            Stage stage = new Stage();
            stage.setTitle("Recherche Avancée");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contentArea.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge une vue FXML dans la zone centrale de l'application.
     */
    public void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tracker/ressources/" + fxmlFile));
            Node node = loader.load();
            
            if (fxmlFile.equals("Dashboard.fxml")) {
                this.studentController = loader.getController();
            }
            
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Déconnecte l'utilisateur et retourne à la page de connexion.
     */
    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/tracker/ressources/Login.fxml"));
            Stage stage = (Stage) contentArea.getScene().getWindow();
            
            // On remplace la racine de la scène actuelle par la vue de Login
            stage.getScene().setRoot(root);
            
            // Optionnel : on peut redimensionner la fenêtre pour le login si besoin
            stage.setWidth(1000);
            stage.setHeight(700);
            stage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
