package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;
import java.util.List;

import fr.univ_lyon1.info.m1.microblog.controleur.InterfaceControleur;

/**
 * Interface représentant le modèle de l'application.
 * Cette interface définit les méthodes nécessaires pour interagir avec les
 *  données de l'application,
 * telles que la gestion des utilisateurs, des messages et la stratégie de score.
 */
public interface InterfaceModel {

    /**
     * Ajoute un contrôleur à l'application.
     * Cette méthode permet d'associer un contrôleur au modèle.
     *
     * @param controleur Le contrôleur à ajouter.
     */
    void ajouterControleur(InterfaceControleur controleur);

    /**
     * Crée un nouvel utilisateur avec l'identifiant spécifié.
     * 
     * @param id L'identifiant de l'utilisateur à créer.
     */
    void creeUtilisateur(String id);

    /**
     * Retourne la liste des utilisateurs.
     * 
     * @return La liste des utilisateurs de l'application.
     */
    List<InterfaceUser> getUsers();

    /**
     * Ajoute un nouveau message.
     * @param message Le contenu du message à ajouter
     */
    void ajouterMessage(String message);

    /**
     * Ajoute un nouveau message avec une date spécifique.
     * @param message Le contenu du message à ajouter
     * @param date La date de création du message
     */
    void ajouterMessageDate(String message, LocalDateTime date);

    /**
     * Marque un message comme favori pour un utilisateur.
     * @param message Le message à marquer
     * @param user L'utilisateur qui marque le message
     */
    void book(InterfaceMessage message, InterfaceUser user);

    /**
     * Retire le marquage favori d'un message pour un utilisateur.
     * @param message Le message à démarquer
     * @param user L'utilisateur concerné
     */
    void nBook(InterfaceMessage message, InterfaceUser user);

    /**
     * Définit la stratégie de score à utiliser.
     * @param strategy La stratégie de score à appliquer
     */
    void setScoringStrategie(InterfaceAffichageStrategie strategy, InterfaceUser user);

    /**
     * Supprime un message du modèle.
     * @param message Le message à supprimer
     */
    void supprimerMessage(String message);
}
