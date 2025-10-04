package fr.univ_lyon1.info.m1.microblog;

import fr.univ_lyon1.info.m1.microblog.facade.MicroblogFacade;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale de l'application. 
 * Cette classe lance l'application JavaFX et initialise
 *  la façade pour gérer la logique de l'application.
 */
public class App extends Application {

    /**
     * Méthode appelée lors du lancement de l'application.
     * Elle initialise les fenêtres (stages) et la façade, puis démarre l'application via la façade.
     *
     * @param primaryStage La scène principale de l'application.
     * @throws Exception Si une erreur survient lors de l'initialisation des vues ou de la logique.
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        Stage secondStage = new Stage();
        MicroblogFacade facade = new MicroblogFacade(primaryStage, secondStage);
        facade.startApplication();
    }

    /**
     * Méthode main.
     * 
     * @param args Les arguments passés lors du lancement de l'application.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
