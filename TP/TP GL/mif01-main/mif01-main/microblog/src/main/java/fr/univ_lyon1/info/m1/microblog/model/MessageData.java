package fr.univ_lyon1.info.m1.microblog.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Données associées à un message pour un utilisateur particulier.
 */
public class MessageData implements InterfaceMessageData {
    private boolean isBookmarked = false;
    private int score = -1;
    private Set<String> words = new HashSet<>();

    /**
     * Retourne l'ensemble des mots associés au message.
     * @return L'ensemble des mots
     */
    public Set<String> getWords() {
        return words;
    }

    /**
     * Définit l'ensemble des mots associés au message.
     * @param words L'ensemble des mots
     */
    public void setWords(final Set<String> words) {
        this.words = words;
    }

    /**
     * Retourne le score du message.
     * @return Le score
     */
    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * Vérifie si le message est marqué comme favori.
     * @return true si le message est marqué comme favori, false sinon
     */
    public boolean isBookmarked() {
        return isBookmarked;
    }

    /**
     * Définit si le message est marqué comme favori.
     * @param bookmarked true pour marquer comme favori, false sinon
     */
    public void setBookmarked(final boolean bookmarked) {
        this.isBookmarked = bookmarked;
    }

    /**
     * Compare ce MessageData avec un autre.
     * @param autre L'autre MessageData à comparer
     * @return Un entier négatif, zéro ou positif selon la comparaison
     */
    public int compare(final InterfaceMessageData autre) {
        return Integer.compare(this.score, autre.getScore());
    }
}
