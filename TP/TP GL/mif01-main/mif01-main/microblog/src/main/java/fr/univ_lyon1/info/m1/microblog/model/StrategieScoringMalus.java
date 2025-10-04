package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stratégie de score basée sur la modération automatique.
 * Applique des malus pour les messages contenant des mots interdits.
 */
public class StrategieScoringMalus implements InterfaceScoreStrategie {
    private static final Set<String> MOTS_INTERDITS = new HashSet<>(Arrays.asList(
        "spam", "virus", "hack"
    ));

    /**
     * Calcule les scores des messages en fonction des mots interdits qu'ils contiennent.
     * Applique un malus de -1 point pour chaque mot interdit trouvé.
     * @param messagesData Map des messages et leurs données
     */
    @Override
    public void calculerScores(final Map<InterfaceMessage, InterfaceMessageData> messagesData) {
        for (Map.Entry<InterfaceMessage, InterfaceMessageData> entry : messagesData.entrySet()) {
            InterfaceMessage message = entry.getKey();
            InterfaceMessageData messageData = entry.getValue();
            
            String content = message.getContent().toLowerCase();
            String[] words = content.split("\\s+");
            int score = 0;

            for (String word : words) {
                if (MOTS_INTERDITS.contains(word)) {
                    score -= 1;
                }
            }

            messageData.setScore(messageData.getScore() + score);
        }
    }
} 

