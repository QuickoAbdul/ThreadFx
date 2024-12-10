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
            // Load the MenuChat scene
            FXMLLoader loader = new FXMLLoader(MenuchatApplication.class.getResource("menu-chat.fxml"));
            Parent menuChatParent = loader.load();

            // Get the controller and set the username
            MenuchatController menuChatController = loader.getController();
            menuChatController.setUsername(username);

            // Get the current stage
            Stage stage = (Stage) Username.getScene().getWindow();

            // Set the new scene
            Scene menuChatScene = new Scene(menuChatParent, 600, 400);
            stage.setScene(menuChatScene);
            stage.setTitle("Chat - " + username);
            stage.show();

        } catch (IOException e) {
            showAlert("Load Error", "Could not load chat menu");
            e.prinx²tStackTrace();
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