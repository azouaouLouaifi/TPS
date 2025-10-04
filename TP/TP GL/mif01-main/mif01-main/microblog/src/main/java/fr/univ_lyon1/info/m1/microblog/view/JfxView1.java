package fr.univ_lyon1.info.m1.microblog.view;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;
import java.time.LocalDateTime;   
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import fr.univ_lyon1.info.m1.microblog.controleur.InterfaceControleur;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceMessage;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceMessageData;
import fr.univ_lyon1.info.m1.microblog.model.Sujet;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceAffichageStrategie;
import fr.univ_lyon1.info.m1.microblog.model.StrategieAffichageChronologique;
import fr.univ_lyon1.info.m1.microblog.model.StrategieAffichageRecentPertinent;
import fr.univ_lyon1.info.m1.microblog.model.StrategieAffichagePlusPertinente;
import javafx.scene.Node;
import javafx.scene.Scene;


/**
 * Vue principale qui gère l'affichage des utilisateurs, des messages et les interactions.
 */
public class JfxView1 implements InterfaceView, InterfaceObservateur {
    private InterfaceControleur controleur;
    private HBox users;
    private Sujet sujet;

    /**
     * Constructeur de la vue.
     * 
     * @param stage La fenêtre principale.
     * @param width La largeur de la fenêtre.
     * @param height La hauteur de la fenêtre.
     * @param listUsers La liste des utilisateurs à afficher.
     * @param sujet Le sujet observé.
     */
    public JfxView1(final Stage stage, final int width, final int height,
     final List<InterfaceUser> listUsers, final Sujet sujet) {
        stage.setTitle("Y Microblogging");
        final VBox root = new VBox(10);

        users = new HBox(2);
        root.getChildren().add(users);
        createUsersPanes(listUsers);
        this.sujet = sujet;

        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Crée et affiche les panneaux des utilisateurs. 
     * @param listUsers La liste des utilisateurs à afficher.
     */
    private void createUsersPanes(final List<InterfaceUser> listUsers) {
        users.getChildren().clear();
        
        for (InterfaceUser u : listUsers) {
            ScrollPane p = new ScrollPane();
            VBox userBox = new VBox();
            p.setMinWidth(300);
            p.setContent(userBox);
            users.getChildren().add(p);

            VBox userMsgBox = new VBox();
            
            Label userID = new Label(u.getId());
            List<InterfaceAffichageStrategie> strategies = List.of(
                new StrategieAffichageChronologique(),
                new StrategieAffichageRecentPertinent(),
                new StrategieAffichagePlusPertinente()
            );
            ComboBox<InterfaceAffichageStrategie> strategyComboBox = new ComboBox<>(
                FXCollections.observableArrayList(strategies)
            );
            strategyComboBox.setMaxWidth(150);
            strategyComboBox.setStyle("-fx-font-size: 11px;");
            
            strategyComboBox.setOnAction(e -> {
                InterfaceAffichageStrategie selectedStrategy = strategyComboBox.getValue();
                if (selectedStrategy instanceof StrategieAffichageRecentPertinent 
                    || selectedStrategy instanceof StrategieAffichagePlusPertinente) {
                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle("Seuil de pertinence");
                    dialog.setHeaderText("Entrez le seuil de pertinence");
                    dialog.setContentText("Seuil (nombre entier) :");
                    dialog.showAndWait().ifPresent(resultat -> {
                        try {
                            int seuil = Integer.parseInt(resultat);
                            selectedStrategy.modifierSeuil(seuil);
                            controleur.modifierStrategie(selectedStrategy, u);
                        } catch (NumberFormatException ex) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText("Valeur invalide");
                            alert.setContentText("Veuillez entrer un nombre entier valide.");
                            alert.showAndWait();
                        }
                    });
                } else {
                    controleur.modifierStrategie(selectedStrategy, u);
                }
            });
            
            HBox userHeader = new HBox(110);
            userHeader.getChildren().addAll(userID, strategyComboBox);
            
            Pane textBox = createInputWidget(u);
            userBox.getChildren().addAll(userHeader, userMsgBox, textBox);
        }
    }

    /**
     * Ajoute un contrôleur à la vue.
     * 
     * @param controleur Le contrôleur à ajouter.
     */
    @Override
    public void addControleur(final InterfaceControleur controleur) {
        this.controleur = controleur;
    }

    /**
     * Retourne le sujet observé.
     * 
     * @return Le sujet.
     */ 
    @Override
    public Sujet getSujet() {
        return sujet;
    }

    /**
     * Définit le sujet observé.
     * 
     * @param sujet Le sujet à définir.
     */
    @Override
    public void setSujet(final Sujet sujet) {
        this.sujet = sujet;
    }

