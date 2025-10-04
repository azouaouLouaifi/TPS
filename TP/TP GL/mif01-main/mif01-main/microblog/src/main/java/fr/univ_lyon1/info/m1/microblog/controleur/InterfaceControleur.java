package fr.univ_lyon1.info.m1.microblog.controleur;

import fr.univ_lyon1.info.m1.microblog.model.InterfaceAffichageStrategie;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceMessage;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceModel;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import fr.univ_lyon1.info.m1.microblog.view.InterfaceView;

/**
 * Interface représentant un contrôleur dans le modèle MVC.
 * Le contrôleur gère les interactions entre les vues et le modèle.
 */
public interface InterfaceControleur {

    /**
     * Ajoute une vue au contrôleur.
     * 
     * @param view La vue à ajouter.
     */
    void ajouterVue(InterfaceView view);

    /**
     * Ajoute un modèle au contrôleur.
     * 
     * @param model Le modèle à ajouter.
     */
    void ajouterModel(InterfaceModel model);

    /**
     * Notifie le modèle de l'ajout d'un message.
     * 
     * @param t Le texte du message à ajouter.
     */
    void notifierAjoutMsg(String t);

    /**
     * Notifie le modèle qu'un message a été marqué comme favori.
     * 
     * @param m Le message à marquer comme favori.
     * @param u L'utilisateur qui marque le message comme favori.
     */
    void notifierB(InterfaceMessage m, InterfaceUser u);

    /**
     * Notifie le modèle qu'un message a été retiré des favoris.
     * 
     * @param m Le message à retirer des favoris.
     * @param u L'utilisateur qui retire le message des favoris.
     */
    void notifierNB(InterfaceMessage m, InterfaceUser u);

    /**
     * Initialise le contrôleur et les vues associées.
     */
    void initialisation();

    /**
     * Modifie la stratégie de score pour un utilisateur.
     * 
     * @param strategy La nouvelle stratégie de score.
     * @param user L'utilisateur pour qui la stratégie est modifiée.
     */
    void modifierStrategie(InterfaceAffichageStrategie strategy, InterfaceUser user);

    /**
     * Supprime un message du modèle.
     * 
     * @param message Le message à supprimer.
     */
    void supprimerMessage(String message);
}
