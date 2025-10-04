package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stratégie de calcul de score basée sur les mots des messages favoris.
 * Attribue des points aux messages qui contiennent des mots présents dans les messages favoris.
 */
public class StrategieScoringBookmark implements InterfaceScoreStrategie {

    /**
     * Calcule les scores des messages en fonction des mots présents dans les messages favoris.
     * Ajoute 1 point pour chaque mot commun avec un message favori.
     * @param messagesData Map des messages et leurs données
     */
    @Override
    public void calculerScores(final Map<InterfaceMessage, InterfaceMessageData> messagesData) {
        Set<InterfaceMessage> messages = messagesData.keySet();
        Set<String> bookmarkedWords = new HashSet<>();

        messages.forEach((InterfaceMessage m) -> {
            InterfaceMessageData d = messagesData.get(m);
            String[] wordsArray = m.getContent().toLowerCase().split("[^\\p{Alpha}]+");
            Set<String> words = new HashSet<>(Arrays.asList(wordsArray));
            d.setWords(words);
            if (d.isBookmarked()) {
                bookmarkedWords.addAll(words);
            }
        });

        messages.forEach((InterfaceMessage m) -> {
            InterfaceMessageData d = messagesData.get(m);
            int score = 0;
            for (String word : d.getWords()) {
                if (bookmarkedWords.contains(word)) {
                    score++;
                }
            }
            d.setScore(score);
        });
    }
}
