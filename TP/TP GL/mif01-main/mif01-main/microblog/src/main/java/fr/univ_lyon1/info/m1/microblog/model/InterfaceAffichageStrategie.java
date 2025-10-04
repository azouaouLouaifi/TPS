package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Map;   

/**
 * Interface définissant une stratégie d'affichage des messages.
 */
public interface InterfaceAffichageStrategie {
    /**
     * Applique la stratégie d'affichage aux messages.
     * @param messages Les messages à traiter
     * @return Les messages triés et filtrés selon la stratégie
     */
    Map<InterfaceMessage, InterfaceMessageData> appliquer(
        Map<InterfaceMessage, InterfaceMessageData> messages);

    /**
     * Retourne une description de la stratégie pour l'interface utilisateur.
     * @return La description de la stratégie
     */
    String toString();

    /**
     * Modifie le seuil de filtrage des messages.
     * @param seuil Le nouveau seuil de filtrage
     */ 
     void modifierSeuil(int seuil);

} 
