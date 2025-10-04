package fr.univ_lyon1.info.m1.microblog.controleur;

import fr.univ_lyon1.info.m1.microblog.model.InterfaceAffichageStrategie;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceMessage;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceModel;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceUser;
import fr.univ_lyon1.info.m1.microblog.view.InterfaceView;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class ControleurTest {
    private Controleur controleur;
    private InterfaceModel mockModel;
    private InterfaceView mockView;
    private InterfaceUser mockUser;
    private InterfaceMessage mockMessage;
    private InterfaceAffichageStrategie mockStrategie;

    @BeforeEach
    void setUp() {
        // Création de mocks
        mockModel = mock(InterfaceModel.class);
        mockView = mock(InterfaceView.class);
        mockUser = mock(InterfaceUser.class);
        mockMessage = mock(InterfaceMessage.class);
        mockStrategie = mock(InterfaceAffichageStrategie.class);

        // Initialisaion du controleur
        controleur = new Controleur();
        controleur.ajouterModel(mockModel);
        controleur.ajouterVue(mockView);
    }

    @Test
    void testAjouterModel() {
        // Given
        InterfaceModel newModel = mock(InterfaceModel.class);

        // When
        controleur.ajouterModel(newModel);

        // Then
        verify(newModel, never()).ajouterControleur(any());
    }

    @Test
    void testAjouterVue() {
        // When
        controleur.ajouterVue(mockView);
        
        // Then
        verify(mockView, never()).addControleur(any());
    }

    @Test
    void testInitialisation() {
        // When
        controleur.initialisation();

        // Then
        verify(mockModel, times(1)).ajouterControleur(controleur);
        verify(mockView, times(1)).addControleur(controleur);
    }

    @Test
    void testNotifierAjoutMsg() {
        // Given
        String message = "Test message";

        // When
        controleur.notifierAjoutMsg(message);

        // Then
        verify(mockModel, times(1)).ajouterMessage(message);
    }

    @Test
    void testNotifierB() {
        // Given
        controleur.notifierB(mockMessage, mockUser);

        // Then
        verify(mockModel, times(1)).book(mockMessage, mockUser);
    }

    @Test
    void testNotifierNB() {
        // Given
        controleur.notifierNB(mockMessage, mockUser);

        // Then
        verify(mockModel, times(1)).nBook(mockMessage, mockUser);
    }

    @Test
    void testModifierStrategie() {
        // When
        controleur.modifierStrategie(mockStrategie, mockUser);

        // Then
        verify(mockModel, times(1)).setScoringStrategie(mockStrategie, mockUser);
    }

    @Test
    void testSupprimerMessage() {
        // Given
        String message = "Message to delete";

        // When
        controleur.supprimerMessage(message);

        // Thenn
        verify(mockModel, times(1)).supprimerMessage(message);
    }
}
