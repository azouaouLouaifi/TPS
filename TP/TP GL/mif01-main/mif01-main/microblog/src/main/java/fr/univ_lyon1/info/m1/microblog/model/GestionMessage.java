package fr.univ_lyon1.info.m1.microblog.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Iterator;

/**
 * Gestionnaire des messages.
 */
public class GestionMessage {
    /**
     * Ajoute un nouveau message.
     * @param contenu Le contenu du message
     * @param messagesData La map des messages existants
     * @param strategieAffichage La stratégie d'affichage à utiliser
     * @return La map mise à jour avec tous les messages
     */
    public Map<InterfaceMessage, InterfaceMessageData> ajouterMessage(
            final String contenu, final Map<InterfaceMessage, 
            InterfaceMessageData> messagesData, 
            final InterfaceAffichageStrategie strategieAffichage) {

        LocalDateTime dateCreation = LocalDateTime.now();
        InterfaceMessage message = new Message(contenu, dateCreation);
        messagesData.put(message, new MessageData());
        return  this.trier(messagesData, strategieAffichage);
    }

    /**
     * Retourne la map des messages visibles (filtrés et triés).
     * @param messagesData La map complète des messages
     * @param strategieAffichage La stratégie d'affichage à utiliser
     * @return La map des messages filtrée et triée selon la stratégie
     */
    public Map<InterfaceMessage, InterfaceMessageData> trier(
            final Map<InterfaceMessage, InterfaceMessageData> messagesData,
            final InterfaceAffichageStrategie strategieAffichage) {
        InterfaceScoreStrategie s = new StrategieScoringBookmark(); 
        s.calculerScores(messagesData);
        return strategieAffichage.appliquer(messagesData);
    }

    /**
     * Marque un message comme favori.
     * @param m Le message à marquer
     * @param messagesData La map des messages
     * @param strategieAffichage La stratégie d'affichage à utiliser
     * @return La map mise à jour avec tous les messages
     */
    public Map<InterfaceMessage, InterfaceMessageData> book(
            final InterfaceMessage m, final Map<InterfaceMessage,
            InterfaceMessageData> messagesData,
            final InterfaceAffichageStrategie strategieAffichage) {
        messagesData.get(m).setBookmarked(true);
        return this.trier(messagesData, strategieAffichage);
    }

    /**
     * Retire le marquage favori d'un message.
     * @param m Le message à démarquer
     * @param messagesData La map des messages
     * @param strategieAffichage La stratégie d'affichage à utiliser
     * @return La map mise à jour avec tous les messages
     */
    public Map<InterfaceMessage, InterfaceMessageData> nBook(
            final InterfaceMessage m, final Map<InterfaceMessage, 
            InterfaceMessageData> messagesData,
            final InterfaceAffichageStrategie strategieAffichage) {
        messagesData.get(m).setBookmarked(false);
        return this.trier(messagesData, strategieAffichage);
    }

    /**
     * Supprime un message de la map des messages de l'utilisateur.
     * @param user L'utilisateur dont les messages doivent être supprimés
     * @param message Le message à supprimer
     * @param strategieAffichage La stratégie d'affichage à utiliser
     * @return La map mise à jour avec tous les messages
     */
    public Map<InterfaceMessage, InterfaceMessageData> supprimerMessage(
            final InterfaceUser user, final String message,
            final InterfaceAffichageStrategie strategieAffichage) {
       
            Iterator<Map.Entry<InterfaceMessage, InterfaceMessageData>> iterator = 
                user.getMessagesUser().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<InterfaceMessage, InterfaceMessageData> e = iterator.next();
                    if (e.getKey().getContent().equals(message)) {
                        iterator.remove();
                    }
                }
        return this.trier(user.getMessagesUser(), strategieAffichage);
    }

    /**
     * Ajoute un nouveau message avec une date spécifique.
     * @param contenu Le contenu du message
     * @param date La date de création du message
     * @param messagesData La map des messages existants
     * @param strategieAffichage La stratégie d'affichage à utiliser
     * @return La map mise à jour avec tous les messages
     */
    public Map<InterfaceMessage, InterfaceMessageData> ajouterMessageAvecDate(
            final String contenu, final LocalDateTime date,
            final Map<InterfaceMessage, InterfaceMessageData> messagesData,
            final InterfaceAffichageStrategie strategieAffichage) {
        InterfaceMessage message = new Message(contenu, date);
        messagesData.put(message, new MessageData());
        return this.trier(messagesData, strategieAffichage);
    }
}
