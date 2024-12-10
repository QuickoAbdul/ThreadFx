package com.example.threadfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuchatController {
    @FXML
    private TextField messageField;

    @FXML
    private VBox messageList;

    @FXML
    private void sendMessage() {
        String message = messageField.getText();

        if (message.isEmpty()) {
            return;
        }

        // Ajouter le message à la liste
        Text messageText = new Text(message);
        messageList.getChildren().add(messageText);

        // Effacer le champ de saisie
        messageField.clear();
    }
}