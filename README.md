# AutoLoc

Plateforme de gestion de location de véhicules multi-agences — projet réalisé dans le cadre du module **UP ASI — Architecture des Systèmes d'Information**.

## Objectifs du projet

AutoLoc a pour but de digitaliser la gestion d'un réseau d'agences de location de véhicules : suivi du parc automobile, réservations, contrats de location, facturation et reporting, avec une gestion centralisée mais multi-agences.

Objectifs principaux :
- Permettre aux clients de rechercher et réserver un véhicule disponible dans l'agence de leur choix
- Permettre aux agents d'agence de gérer les contrats de location (départ, retour, état du véhicule)
- Permettre aux responsables d'agence de superviser le parc et l'activité de leur agence
- Permettre à l'administrateur de gérer les utilisateurs, les agences et les paramètres globaux de la plateforme

## Acteurs identifiés

| Acteur | Rôle |
|---|---|
| **Client** | Recherche un véhicule, effectue une réservation, consulte l'historique et les factures de ses locations |
| **Agent d'agence** | Gère les contrats de location au quotidien (enregistrement, départ/retour de véhicule, état des lieux) |
| **Responsable d'agence** | Supervise le parc de véhicules et l'activité de son agence, valide les cas particuliers |
| **Administrateur** | Gère les comptes utilisateurs, les agences, et la configuration générale de la plateforme |

## Cas d'utilisation (première identification)

- Rechercher un véhicule disponible (Client)
- Réserver un véhicule (Client)
- Consulter l'historique de ses locations (Client)
- Enregistrer un départ / retour de véhicule (Agent d'agence)
- Créer un contrat de location (Agent d'agence)
- Consulter le tableau de bord de l'agence (Responsable d'agence)
- Gérer le parc de véhicules de l'agence (Responsable d'agence)
- Gérer les comptes utilisateurs (Administrateur)
- Gérer les agences (Administrateur)

## Stack technique

Voir le guide de mise en place de l'environnement (Atelier 0) pour le détail : Java 17, Spring Boot, Spring Data JPA, MySQL, Maven, IntelliJ IDEA Ultimate, Postman.

## Environnement de développement

L'environnement de développement (JDK 17, IntelliJ IDEA Ultimate, MySQL, Postman, Git) a été mis en place suivant le guide de l'Atelier 0. Voir capture d'écran jointe au dépôt.
