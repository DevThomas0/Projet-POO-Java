# Projet : Gestion du Restaurant "La Bella Tavola"
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Table des Matières

1. [Introduction](#introduction)
2. [Contexte et Objectifs](#contexte-et-objectifs)
3. [Fonctionnalités](#fonctionnalités)
4. [Technologies Utilisées](#technologies-utilisées)
5. [Structure du Projet](#structure-du-projet)
6. [Installation et Lancement](#installation-et-lancement)
7. [Utilisation](#utilisation)
8. [Milestones et Tâches](#milestones-et-tâches)
9. [Contribution](#contribution)
10. [Licence](#licence)

## Introduction

> **Note :** Ce projet est une application de gestion pour un restaurant italien.

Le projet "La Bella Tavola" vise à moderniser la gestion du restaurant en automatisant les tâches administratives et en améliorant l'expérience client.

## Contexte et Objectifs

> **Warning :** L'application doit être robuste et intuitive.

Le restaurant "La Bella Tavola" souhaite améliorer sa gestion en développant une application qui prend en charge le menu, les commandes, le stock d’ingrédients, et les employés. L'application doit être connectée à une base de données MySQL.

## Fonctionnalités

*   Gestion du menu : ajout, modification, et suppression de plats.
*   Gestion des commandes : prise de commande, envoi en cuisine, et calcul automatique du total.
*   Gestion des stocks : mise à jour automatique du stock après une commande, vérification de la disponibilité des ingrédients.
*   Gestion des employés : gestion des informations sur les employés, stockage en base de données.

## Technologies Utilisées

*   Langage de programmation : Java
*   Base de données : MySQL
*   Bibliothèque JDBC pour la connexion à la base de données
*   Interface graphique : JavaFX (pour le bonus)

## Structure du Projet

*   `src` : code source de l'application
*   `database` : scripts de création de la base de données
*   `docs` : documentation du projet

## Installation et Lancement

1.  Cloner le dépôt GitHub : `git clone https://github.com/votre-nom/la-bella-tavola.git`
2.  Créer la base de données MySQL en exécutant les scripts dans le dossier `database`.
3.  Configurer les paramètres de connexion à la base de données dans le fichier `config.properties`.
4.  Compiler et exécuter l'application : `mvn clean package` puis `java -jar target/la-bella-tavola.jar`

## Utilisation

1.  Lancer l'application et se connecter à la base de données.
2.  Utiliser le menu interactif pour gérer le menu, les commandes, les stocks, et les employés.

## Milestones et Tâches

Le projet est divisé en trois milestones :

*   **Milestone 1** : Mise en place des fonctionnalités de base (menu, commandes).
*   **Milestone 2** : Gestion avancée (stocks, employés, base de données).
*   **Milestone 3** : Finalisation et fonctionnalités avancées (menu interactif, optimisation des requêtes SQL).

Chaque milestone est composé de plusieurs tâches détaillées dans le Trello.

## Contribution

> **Info :** Merci à mes camarades pour leur collaborations (Lam,Hasham et Sarah) !
## Licence

Ce projet est sous licence MIT. Vous êtes libre de l'utiliser, de le modifier, et de le distribuer sous les termes de cette licence.

### Bonus : Interface Graphique

> **Félicitations :** Nous avons réalisé le bonus en ajoutant une interface graphique à l'application en utilisant JavaFX ! Cette interface permet une interaction plus intuitive et conviviale avec l'application. Nous sommes fiers d'avoir pu aller au-delà des exigences initiales et d'avoir amélioré l'expérience utilisateur.