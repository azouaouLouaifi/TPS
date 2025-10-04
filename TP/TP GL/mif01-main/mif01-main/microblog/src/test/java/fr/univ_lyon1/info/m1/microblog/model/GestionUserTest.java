package fr.univ_lyon1.info.m1.microblog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class GestionUserTest {
    private GestionUser gestionUser;
    private InterfaceUser mockUser1;
    private InterfaceUser mockUser2;
    
    @BeforeEach
    void setUp() {
        gestionUser = new GestionUser();
        mockUser1 = new User("user1");
        mockUser2 = new User("user2");
        new StrategieAffichageChronologique();
    }

    @Test
    void testCreeUser() {
        gestionUser.creeUser("user1");
        Map<InterfaceUser, InterfaceAffichageStrategie> users = gestionUser.getUsers();

        assertEquals(1, users.size());
        assertTrue(users.containsKey(mockUser1));
        assertTrue(users.get(mockUser1) instanceof StrategieAffichageChronologique);
    }

    @Test
    void testGetUsers() {
        gestionUser.creeUser("user1");
        gestionUser.creeUser("user2");

        Map<InterfaceUser, InterfaceAffichageStrategie> users = gestionUser.getUsers();

        assertEquals(2, users.size());
        assertTrue(users.containsKey(mockUser1));
        assertTrue(users.containsKey(mockUser2));
    }

    @Test
    void testGetUsersOnly() {
        gestionUser.creeUser("user1");
        gestionUser.creeUser("user2");

        List<InterfaceUser> userList = gestionUser.getUsersOnly();

        assertEquals(2, userList.size());
        assertTrue(userList.contains(mockUser1));
        assertTrue(userList.contains(mockUser2));
    }

    @Test
    void testChangeStrategieDisplay() {
        gestionUser.creeUser("user1");
        InterfaceAffichageStrategie newStrategy = new StrategieAffichageRecentPertinent();

        gestionUser.changeStrategieDisplay(mockUser1, newStrategy);

        InterfaceAffichageStrategie updatedStrategy = gestionUser.getStrategyUser(mockUser1);
        assertEquals(newStrategy, updatedStrategy);
    }

    @Test
    void testGetStrategyUser() {
        gestionUser.creeUser("user1");

        InterfaceAffichageStrategie strategy = gestionUser.getStrategyUser(mockUser1);

        assertNotNull(strategy);
        assertTrue(strategy instanceof StrategieAffichageChronologique);
    }
}
