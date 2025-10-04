package fr.univ_lyon1.info.m1.microblog.controleur;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.microblog.model.InterfaceModel;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceAffichageStrategie;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceMessage;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import fr.univ_lyon1.info.m1.microblog.view.InterfaceView;

/**
 * Contrôleur principal qui gère la logique entre les vues et le modèle.
 */
public class Controleur implements InterfaceControleur  {
    private InterfaceModel model;
    private List<InterfaceView> views = new ArrayList<>();

    /**
     * Ajoute un modèle au contrôleur.
     * 
     * @param model Le modèle à ajouter.
     */
    @Override
    public void ajouterModel(final InterfaceModel model) {
        this.model = model;
    }

    /**
     * Ajoute une vue au contrôleur.
     * 
     * @param view La vue à ajouter.
     */
    @Override
    public void ajouterVue(final InterfaceView view) {
        this.views.add(view);
    }

    /**
     * Initialise le contrôleur et les vues associées.
     */
    public void initialisation() {
        this.model.ajouterControleur(this);
        for (InterfaceView c : views) {
            c.addControleur(this);
        }
    }

    /**
     * Notifie l'ajout d'un message au modèle.
     * 
     * @param t Le texte du message à ajouter.
     */
    @Override
    public void notifierAjoutMsg(final String t) {
        model.ajouterMessage(t);
    }

    /**
     * Notifie le modèle qu'un message a été marqué comme favori.
     * 
     * @param m Le message à marquer comme favori.
     * @param u L'utilisateur qui marque le message comme favori.
     */
    @Override
    public void notifierB(final InterfaceMessage m, final InterfaceUser u) {
        model.book(m, u);
    }

    /**
     * Notifie le modèle qu'un message a été retiré des favoris.
     * 
     * @param m Le message à retirer des favoris.
     * @param u L'utilisateur qui retire le message des favoris.
     */
    @Override
    public void notifierNB(final InterfaceMessage m, final InterfaceUser u) {
        model.nBook(m, u);
    }

    /**
     * Modifie la stratégie de score pour un utilisateur.
     * 
     * @param strategy La nouvelle stratégie de score.
     * @param user L'utilisateur pour qui la stratégie est modifiée.
     */
    public void modifierStrategie(final InterfaceAffichageStrategie strategy,
     final InterfaceUser user) {
        model.setScoringStrategie(strategy, user);
    }

    /*
     * Supprime un message du modèle.x
     * 
     * @param message Le message à supprimer.
     */
    @Override
    public void supprimerMessage(final String message) {
        model.supprimerMessage(message);
    }
}
