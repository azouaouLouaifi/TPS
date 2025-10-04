package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Stratégie d'affichage chronologique des messages.
 * Affiche tous les messages triés par favoris puis par date de création.
 */
public class StrategieAffichageChronologique implements InterfaceAffichageStrategie {

    /**
     * Applique la stratégie d'affichage chronologique aux messages.
     * @param messages Les messages à traiter
     * @return Les messages triés et filtrés selon la stratégie
     */ 
    @Override
    public Map<InterfaceMessage, InterfaceMessageData> appliquer(
            final Map<InterfaceMessage, InterfaceMessageData> messages) {
        return messages.entrySet().stream()
                .sorted(Collections.reverseOrder((
                    Entry<InterfaceMessage, InterfaceMessageData> left,
                    Entry<InterfaceMessage, InterfaceMessageData> right) -> {
                    boolean leftB = left.getValue().isBookmarked();
                    boolean rightB = right.getValue().isBookmarked();
    
                    if (leftB && !rightB) {
                        return 1;
                    } else if (!leftB && rightB) {
                        return -1;
                    }
                    // Si même statut de bookmark, trie par date (plus ancien en premier)
                    return left.getKey().getDateCreation()
                        .compareTo(right.getKey().getDateCreation());
                }))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }

    /**
     * Retourne une description de la stratégie pour l'interface utilisateur.
     * @return La description de la stratégie
     */
    @Override
    public String toString() {
        return "Chronologique";
    }

    /**
     * Modifie le seuil de filtrage des messages.
     * @param seuil Le nouveau seuil de filtrage
     */
    @Override
    public void modifierSeuil(final int seuil) {
        // La stratégie chronologique n'utilise pas de seuil
    }
} 
