# Application de Chat avec Sockets TCP

## Description
Ce projet consiste à développer une application de chat centralisée basée sur les sockets TCP. Elle permet à plusieurs utilisateurs de communiquer via une interface graphique simple et intuitive. Le serveur gère les connexions des clients et assure la diffusion des messages dans un salon de discussion partagé.

## Fonctionnalités
- **Connexion utilisateur** : Les utilisateurs peuvent se connecter en saisissant un pseudo unique.
- **Salon de discussion** : Visualisation des messages envoyés par tous les utilisateurs connectés.
- **Envoi de messages** : Possibilité d'envoyer des messages qui seront visibles par tous les utilisateurs connectés.
- **Déconnexion** : Gestion propre de la déconnexion d'un utilisateur avec notification aux autres utilisateurs.

## Architecture
### Composants principaux
1. **Serveur** :
    - Gère les connexions des clients via des sockets TCP.
    - Maintient une liste des clients connectés.
    - Utilise un thread par client pour gérer les communications.
2. **Client** :
    - Établit une connexion avec le serveur.
    - Permet la saisie et l'envoi de messages.
    - Affiche les messages reçus.

## Installation et Exécution

### Prérequis
- Java JDK 8 ou supérieur
- Un IDE comme IntelliJ IDEA, Eclipse, ou NetBeans

### Instructions
1. Clonez ce repository :
   ```bash
   git clone <URL_DU_REPOSITORY>
   ```
2. Importez le projet dans votre IDE.
3. Compilez et exécutez les classes du serveur et du client :
    - **Serveur** : Exécutez `Server.java`.
    - **Client** : Exécutez `Client.java`.
4. Connectez plusieurs clients en lançant plusieurs instances de l'application.

## Utilisation
1. Lancez le serveur sur une machine.
2. Connectez un ou plusieurs clients en entrant l'adresse IP et le port du serveur.
3. Profitez du chat en envoyant et recevant des messages.

## Auteurs
- Étudiants INSA HDF 2024, cours "Architecture des applications Internet".

## Ressources
- [Documentation officielle Java](https://docs.oracle.com/en/java/)
- [Tutoriels YouTube](https://www.youtube.com/playlist?list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev)

---

*Pour toute question, veuillez contacter votre enseignant ou vous référer à la documentation fournie.*
