package fr.univ_lyon1.info.m1.microblog.view;

import java.util.Map;

import fr.univ_lyon1.info.m1.microblog.model.InterfaceMessageData;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceMessage;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import fr.univ_lyon1.info.m1.microblog.model.Sujet;

/**
 * Interface pour un observateur.
 */
public interface InterfaceObservateur {

    /**
     * Met à jour l'observateur avec les nouvelles données des messages de l'utilisateur.
     * @param m Les nouvelles données des messages.
     * @param u utilisateur.
     */ 
     void miseAjour(Map<InterfaceMessage, InterfaceMessageData> m, InterfaceUser u);

     /**
      * Retourne le sujet observé.
      * @return Le sujet.
      */
     Sujet getSujet();

     /**
      * Définit le sujet observé.
      * @param sujet Le sujet à définir.
      */
     void setSujet(Sujet sujet);
}
