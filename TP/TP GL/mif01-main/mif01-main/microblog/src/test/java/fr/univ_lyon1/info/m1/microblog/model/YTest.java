package fr.univ_lyon1.info.m1.microblog.model;

import fr.univ_lyon1.info.m1.microblog.controleur.InterfaceControleur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

class YTest {
    private Y y;
    private InterfaceControleur controleur; 
    private GestionUser gestionUser;
    private GestionMessage gestionMessage;

    @BeforeEach
    void setUp() {
        gestionUser = new GestionUser();
        gestionMessage = new GestionMessage();

        y = new Y(gestionUser, gestionMessage);

        // Ajouter un utilisateur pour les tests dsna Y
        y.creeUtilisateur("testUser");
    }

    @Test
    void testInstancier() {
        assertNotNull(y, "L'instance de Y ne doit pas être null");
    }

    @Test
    void testAjouterControleur() {
        y.ajouterControleur(controleur);

        // Assert
        assertNotNull(y, "Le contrôleur doit être ajouté correctement");
    }

    @Test
    void testCreeUtilisateur() {
        // Act
        y.creeUtilisateur("newUser");

        // Assert
        assertEquals(2, y.getUsers().size(), "Le nouvel utilisateur doit être ajouté");
    }

    @Test
    void testAjouterMessage() {
        // Arrange
        String message = "Hello World";

        // Act
        y.ajouterMessage(message);

        // Assert
        Map<InterfaceMessage, InterfaceMessageData> messages =
                y.getUsers().get(0).getMessagesUser();
        assertEquals(1, messages.size(), "Le message doit être ajouté pour l'utilisateur");
    }

    @Test
    void testAjouterMessageDate() {
        // Arrange
        String message = "Hello with date";
        LocalDateTime date = LocalDateTime.of(2024, 12, 14, 12, 0);

        // Act
        y.ajouterMessageDate(message, date);

        // Assert
        Map<InterfaceMessage, InterfaceMessageData> messages =
                y.getUsers().get(0).getMessagesUser();
        assertEquals(1, messages.size(), "Un message avec date doit être ajouté");
        assertTrue(messages.keySet().stream().anyMatch(m -> m.getDateCreation().equals(date)),
                "La date du message doit correspondre");
    }

    @Test
    void testSupprimerMessage() {
        // Arrange
        String message = "Message to delete";
        y.ajouterMessage(message);

        // Act
        y.supprimerMessage(message);

        // Assert
        Map<InterfaceMessage, InterfaceMessageData> messages =
                y.getUsers().get(0).getMessagesUser();
        assertTrue(messages.isEmpty(), "Le message doit être supprimé");
    }

    @Test
    void testSetScoringStrategie() {
        // Arrange
        InterfaceAffichageStrategie strategie = new StrategieAffichageChronologique();
        InterfaceUser user = y.getUsers().get(0);

        // Act
        y.setScoringStrategie(strategie, user);

        // Assert
        InterfaceAffichageStrategie userStrategie = gestionUser.getStrategyUser(user);
        assertTrue(userStrategie instanceof StrategieAffichageChronologique,
                "La stratégie d'affichage doit être définie pour l'utilisateur");
    }

    @Test
    void testGetUsers() {
        // Act
        List<InterfaceUser> users = y.getUsers();

        // Assert
        assertEquals(1, users.size(), "La list des utilisateurs doit contenir un utilisateur");
        assertEquals("testUser", users.get(0).getId(), "L'utilisateur doi avoir l'ID correct");
    }


    
}
