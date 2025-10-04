package fr.univ_lyon1.info.m1.microblog.view;

import javafx.stage.Stage;
import fr.univ_lyon1.info.m1.microblog.model.Sujet;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import java.util.List;

/**
 * Interface pour la création de vues.
 */
public interface ViewFactory {

    /**
     * Crée une vue en fonction du type spécifié.
     *
     * @param stage La scène principale pour la vue.
     * @param width La largeur de la vue.
     * @param height La hauteur de la vue.
     * @param users La liste des utilisateurs à afficher dans la vue.
     * @param sujet Le sujet à associer à la vue.
     * @param viewType Le type de la vue à créer.
     * @return Une instance d'InterfaceView correspondant au type de 
     * vue demandé.
     */
    InterfaceView createView(Stage stage, int width, int height, 
    List<InterfaceUser> users, Sujet sujet, String viewType);
}
