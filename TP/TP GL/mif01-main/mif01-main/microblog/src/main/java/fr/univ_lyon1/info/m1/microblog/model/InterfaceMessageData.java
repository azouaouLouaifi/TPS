package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Set;

/**
 * Interface pour les données associées à un message.
 */
public interface InterfaceMessageData {

    /**
     * Retourne le score du message.
     * @return Le score du message
     */
    int getScore();

    /**
     * Définit l'état favori du message.
     * @param bookmarked true pour marquer comme favori, false sinon
     */
    void setBookmarked(boolean bookmarked);

    /**
     * Vérifie si le message est marqué comme favori.
     * @return true si le message est favori, false sinon
     */
    boolean isBookmarked();

    /**
     * Définit les mots associés au message.
     * @param words L'ensemble des mots à associer
     */
    void setWords(Set<String> words);

    /**
     * Définit le score du message.
     * @param score Le nouveau score
     */
    void setScore(int score);

    /**
     * Retourne les mots associés au message.
     * @return L'ensemble des mots associés
     */
    Set<String> getWords();

    /**
     * Compare ce MessageData avec un autre.
     * @param other L'autre MessageData à comparer
     * @return Un entier négatif, zéro ou positif selon la comparaison
     */
    int compare(InterfaceMessageData other);
}
