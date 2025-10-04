package fr.univ_lyon1.info.m1.microblog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import fr.univ_lyon1.info.m1.microblog.controleur.InterfaceControleur;
import fr.univ_lyon1.info.m1.microblog.view.InterfaceObservateur;

/**
 * Classe représentant un modèle de microblogging.
 * Cette classe gère les utilisateurs, les messages et leur tri, 
 * la stratégie de score utilisée pour trier les messages.
 */
public final class Y implements InterfaceModel, Sujet {
    private InterfaceControleur controleur;
    private GestionUser gestionUser;
    private GestionMessage gestionMessage;
    private List<InterfaceObservateur> observateurs = new ArrayList<>();
    private static Y instance;

    /**
     * Constructeur de la classe Y (utilisé pour les tests).
     * Permet d'initialiser le modèle avec des instances spécifiques des
     * gestionnaires
     * d'utilisateurs et de messages, afin de faciliter les tests unitaires.
     *
     * @param gu Le gestionnaire d'utilisateurs utilisé pour gérer les utilisateurs.
     * @param gm Le gestionnaire de messages utilisé pour gérer les messages.
     */
    public Y(final GestionUser gu, final GestionMessage gm) {
        this.gestionUser = gu;
        this.gestionMessage = gm;
    } 

    /*
     * Constructeur privé
     */
    private Y() {
        this.gestionUser = new GestionUser();
        this.gestionMessage = new GestionMessage();
    }

    /**
     * Retourne l'instance unique de la classe Y (Singleton).
     * @return L'instance de Y
     */
    public static Y instancier() {
        if (instance == null) {
            instance = new Y();
        }
        return instance;
    }

    /**
     * Ajouter un controleur au modèle.
     * @param controleur Le contrôleur à ajouter
     */
    @Override
    public void ajouterControleur(final InterfaceControleur controleur) {
        this.controleur = controleur;
    }

    /**
     * Créer un nouvel utilisateur.
     * @param id L'identifiant de l'utilisateur
     */
    @Override
    public void creeUtilisateur(final String id) {
        this.gestionUser.creeUser(id);
    }

    /**
     * Ajouter  un nouveau message pour tous les utilisateurs.
     * @param m Le contenu du message à ajouter
     */
    public void ajouterMessage(final String m) {
        this.gestionUser.getUsers().forEach((u, strategie) ->
            this.notifierObservateursAutre(this.gestionMessage.ajouterMessage(m,
            u.getMessagesUser(), strategie), u)
        );
    }

    /*
     * Supprimer un message
     * @param message Le message à supprimer
     */
    @Override
    public void supprimerMessage(final String message) {
        this.gestionUser.getUsers().forEach((u, strategie) ->
            this.notifierObservateursAutre(this.gestionMessage.
            supprimerMessage(u, message, strategie), u)
        ); 
    }

    /**
     * Retourner la liste des utilisateurs.
     * @return La liste des utilisateurs
     */ 
    public List<InterfaceUser> getUsers() {
        return this.gestionUser.getUsersOnly();
    }

    /**
     * Définit la stratégie de score pour les messages.
     * @param scoringStrategy La stratégie de score à utiliser
     */
    public void setScoringStrategie(final InterfaceAffichageStrategie scoringStrategy,
        final InterfaceUser user) {
        this.gestionUser.changeStrategieDisplay(user, scoringStrategy);
        this.notifierObservateursAutre(this.gestionMessage.trier(
            user.getMessagesUser(), scoringStrategy), user);
    }

    /**
     * Marque un message comme étant en favoris.
     * @param m Le message à marquer comme favori
     * @param u L'utilisateur qui marque le message
     */
    public void book(final InterfaceMessage m, final InterfaceUser u) {
        this.notifierObservateursAutre(this.gestionMessage.book(
            m, u.getMessagesUser(), this.gestionUser.getStrategyUser(u)), u);
    }

    /**
     * Retire le marquage favori d'un message.
     * @param m Le message à démarquer
     * @param u L'utilisateur concerné
     */
    public void nBook(final InterfaceMessage m, final InterfaceUser u) {
        this.notifierObservateursAutre(this.gestionMessage.nBook(
            m, u.getMessagesUser(), this.gestionUser.getStrategyUser(u)), u);
    }

    /**
     * Ajoute un observateur à la liste.
     * @param o L'observateur à ajouter
     */
    @Override
    public void ajouterObservateur(final InterfaceObservateur o) {
        this.observateurs.add(o);
        notifierObservateursAjout();
    }

    /**
     * Supprime un observateur de la liste.
     * @param o L'observateur à supprimer
     */
    @Override
    public void supprimerObservateur(final InterfaceObservateur o) {
        this.observateurs.remove(o);
    }

    /**
     * Notifie tous les observateurs pour tous les utilisateurs.
     */
    @Override
    public void notifierObservateursAjout() {
        for (InterfaceObservateur o : observateurs) {
            for (InterfaceUser u : this.getUsers()) {
                o.miseAjour(u.getMessagesUser(), u);
            }
        }
    }

    
    /**
     * Notifie les observateurs pour un utilisateur spécifique.
     * @param u L'utilisateur concerné par la notification
     */
    public void notifierObservateursAutre(final Map<InterfaceMessage,
     InterfaceMessageData> messages, final InterfaceUser u) {
        for (InterfaceObservateur o : observateurs) {                
            o.miseAjour(messages, u);
        }
    }

    /**
     * Ajouter un message avec une date.
     * @param message Le message à ajouter
     * @param date La date du message
     */
    @Override
    public void ajouterMessageDate(final String message, final LocalDateTime date) {
        this.gestionUser.getUsers().forEach((u, strategie) ->
            this.notifierObservateursAutre(this.gestionMessage.ajouterMessageAvecDate(message,
            date, u.getMessagesUser(), strategie), u)
        );
    }
}
