package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;

/**
 * Représente un message avec son contenu et sa date de création.
 */
public class Message implements InterfaceMessage {
    private String content;
    private LocalDateTime dateCreation;

    /**
     * Constructeur de la classe Message.
     * @param content Le contenu du message
     * @param dateCreation La date de création du message
     */
    public Message(final String content, final LocalDateTime dateCreation) {
        this.content = content;
        this.dateCreation = dateCreation;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
}
