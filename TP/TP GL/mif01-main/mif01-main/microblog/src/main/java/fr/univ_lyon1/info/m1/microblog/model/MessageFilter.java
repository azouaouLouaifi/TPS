package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Filtre les messages selon leur score et leur âge.
 * Les messages plus vieux qu'une semaine sans bonus de score sont supprimés.
 */
public class MessageFilter {
    private  int seuil = 0;



    /**
     * Définit le seuil minimal de score pour afficher un message.
     * @param seuil Le nouveau seuil
     */
    public void setSeuil(final int seuil) {
        this.seuil = seuil;
    }
    
    /**
     * Filtre les messages selon leur score et leur âge.
     * @param messages Les messages à filtrer
     * @return Les messages qui passent le filtre
     */
    public Map<InterfaceMessage, InterfaceMessageData> filterMessages(
        final Map<InterfaceMessage, InterfaceMessageData> messages) {
        Map<InterfaceMessage, InterfaceMessageData> messagesFiltres = new HashMap<>();
        final LocalDateTime now = LocalDateTime.now();
        
        messages.forEach((message, data) -> {
            // Calcule l'âge du message en jours
            long daysOld = ChronoUnit.DAYS.between(message.getDateCreation(), now);
            if (daysOld < 7 || data.getScore() > seuil) {
                messagesFiltres.put(message, data);
            }
        });
        
        return messagesFiltres;
    }
} 

