package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Map;

/**
 * Interface représentant un utilisateur dans le modèle.
 */
public interface InterfaceUser {

    /**
     * Retourne l'identifiant de l'utilisateur.
     * @return L'identifiant de l'utilisateur
     */
    String getId();

    /**
     * Retourne la map des messages de l'utilisateur.
     * @return La map des messages et leurs données
     */
    Map<InterfaceMessage, InterfaceMessageData> getMessagesUser();

    /**
     * Définit la map des messages de l'utilisateur.
     * @param messagesData La nouvelle map des messages
     */
    void setMessagesUser(Map<InterfaceMessage, InterfaceMessageData> messagesData);
}
