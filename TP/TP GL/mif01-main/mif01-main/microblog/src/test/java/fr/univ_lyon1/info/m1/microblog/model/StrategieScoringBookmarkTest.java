package fr.univ_lyon1.info.m1.microblog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class StrategieScoringBookmarkTest {
    private StrategieScoringBookmark strategieScoringBookmark;
    private Map<InterfaceMessage, InterfaceMessageData> messagesData;

    @BeforeEach
    void setUp() {
        strategieScoringBookmark = new StrategieScoringBookmark();
        messagesData = new HashMap<>();
    }

    @Test
    void testCalculerScoresWithBookmarkedMessages() {
        // Given
        InterfaceMessage msg1 = new Message("Test message", LocalDateTime.now());
        InterfaceMessage msg2 = new Message("Autree test message", LocalDateTime.now());
        InterfaceMessage msg3 = new Message("Unique content", LocalDateTime.now());

        InterfaceMessageData data1 = new MessageData();
        InterfaceMessageData data2 = new MessageData();
        InterfaceMessageData data3 = new MessageData();

        // When
        data1.setBookmarked(true);
        messagesData.put(msg1, data1);
        messagesData.put(msg2, data2);
        messagesData.put(msg3, data3);

        strategieScoringBookmark.calculerScores(messagesData);

        // Then
        assertEquals(
            2,
            messagesData.get(msg2).getScore(),
            "msg2 doit avoir score = 2 (test, message)"
        );
        assertEquals(
            0,
            messagesData.get(msg3).getScore(),
            "msg3 doit avoir score = 0 (no common words)"
        );
    }

    @Test
    void testCalculerScoresNoBookmarkedMessages() {
        // Givn
        InterfaceMessage msg1 = new Message("Pas de bookmarks ici", LocalDateTime.now());
        InterfaceMessage msg2 = new Message("Toujours pas des bookmarks", LocalDateTime.now());

        InterfaceMessageData data1 = new MessageData();
        InterfaceMessageData data2 = new MessageData();

        messagesData.put(msg1, data1);
        messagesData.put(msg2, data2);

        // When
        strategieScoringBookmark.calculerScores(messagesData);

        // Then
        assertEquals(
            0,
            messagesData.get(msg1).getScore(),
            "msg1 score = 0 ? (no bookmarks)"
        );
        assertEquals(
            0,
            messagesData.get(msg2).getScore(),
            "msg2 score = 0 ? (no bookmarks)"
        );
    }

    @Test
    void testCalculerScoresWithNoCommonWords() {
        // Given
        InterfaceMessage msg1 = new Message("Que des mots unique", LocalDateTime.now());
        InterfaceMessage msg2 = new Message("Autre set des mots", LocalDateTime.now());

        InterfaceMessageData data1 = new MessageData();
        InterfaceMessageData data2 = new MessageData();

        // When
        data1.setBookmarked(true);
        messagesData.put(msg1, data1);
        messagesData.put(msg2, data2);

        
        strategieScoringBookmark.calculerScores(messagesData);

        // Then
        assertEquals(
            2,
            messagesData.get(msg2).getScore(),
            "msg2 score = 2 ? (no common words)"
        );
    }
}
