package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.view.InterfaceObservateur;
import java.util.Map;
/**
 * Interface représentant le sujet (observable).
 */
public interface Sujet {

    /**
     * Ajoute un observateur à la liste des observateurs du sujet.
     * @param o L'observateur à ajouter
     */
    void ajouterObservateur(InterfaceObservateur o);

    /**
     * Supprime un observateur de la liste des observateurs du sujet.
     * @param o L'observateur à supprimer
     */
    void supprimerObservateur(InterfaceObservateur o);

    /**
    * Notifie tous les observateurs des changements dans le sujet.
    */
    void notifierObservateursAjout();

    /**
     * Notifie les observateurs des changements pour un utilisateur spécifique.
     * @param u L'utilisateur concerné par la notification
     */
    void notifierObservateursAutre(Map<InterfaceMessage, InterfaceMessageData> messages, 
     InterfaceUser u);
}
