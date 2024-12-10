package com.example.threadfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MenuchatController {
    @FXML
    private TextField messageField;

    @FXML
    private VBox messageList;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();

        if (message.isEmpty()) {
            return;
        }

        // Create a text flow to add username to the message
        TextFlow messageFlow = new TextFlow();
        Text usernameText = new Text(username + ": ");
        usernameText.setStyle("-fx-font-weight: bold;");
        Text messageText = new Text(message);

        messageFlow.getChildren().addAll(usernameText, messageText);
        messageList.getChildren().add(messageFlow);

        // Clear the message field
        messageField.clear();
    }
}