package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stratégie d'affichage basée sur la pertinence des messages.
 * Priorise les messages bookmarkés, puis trie par score décroissant
 * et par date (plus récent d'abord).
 */
public class StrategieAffichagePlusPertinente implements InterfaceAffichageStrategie {
    private final MessageFilter messageFilter;
    
    /**
     * Constructeur de la stratégie.
     */
    public StrategieAffichagePlusPertinente() {
        this.messageFilter = new MessageFilter();
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
        final Map<InterfaceMessage, InterfaceMessageData> filteredMessages = 
            messageFilter.filterMessages(messages);
            
        return filteredMessages.entrySet().stream()
                .sorted((e1, e2) -> {
                    // Compare d'abord les bookmarks
                    boolean leftB = e1.getValue().isBookmarked();
                    boolean rightB = e2.getValue().isBookmarked();
                    if (leftB != rightB) {
                        return leftB ? -1 : 1;
                    }
                    
                    // Ensuite compare les scores
                    int scoreCompare = Integer.compare(
                        e2.getValue().getScore(),
                        e1.getValue().getScore()
                    );
                    if (scoreCompare != 0) {
                        return scoreCompare;
                    }
                    
                    // Si même score, trie par date (plus récent d'abord)
                    return e2.getKey().getDateCreation()
                            .compareTo(e1.getKey().getDateCreation());
                })
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }

    @Override
    public String toString() {
        return "Messages les plus pertinents";
    }
} 
