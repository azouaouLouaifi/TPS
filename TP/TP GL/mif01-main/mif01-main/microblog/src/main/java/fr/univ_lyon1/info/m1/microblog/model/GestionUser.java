package fr.univ_lyon1.info.m1.microblog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestionnaire des utilisateurs de l'application.
 */
public class GestionUser {
    private Map<InterfaceUser, InterfaceAffichageStrategie> users = new HashMap<>();
    /**
     * Crée un nouvel utilisateur avec l'identifiant spécifié.
     * @param id L'identifiant du nouvel utilisateur
     */
    public void creeUser(final String id) {
        InterfaceUser user = new User(id);
        this.users.put(user, new StrategieAffichageChronologique());
    }

    /**
     * Retourne la liste des utilisateurs.
     * @return La liste des utilisateurs
     */
    public Map<InterfaceUser, InterfaceAffichageStrategie> getUsers() {
        return users;
    }

    /**
     * Change la stratégie d'affichage pour un utilisateur.
     * @param user L'utilisateur dont la stratégie d'affichage doit être changée
     * @param strategieAffichage La nouvelle stratégie d'affichage
     */
    public void changeStrategieDisplay(final InterfaceUser user,
        final InterfaceAffichageStrategie strategieAffichage) {
        this.users.put(user, strategieAffichage);  
    }

    /**
     * Retourne la liste des utilisateurs.
     * @return La liste des utilisateurs
     */
    public List<InterfaceUser> getUsersOnly() {
        List<InterfaceUser> u = new ArrayList<>();
        users.forEach((us, s) -> {
            u.add(us);
        });
        return u;
    }

    /**
     * Retourne la stratégie d'affichage pour un utilisateur.
     * @param user L'utilisateur dont la stratégie d'affichage doit être récupérée
     * @return La stratégie d'affichage de l'utilisateur
     */
    public InterfaceAffichageStrategie getStrategyUser(final InterfaceUser user) {
        return this.users.get(user);
    } 
}
