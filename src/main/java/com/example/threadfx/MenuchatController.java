package com.example.threadfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
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

    public void setUsername(String username) {
        this.username = username;
    }

    //TODO : AJouter un IF else pour faire la différence des messages reçu
    //  après avoir implementer les Threads et sockets

    @FXML
    private void sendMessage() {
        String message = messageField.getText();

        if (message.isEmpty()) {
            return;
        }
        // Ajout de l'username avant le message (FAIT DANS LE CONTROLLER CAR USR DOIT êTRE UNIQUE
        TextFlow messageFlow = new TextFlow();
        messageFlow.getStyleClass().add("user-message");
        messageFlow.setTextAlignment(TextAlignment.RIGHT);

        Text usernameText = new Text(  " :" + username);
        usernameText.setStyle("-fx-font-weight: bold;");
        usernameText.setFill(Color.WHITE);

        Text messageText = new Text(message);
        messageText.setFill(Color.WHITE);

        messageFlow.getChildren().addAll(messageText, usernameText);
        messageList.getChildren().add(messageFlow);

        // Nettoyage du champs de saisie
        messageField.clear();
    }
}