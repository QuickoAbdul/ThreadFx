package com.example.threadfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class MenuchatController {
    @FXML
    private TextField messageField;

    @FXML
    private VBox messageList;

    private String username;
    private ChatClient chatClient;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;

        // Ajouter un écouteur pour les messages reçus
        chatClient.addListener(this::onMessageReceived);
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();

        if (message.isEmpty()) {
            return;
        }

        // Envoi du message au serveur via le ChatClient
        chatClient.sendMessage(username + ": " + message);

        // Nettoyer le champ de texte
        messageField.clear();
    }

    private void onMessageReceived(String message) {
        // Affichage des messages reçus dans le VBox
        displayMessage(message);
    }

    private void displayMessage(String message) {
        // Créer une VBox pour empiler les lignes (nom d'utilisateur et message)
        VBox messageBox = new VBox();

        // Séparer le message en deux parties : le nom d'utilisateur et le message
        String[] parts = message.split(":", 2);
        String usernameText = parts[0] + " :";
        String messageTexts = parts.length > 1 ? parts[1].trim() : "";

        if (message.startsWith(username + ":")) {
            // Message envoyé par l'utilisateur actuel
            Text usernameLabel = new Text(usernameText);
            usernameLabel.setStyle("-fx-font-weight: bold;");
            usernameLabel.setFill(Color.WHITE);

            Text messageContent = new Text(messageTexts);

            messageBox.getChildren().addAll(usernameLabel, messageContent);
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            messageBox.getStyleClass().add("user-message");
            usernameLabel.setFill(Color.WHITE);
        } else {
            // Message envoyé par un autre utilisateur
            Text usernameLabel = new Text(usernameText);
            usernameLabel.setStyle("-fx-font-weight: bold;");

            Text messageContent = new Text(messageTexts);

            messageBox.getChildren().addAll(usernameLabel, messageContent);
            messageBox.setAlignment(Pos.CENTER_LEFT);
            messageBox.getStyleClass().add("other-message");
        }

        Platform.runLater(() -> messageList.getChildren().add(messageBox));
    }


}
