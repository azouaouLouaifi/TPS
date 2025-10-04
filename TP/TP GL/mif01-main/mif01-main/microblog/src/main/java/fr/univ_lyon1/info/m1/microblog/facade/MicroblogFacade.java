package fr.univ_lyon1.info.m1.microblog.facade;

import fr.univ_lyon1.info.m1.microblog.model.InterfaceModel;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.view.InterfaceObservateur;
import fr.univ_lyon1.info.m1.microblog.view.InterfaceView;
import fr.univ_lyon1.info.m1.microblog.controleur.InterfaceControleur;
import fr.univ_lyon1.info.m1.microblog.initializer.MicroblogInitialiser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.microblog.controleur.Controleur;
import fr.univ_lyon1.info.m1.microblog.model.Sujet;
import fr.univ_lyon1.info.m1.microblog.view.JfxViewFactory;

/**
 * Facade de l'application Microblog.
 */
public class MicroblogFacade {
    private InterfaceModel model;
    private List<InterfaceView> views = new ArrayList<>();
    private InterfaceControleur controller;
    private MicroblogInitialiser initializer;

    /**
     * Constructeur de la facade. Initialise le modèle, les vues et le contrôleur.
     * 
     * @param primaryStage La fenêtre principale de l'application.
     * @param secondStage La deuxième fenêtre de l'application.
     */
    public MicroblogFacade(final Stage primaryStage, final Stage secondStage) {
        this.model = Y.instancier();
        this.initializer = new MicroblogInitialiser();
        initializer.initialiser(model);
        this.controller = new Controleur();
        creeVues(primaryStage, secondStage, model.getUsers(), (Sujet) model);
    }

    /**
     * Crée les vues et les ajoute à la liste des vues.
     * 
     * @param primaryStage La fenêtre principale.
     * @param secondStage La deuxième fenêtre.
     * @param users La liste des utilisateurs.
     * @param sujet Le sujet à observer.
     */
    private void creeVues(final Stage primaryStage, final Stage secondStage, 
    final List<InterfaceUser> users, final Sujet sujet) {
        final InterfaceView view1 = new JfxViewFactory().createView(primaryStage, 600, 600, users, 
        sujet, "view1");
        final InterfaceView view2 = new JfxViewFactory().createView(secondStage, 300, 300, users,
         sujet, "view2");
        views.add(view1);
        views.add(view2);   
    }

    /**
     * Démarre l'application.
     */
    public void startApplication() {
        controller.ajouterModel(model);
        for (InterfaceView view : views) {
            ((Sujet) model).ajouterObservateur((InterfaceObservateur) view);
            controller.ajouterVue(view);
        }
        controller.initialisation();
    }
}
