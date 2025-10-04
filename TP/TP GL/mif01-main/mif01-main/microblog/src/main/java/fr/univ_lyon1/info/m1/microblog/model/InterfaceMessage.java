package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;

/**
 * Interface représentant un message dans le modèle.
 */
public interface InterfaceMessage {

    /**
     * Retourne le contenu du message.
     * @return Le contenu du message
     */
    String getContent();

    /**
     * Retourne la date de création du message.
     * @return La date de création
     */
    LocalDateTime getDateCreation();
}
