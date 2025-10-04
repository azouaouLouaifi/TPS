package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Stratégie de score basée sur la récence des messages.
 * Attribue des bonus aux messages récents :
 * - +1 point pour les messages de moins de 24 heures
 * - +1 point pour les messages de moins de 7 jours
 */
public class StrategieScoringRecent implements InterfaceScoreStrategie {

    /**
     * Calcule les scores des messages en fonction de leur récence.
     * @param messagesData Map des messages et leurs données
     */
    @Override
    public void calculerScores(final Map<InterfaceMessage, InterfaceMessageData> messagesData) {
        LocalDateTime dateActuelle = LocalDateTime.now();

        for (Map.Entry<InterfaceMessage, InterfaceMessageData> entry : messagesData.entrySet()) {
            InterfaceMessage message = entry.getKey();
            InterfaceMessageData messageData = entry.getValue();

            LocalDateTime messageTime = message.getDateCreation();
            long heures = ChronoUnit.HOURS.between(messageTime, dateActuelle);
            long jours = ChronoUnit.DAYS.between(messageTime, dateActuelle);
            int bonus = 0;
            if (heures < 24) {
                bonus = 1;
            }
            if (jours < 7) {
                bonus += 1;
            }
            messageData.setScore(messageData.getScore() + bonus);
        }
    }
}
