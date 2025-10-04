package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stratégie de score basée sur les mots clés positifs.
 * Applique des bonus pour les messages contenant des mots clés importants.
 */
public class StrategieScoringMotsCle implements InterfaceScoreStrategie {
    private static final Set<String> MOTS_POSITIFS = new HashSet<>(Arrays.asList(
        "hello", "urgent", "news", "bye", "demain", "what"
    ));

    /**
     * Calcule les scores des messages en fonction des mots clés qu'ils contiennent.
     * Ajoute 2 points pour chaque mot clé positif trouvé.
     * @param messagesData Map des messages et leurs données
     */
    @Override
    public void calculerScores(final Map<InterfaceMessage, InterfaceMessageData> messagesData) {
        for (Map.Entry<InterfaceMessage, InterfaceMessageData> entry : messagesData.entrySet()) {
            InterfaceMessage message = entry.getKey();
            InterfaceMessageData messageData = entry.getValue();
            
            String contenu = message.getContent().toLowerCase();
            String[] mots = contenu.split("\\s+");
            int score = 0;

            for (String mot : mots) {
                if (MOTS_POSITIFS.contains(mot)) {
                    score += 2;
                }
            }

            messageData.setScore(messageData.getScore() + score);
        }
    }
} 
