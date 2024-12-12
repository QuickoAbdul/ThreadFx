package com.example.threadfx;


import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            
            System.out.println("Nouveau client connecte...");
            
            // Préparation des flux d'entrée/sortie
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);

            // Thread de réception des messages
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = serverInput.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Connexion au serveur interrompue.");
                }
            });
            receiveThread.start();

            // Lecture et envoi des messages
            String message;
            
            while ((message = consoleInput.readLine()) != null) {
                serverOutput.println(message);
                if ("/quit".equalsIgnoreCase(message)) {
                    break;
                }
            }

            socket.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Impossible de se connecter au serveur : " + e.getMessage());
        }
    }
}