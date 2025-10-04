package fr.univ_lyon1.info.m1.microblog.initializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.univ_lyon1.info.m1.microblog.model.InterfaceModel;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Classe responsable de l'initialisation du microblog.
 * Gère le chargement de la configuration initiale a partir d'un fichier JSON
 * et crée une configuration par défaut si le fichier n'existe pas.
 */
public class MicroblogInitialiser {
    private static final String CONFIG_FILE = "microblog_config.json";
    private static final Logger LOGGER = Logger.getLogger(MicroblogInitialiser.class.getName());
    private final Gson gson;
    
    /**
     * Configuration du microblog.
     */
    public static class MicroblogConfig {
        private List<UserConfig> users = new ArrayList<>();
        private List<MessageConfig> messages = new ArrayList<>();
    }
    
    /**
     * Configuration d'un utilisateur.
     */
    public static class UserConfig {
        private String id;
    }
    
    /**
     * Configuration d'un message.
     */
    public static class MessageConfig {
        private String content;
        private LocalDateTime dateCreation;
    }
   
    /**
     * Constructeur de la classe MicroblogInitialiser.
     */
    public MicroblogInitialiser() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new AdapterLocalDateTime())
            .create();
    }
    
    /**
     * Initialise le modèle avec la configuration.
     * @param model Le modèle à initialiser
     */
    public void initialiser(final InterfaceModel model) {
        MicroblogConfig config = chargerConfig();
        for (UserConfig userConfig : config.users) {
            model.creeUtilisateur(userConfig.id);
        }
        for (MessageConfig msgConfig : config.messages) {
            model.ajouterMessageDate(msgConfig.content, msgConfig.dateCreation);
        }
        saveConfig(config);
    }
    
    /**
     * Charge la configuration à partir d'un fichier JSON.
     * @return La configuration chargée
     */
    private MicroblogConfig chargerConfig() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                return gson.fromJson(reader, MicroblogConfig.class);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,
                "Erreur lors de la lecture du fichier de configuration", e);
                return defaultConfig();
            }
        }
        return defaultConfig();
    }

    /**
     * Sauvegarde la configuration dans un fichier JSON.
     * @param config La configuration à sauvegarder
     */
    private void saveConfig(final MicroblogConfig config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la sauvegarde de la configuration", e);
        }
    }
    
    /**
     * Crée une configuration par défaut.
     * @return La configuration par défaut
     */
    private MicroblogConfig defaultConfig() {
        MicroblogConfig config = new MicroblogConfig();
        
        // Ajoute des utilisateurs par défaut
        UserConfig user1 = new UserConfig();
        user1.id = "Alice";
        config.users.add(user1);
        
        UserConfig user2 = new UserConfig();
        user2.id = "Bob";
        config.users.add(user2);
        
        // Ajoute des messages par défaut
        MessageConfig msg1 = new MessageConfig();
        msg1.content = "Premier message de test";
        msg1.dateCreation = LocalDateTime.now();
        config.messages.add(msg1);
        
        MessageConfig msg2 = new MessageConfig();
        msg2.content = "Second message de test";
        msg2.dateCreation = LocalDateTime.now().minusHours(1);
        config.messages.add(msg2);
        
        return config;
    }
} 
