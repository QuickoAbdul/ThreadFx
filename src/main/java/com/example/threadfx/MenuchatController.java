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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MenuchatController {
    @FXML
    private TextField messageField;

    @FXML
    private VBox messageList;

    @FXML
    private VBox onlineList;

    private String username;
    private ChatClient chatClient;

    public void initialize() {
        // Ajouter un gestionnaire pour la fermeture de la fenêtre
        Platform.runLater(() -> {
            Stage stage = (Stage) messageField.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                if (chatClient != null) {
                    try {
                        chatClient.disconnect();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;

        chatClient.addListener(new ChatClient.ChatClientListener() {
            @Override
            public void onMessageReceived(String message) {
                displayMessage(message);
            }

            @Override
            public void onUserListUpdated(List<String> users) {
                updateOnlineList(users);
            }

            @Override
            public void onSystemMessage(String message) {
                displaySystemMessage(message);
            }
        });
    }

    private void updateOnlineList(List<String> users) {
        Platform.runLater(() -> {
            onlineList.getChildren().clear();
            for (String user : users) {
                Text userText = new Text(user);
                userText.setFill(Color.BLACK);
                onlineList.getChildren().add(userText);
            }
        });
    }

    private void displaySystemMessage(String message) {
        VBox messageBox = new VBox();
        Text systemText = new Text(message);
        systemText.setFill(Color.RED);
        systemText.setStyle("-fx-font-style: italic;");

        messageBox.getChildren().add(systemText);
        messageBox.setAlignment(Pos.CENTER);

        Platform.runLater(() -> messageList.getChildren().add(messageBox));
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatClient.sendMessage(username + ": " + message);
            messageField.clear();
        }
    }

    private void displayMessage(String message) {
        VBox messageBox = new VBox();
        String[] parts = message.split(":", 2);
        String usernameText = parts[0] + " :";
        String messageTexts = parts.length > 1 ? parts[1].trim() : "";

        if (message.startsWith(username + ":")) {
            Text usernameLabel = new Text(usernameText);
            usernameLabel.setStyle("-fx-font-weight: bold;");
            usernameLabel.setFill(Color.WHITE);

            Text messageContent = new Text(messageTexts);
            messageContent.setFill(Color.WHITE);

            messageBox.getChildren().addAll(usernameLabel, messageContent);
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            messageBox.getStyleClass().add("user-message");
        } else {
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
