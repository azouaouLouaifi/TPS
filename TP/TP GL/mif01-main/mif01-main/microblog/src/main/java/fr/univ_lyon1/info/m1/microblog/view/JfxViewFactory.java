package fr.univ_lyon1.info.m1.microblog.view;

import javafx.stage.Stage;
import fr.univ_lyon1.info.m1.microblog.model.Sujet;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import java.util.List;

/**
 * Factory pour créer des instances de vues.
 * Cette classe est responsable de la création des vues en fonction du type spécifié.
 */
public class JfxViewFactory implements ViewFactory {

    /**
     * Crée une vue en fonction du type spécifié.
     *
     * @param stage La scène principale.
     * @param width La largeur de la vue.
     * @param height La hauteur de la vue.
     * @param users La liste des utilisateurs à afficher dans la vue.
     * @param sujet Le sujet associé à la vue.
     * @param viewType Le type de la vue à créer (view1, view2).
     * @return Une instance de `InterfaceView` (JfxView ou JfxView2).
     * @throws IllegalArgumentException si le type de vue est inconnu.
     */
    @Override
    public InterfaceView createView(final Stage stage, final int width, final int height,
     final List<InterfaceUser> users, final Sujet sujet, final String viewType) {
        if ("view1".equals(viewType)) {
            return new JfxView1(stage, width, height, users, sujet);
        } else if ("view2".equals(viewType)) {
            return new JfxView2(stage, width, height, users, sujet);
        } else {
            throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }
}
