package com.example.threadfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Mettre à jour la liste des utilisateurs pour tous les clients
            updateUserList();

            // Demander le nom d'utilisateur
            out.println("Entrez votre nom d'utilisateur :");
            username = in.readLine();

            // Envoyer un message système pour la connexion
            broadcastSystemMessage(username + " a rejoint le chat");

            // Mettre à jour la liste des utilisateurs pour tous les clients
            updateUserList();

            // Gestion des messages
            String message;
            while ((message = in.readLine()) != null) {
                if ("/quit".equalsIgnoreCase(message)) break;
                
                broadcastMessageToOthers(username,message);
            }

        } catch (IOException e) {
            System.err.println("Erreur de communication : " + e.getMessage());
        } finally {
            // Gestion de la déconnexion
            Server.clients.remove(this);
            broadcastSystemMessage(username + " a quitté le chat");
            updateUserList();
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Erreur de fermeture de socket : " + e.getMessage());
            }
        }
    }
    private void updateUserList() {
        String userList = "USERLIST:" + Server.clients.stream()
                .map(client -> client.username)
                .collect(Collectors.joining(","));
        for (ClientHandler client : Server.clients) {
            client.out.println(userList);
        }
    }


    private void broadcastSystemMessage(String message) {
        for (ClientHandler client : Server.clients) {
            client.out.println("SYSTEM:" + message);
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : Server.clients) {
            client.out.println(message);
        }
    }

    private void broadcastMessageToOthers(String sender, String message) {
        for (ClientHandler client : Server.clients) {
            if (client.username.equals(sender)) {
                // Message pour l'expéditeur
                client.out.println(message);
            } else {
                // Messages pour les autres clients
                client.out.println(message);
            }
        }
    }
}