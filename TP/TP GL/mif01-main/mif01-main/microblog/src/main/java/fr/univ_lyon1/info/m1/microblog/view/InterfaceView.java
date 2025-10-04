package fr.univ_lyon1.info.m1.microblog.view;

import fr.univ_lyon1.info.m1.microblog.controleur.InterfaceControleur;

/**
 * Interface représentant une vue.
 */
public interface InterfaceView {

    /**
     * Ajoute un contrôleur à la vue.
     * @param controleur Le contrôleur à ajouter.
     */
    void addControleur(InterfaceControleur controleur);
}
