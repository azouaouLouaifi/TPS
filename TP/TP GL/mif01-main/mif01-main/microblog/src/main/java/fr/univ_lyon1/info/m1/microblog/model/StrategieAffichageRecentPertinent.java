package fr.univ_lyon1.info.m1.microblog.model;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Stratégie d'affichage combinant pertinence et récence des messages.
 */
public class StrategieAffichageRecentPertinent implements InterfaceAffichageStrategie {
    private final MessageFilter messageFilter;
    private final StrategieScoringRecent scoringRecent;
   
    /**
     * Constructeur de la stratégie.
     */
    public StrategieAffichageRecentPertinent() {
        this.messageFilter = new MessageFilter();
        this.scoringRecent = new StrategieScoringRecent();
    }
    
    /**
     * Modifie le seuil de filtrage des messages.
     * @param seuil Le nouveau seuil
     */
    public void modifierSeuil(final int seuil) {
        this.messageFilter.setSeuil(seuil);
    }
    
    /**
     * Applique la stratégie d'affichage.
     * @param messages Les messages à afficher
     * @return Les messages triés par pertinence
     */
    @Override
    public Map<InterfaceMessage, InterfaceMessageData> appliquer(
            final Map<InterfaceMessage, InterfaceMessageData> messages) {
        scoringRecent.calculerScores(messages);
        
        Map<InterfaceMessage, InterfaceMessageData> filteredMessages = 
            messageFilter.filterMessages(messages);

        // Trie les messages par score et statut bookmark
        return filteredMessages.entrySet().stream()
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
                    return right.getValue().compare(left.getValue());
                }))
                .collect(Collectors.toMap(
                    Entry::getKey,
                    Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }

    /**
     * Retourne une représentation sous forme de chaîne de la stratégie.
     * @return Une chaîne représentant la stratégie
     */ 
    @Override
    public String toString() {
        return "Messages pertinents récents";
    }
} 
