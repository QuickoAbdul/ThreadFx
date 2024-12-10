package com.example.threadfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField Username;

    @FXML
    protected void onJoinBtn() {
        String username = Username.getText().trim();

        if (username.isEmpty()) {
            showAlert("Username Error", "Please enter a username");
            return;
        }

        try {
            // Charger la nouvelle scène à partir du fichier FXML
            FXMLLoader loader = new FXMLLoader(MenuchatApplication.class.getResource("menu-chat.fxml"));
            Parent menuChatParent = loader.load();

            // Appliquer la méthode SetUsername dans le contrôleur MenuChat
            MenuchatController menuChatController = loader.getController();
            menuChatController.setUsername(username);

            // Récupérer le Stage existant et remplacer la scène
            Stage stage = (Stage) Username.getScene().getWindow();
            stage.setScene(new Scene(menuChatParent, 600, 400));
            stage.setTitle("Chat - " + username);

            // Réinitialisation des ressources de l'ancienne scène
            Username.clear();

        } catch (IOException e) {
            showAlert("Load Error", "Could not load chat menu");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}