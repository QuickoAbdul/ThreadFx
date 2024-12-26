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

    private ChatClient chatClient;

    @FXML
    protected void onJoinBtn() {
        String username = Username.getText().trim();

        if (username.isEmpty()) {
            showAlert("Username Error", "Please enter a username");
            return;
        }

        try {
            // Créer une instance de ChatClient et se connecter au serveur
            chatClient = new ChatClient();
            chatClient.connect();
            chatClient.sendMessage(username); // Envoi du nom d'utilisateur au serveur

            // Charger la nouvelle scène du chat
            FXMLLoader loader = new FXMLLoader(MenuchatApplication.class.getResource("menu-chat.fxml"));
            Parent menuChatParent = loader.load();

            // Appliquer la méthode setUsername dans le contrôleur MenuChat
            MenuchatController menuChatController = loader.getController();
            menuChatController.setUsername(username);
            menuChatController.setChatClient(chatClient);  // Lier le ChatClient

            // Récupérer le Stage existant et remplacer la scène
            Stage stage = (Stage) Username.getScene().getWindow();
            stage.setScene(new Scene(menuChatParent, 600, 400));
            stage.setTitle("Chat - " + username);

            // Réinitialiser le champ de texte
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