    /**
     * Affiche les messages d'un utilisateur spécifique.
     * 
     * @param messagesData Les messages.
     * @param user utilisateur dont afficher les messages.
     */
    private void setMessageList(final Map<InterfaceMessage, InterfaceMessageData> messagesData,
     final InterfaceUser user) {
        for (Node u : users.getChildren()) {
            ScrollPane scroll = (ScrollPane) u;
            VBox userBox = (VBox) scroll.getContent();
            HBox userHeader = (HBox) userBox.getChildren().get(0);
            Label userID = (Label) userHeader.getChildren().get(0);
            
            if (userID.getText().equals(user.getId())) {
                VBox userMsg = (VBox) userBox.getChildren().get(1);
                userMsg.getChildren().clear();
                for (Entry<InterfaceMessage, InterfaceMessageData> e : messagesData.entrySet()) {
                    InterfaceMessage m = e.getKey();
                    final InterfaceMessageData d = e.getValue();
                    userMsg.getChildren().add(createMessageWidget(m, d, user));
                }
                break;
            }
        }
    }

    static final String MSG_STYLE = "-fx-background-color: white; "
    + "-fx-border-color: black; -fx-border-width: 1;"
    + "-fx-border-radius: 10px;"
    + "-fx-background-radius: 10px;"
    + "-fx-padding: 8px; "
    + "-fx-margin: 5px; "
    + "-fx-min-width: 280px; "
    + "-fx-max-width: 280px; ";

    /**
     * Crée un widget pour afficher un message.
     * 
     * @param m Le message à afficher.
     * @param d Les données du message.
     * @return Le widget représentant le message.
     */
    private VBox createMessageWidget(final InterfaceMessage m, final InterfaceMessageData d,
     final InterfaceUser u) {
        VBox msgBox = new VBox(5);

        String bookmarkText = d.isBookmarked() ? "⭐" : "Click to bookmark";
        Button bookButton = new Button(bookmarkText);
        bookButton.setStyle("-fx-padding: 2 5 2 5; -fx-min-height: 25px; -fx-max-height: 25px;");
        bookButton.setOnAction(e -> {
            if (bookButton.getText().equals("Click to bookmark")) {
                bookButton.setText("⭐");
                controleur.notifierB(m, u);
            } else {
                bookButton.setText("Click to bookmark");
                controleur.notifierNB(m, u);
            }
        });

        Button suppButton = new Button("x");
        suppButton.setStyle("-fx-padding: 2 5 2 5; -fx-min-height: 25px; -fx-max-height: 25px;");
        suppButton.setOnAction(e -> {
            controleur.supprimerMessage(m.getContent());
        });

        HBox buttonBox = new HBox(0);
        buttonBox.setStyle("-fx-alignment: center-right;");
        buttonBox.getChildren().addAll(bookButton, suppButton);

        msgBox.getChildren().add(buttonBox);

        final Label label = new Label(m.getContent());
        label.setWrapText(true);
        msgBox.getChildren().add(label);
        LocalDateTime dateFromM = m.getDateCreation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        String dateString = dateFromM.format(formatter);
        final Label date = new Label(dateString);
        msgBox.getChildren().add(date);
        final Label score = new Label("Score: " + d.getScore());
        score.setTextFill(Color.LIGHTGRAY);
        msgBox.getChildren().add(score);

        msgBox.setStyle(MSG_STYLE);
        return msgBox;
    }

    /**
     * Crée un widget d'entrée pour un utilisateur.
     * @param u utilisateur pour lequel créer le widget.
     * @return Le widget d'entrée.
     */
    private Pane createInputWidget(final InterfaceUser u) {
        final Pane input = new HBox();
        final TextArea t = new TextArea();
        t.setMaxSize(200, 150);
        t.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && e.isControlDown()) {
                publish(t);
                t.clear();
            }
        });
        Button s = new Button("Publish");
        s.setOnAction(e -> {
            publish(t);
            t.clear();
        });
        input.getChildren().addAll(t, s);
        return input;
    }

    /**
     * Publie un message dans la vue.
     * 
     * @param t La zone de texte contenant le message à publier.
     */
    private void publish(final TextArea t) {
        controleur.notifierAjoutMsg(t.getText());
    }

    /**
     * Met à jour la liste des messages pour un utilisateur .
     * 
     * @param m Les messages.
     * @param u utilisateur dont afficher les messages.
     */
    public void miseAjour(final Map<InterfaceMessage, InterfaceMessageData> m,
     final InterfaceUser u) {
        this.setMessageList(m, u);
    }
}
