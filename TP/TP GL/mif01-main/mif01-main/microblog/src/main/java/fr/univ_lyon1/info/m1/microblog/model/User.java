package fr.univ_lyon1.info.m1.microblog.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente un utilisateur de l'application.
 * Chaque utilisateur a un identifiant unique et une liste de messages.
 */
public class User implements InterfaceUser {
    private String id;
    private Map<InterfaceMessage, InterfaceMessageData> messagesData = new HashMap<>();

    /**
     * Constructeur par défaut.
     * @param id L'identifiant unique de l'utilisateur
     */
    public User(final String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String getId() {
        return id;
    }

    /**
     * Retourne les messages de l'utilisateur.
     * @return Les messages de l'utilisateur
     */
    @Override
    public Map<InterfaceMessage, InterfaceMessageData> getMessagesUser() {
        return messagesData;
    }
    
    /**
     * Définit les messages de l'utilisateur.
     * @param messagesData Les messages de l'utilisateur
     */
    @Override
    public void setMessagesUser(final Map<InterfaceMessage, InterfaceMessageData> messagesData) {
        this.messagesData = messagesData;
    }
}
