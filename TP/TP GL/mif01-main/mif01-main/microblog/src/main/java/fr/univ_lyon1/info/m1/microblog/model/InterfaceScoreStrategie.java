package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Map;

/**
 * Interface représentant une stratégie de calcul de score pour les messages.
 */
public interface InterfaceScoreStrategie {

    /**
     * Calcule les scores des messages en fonction de certaines règles de scoring.
     * @param messagesData Map contenant les messages et leurs données associées
     */
    void calculerScores(Map<InterfaceMessage, InterfaceMessageData> messagesData);
}
