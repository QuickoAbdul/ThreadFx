package com.example.threadfx;

import javafx.application.Platform;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    private Socket socket;
    private PrintWriter serverOutput;
    private BufferedReader serverInput;
    private List<ChatClientListener> listeners = new ArrayList<>();
    private List<String> onlineUsers = new ArrayList<>();


    // Interface pour recevoir les messages
    public interface ChatClientListener {
        void onMessageReceived(String message);
        void onUserListUpdated(List<String> users);
        void onSystemMessage(String message); // Pour les messages de connexion/déconnexion
    }


    // Méthode pour ajouter un écouteur
    public void addListener(ChatClientListener listener) {
        listeners.add(listener);
    }

    // Connexion au serveur
    public void connect() throws IOException {
        socket = new Socket(HOST, PORT);
        serverOutput = new PrintWriter(socket.getOutputStream(), true);
        serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(() -> {
            try {
                String message;
                while ((message = serverInput.readLine()) != null) {
                    if (message.startsWith("USERLIST:")) {
                        // Message de mise à jour de la liste des utilisateurs
                        String[] users = message.substring(9).split(",");
                        onlineUsers = Arrays.asList(users);
                        for (ChatClientListener listener : listeners) {
                            List<String> userList = onlineUsers;
                            Platform.runLater(() -> listener.onUserListUpdated(userList));
                        }
                    } else if (message.startsWith("SYSTEM:")) {
                        // Message système (connexion/déconnexion)
                        String systemMessage = message.substring(7);
                        for (ChatClientListener listener : listeners) {
                            String finalMessage = systemMessage;
                            Platform.runLater(() -> listener.onSystemMessage(finalMessage));
                        }
                    } else {
                        // Message normal
                        for (ChatClientListener listener : listeners) {
                            String finalMessage = message;
                            Platform.runLater(() -> listener.onMessageReceived(finalMessage));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Envoi d'un message au serveur
    public void sendMessage(String message) {
        if (serverOutput != null) {
            serverOutput.println(message);
        }
    }

    // Déconnexion du serveur
    public void disconnect() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
