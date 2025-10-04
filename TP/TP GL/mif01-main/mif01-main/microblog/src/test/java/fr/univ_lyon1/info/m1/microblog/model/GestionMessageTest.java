package fr.univ_lyon1.info.m1.microblog.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class GestionMessageTest {
    private GestionMessage gestionMessage;
    private Map<InterfaceMessage, InterfaceMessageData> messagesData;
    private InterfaceAffichageStrategie mockStrategieAffichage;
    private InterfaceMessage mockMessage;
    private InterfaceMessageData mockMessageData;

    @BeforeEach
    void setUp() {
        gestionMessage = new GestionMessage();

        // Mock des dépendances
        messagesData = new HashMap<>();
        mockStrategieAffichage = mock(InterfaceAffichageStrategie.class);
        mockMessage = mock(InterfaceMessage.class);
        mockMessageData = mock(InterfaceMessageData.class);

        // Config de comportements des mocks
        when(mockMessage.getContent()).thenReturn("Hello");
        when(mockMessage.getDateCreation()).thenReturn(LocalDateTime.now());
        when(mockMessageData.isBookmarked()).thenReturn(false);
        when(mockStrategieAffichage.appliquer(any()))
            .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testAjouterMessage() {
        Map<InterfaceMessage, InterfaceMessageData> result = gestionMessage.ajouterMessage(
            "Hello World", messagesData, mockStrategieAffichage);

        assertEquals(1, result.size());
        assertTrue(result.keySet().stream()
            .anyMatch(m -> "Hello World".equals(m.getContent())));
    }

    @Test
    void testAjouterMessageAvecDate() {
        LocalDateTime specificDate = LocalDateTime.of(2023, 1, 1, 12, 0);

        Map<InterfaceMessage, InterfaceMessageData> result = gestionMessage.ajouterMessageAvecDate(
            "Message with date", specificDate, messagesData, mockStrategieAffichage);

        assertEquals(1, result.size());
        assertTrue(result.keySet().stream()
            .anyMatch(m -> "Message with date".equals(m.getContent())));
        assertTrue(result.keySet().stream()
            .anyMatch(m -> specificDate.equals(m.getDateCreation())));
    }

    @Test
    void testBook() {
        messagesData.put(mockMessage, mockMessageData);

        // Act
        Map<InterfaceMessage, InterfaceMessageData> result = gestionMessage.book(
            mockMessage, messagesData, mockStrategieAffichage);

        // Assert
        verify(mockMessageData).setBookmarked(true);
        assertEquals(messagesData, result);
    }

    @Test
    void testNBook() {
        // Arrange
        when(mockMessageData.isBookmarked()).thenReturn(true);
        messagesData.put(mockMessage, mockMessageData);

        // Act
        Map<InterfaceMessage, InterfaceMessageData> result = gestionMessage.nBook(
            mockMessage, messagesData, mockStrategieAffichage);

        // Assert
        verify(mockMessageData).setBookmarked(false);
        assertEquals(messagesData, result);
    }

    @Test
    void testSupprimerMessage() {
        InterfaceUser mockUser = mock(InterfaceUser.class);
        Map<InterfaceMessage, InterfaceMessageData> userMessages = new HashMap<>();
        userMessages.put(mockMessage, mockMessageData);

        when(mockUser.getMessagesUser()).thenReturn(userMessages);

        Map<InterfaceMessage, InterfaceMessageData> result = gestionMessage.supprimerMessage(
            mockUser, "Hello", mockStrategieAffichage);

        assertFalse(result.containsKey(mockMessage));
    }

    @Test
    void testTrier() {
        messagesData.put(mockMessage, mockMessageData);

        Map<InterfaceMessage, InterfaceMessageData> result = gestionMessage.trier(
            messagesData, mockStrategieAffichage);

        assertEquals(1, result.size());
    }
}
