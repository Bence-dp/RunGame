package com.mygame.controller;

import com.badlogic.gdx.utils.Array;
import com.mygame.Main;
import com.mygame.common.SoundFactory;
import com.mygame.config.KeyBindings;
import com.mygame.controller.commands.Command;
import com.mygame.common.EntityFactory;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.mygame.model.entities.Player;
import com.mygame.model.maps.Level;
import java.util.HashMap;
import com.mygame.controller.commands.*;
import com.mygame.utils.saver.SaveData;
import com.mygame.utils.saver.SaveManager;
import com.mygame.view.screen.EndGameScreen;
import com.mygame.view.screen.LevelScreen;

/**
 * Le gestionnaire principal du jeu. Cette classe est responsable de la gestion des éléments
 * du jeu, y compris le joueur, les niveaux, les commandes et la progression du jeu.
 * Elle est implémentée en tant que Singleton, garantissant qu'il n'existe qu'une seule instance
 * de cette classe dans le jeu.
 */
public class GameManager {

    // L'instance unique du GameManager
    private static GameManager instance;

    // Références à l'EntityFactory, au joueur, au niveau actuel et aux commandes
    private EntityFactory entityFactory;
    private SoundFactory soundFactory;
    private Level currentLevel;
    private KeyBindings keyBindings;
    private Array<Level> levels;
    private Boolean needSave;
    private Main game;
    private HashMap<Integer, Command> commands;
    private int coin;

    /**
     * Constructeur privé du GameManager. Initialise les composants du jeu, comme le joueur,
     * l'EntityFactory et la configuration des commandes.
     *
     * @param game L'instance principale du jeu.
     * @param levels La liste des niveaux disponibles.
     * @param currentLevel Le niveau actuel du jeu.
     */
    private GameManager(Main game, Array<Level> levels, Level currentLevel) {
        this.currentLevel = currentLevel;
        this.coin = 0;
        this.game = game;
        this.needSave = false;
        this.soundFactory = SoundFactory.getInstance();
        soundFactory.loadSound("piece");
        soundFactory.loadSound("jump");
        soundFactory.loadSound("win");
        soundFactory.loadSound("lose");
        this.entityFactory = EntityFactory.getInstance(this);
        this.levels = levels;
        // Initialisation du joueur
        Sprite sprite = new Sprite(new Texture("Entities/player.png"));
        this.keyBindings = new KeyBindings();
        commands = new HashMap<>();
    }

    /**
     * Méthode statique pour obtenir l'instance unique de GameManager.
     *
     * @param game L'instance principale du jeu.
     * @param levels La liste des niveaux disponibles.
     * @param currentLevel Le niveau actuel du jeu.
     * @return L'instance unique de GameManager.
     */
    public static GameManager getInstance(Main game, Array<Level> levels, Level currentLevel) {
        if (instance == null) {
            instance = new GameManager(game, levels, currentLevel);  // Crée une nouvelle instance si elle n'existe pas
        }
        return instance;  // Retourne l'instance existante
    }

    /**
     * Retourne l'instance unique de GameManager.
     *
     * @return L'instance unique de GameManager.
     * @throws IllegalStateException Si GameManager n'est pas initialisé.
     */
    public static GameManager getGameManager() {
        if (instance == null) {
            throw new IllegalStateException("GameManager is not initialized. Call initialize() first.");
        }
        return instance;
    }

    /**
     * Méthode appelée lorsque le niveau est terminé. Gère l'ajout de pièces,
     * la transition vers le niveau suivant, ou l'affichage de l'écran de fin de jeu
     * si tous les niveaux sont terminés.
     */
    public void levelCompleted() {
        addCoin(getEntityFactory().getPlayer().getScore());
        if (currentLevel.getNext() != null) {
            currentLevel = currentLevel.getNext();
            System.out.println("Niveau terminé! Passage au niveau suivant : " + currentLevel.getName());
            game.setScreen(new LevelScreen(this, currentLevel));
        } else {
            System.out.println("Aucun niveau suivant. Vous avez terminé tous les niveaux !");
            game.setScreen(new EndGameScreen(game, this));
        }
    }

    /**
     * Méthode appelée si le niveau est passé, mais invalidé. Elle essaie de passer
     * au niveau suivant.
     */
    public void levelPassed() {
        if (currentLevel.getNext() != null) {
            currentLevel = currentLevel.getNext();
            System.out.println("Niveau invalide! Passage au niveau suivant : " + currentLevel.getName());
            game.setScreen(new LevelScreen(this, currentLevel));
        } else {
            System.out.println("Aucun niveau suivant. Vous avez terminé tous les niveaux !");
            game.setScreen(new EndGameScreen(game, this));
        }
    }

    /**
     * Récupère le niveau actuel.
     *
     * @return Le niveau actuel.
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Retourne l'instance de SoundFactory, utilisée pour gérer les sons du jeu.
     *
     * @return L'instance de SoundFactory.
     */
    public SoundFactory getSoundFactory() {
        return soundFactory;
    }

    /**
     * Définit le niveau actuel.
     *
     * @param currentLevel Le niveau à définir.
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Configure les commandes du joueur en fonction des touches définies dans les paramètres.
     *
     * @param player Le joueur pour lequel les commandes sont configurées.
     */
    public void setupCommands(Player player) {
        commands.put(keyBindings.getMoveLeftKey(), new MoveLeftCommand(player));
        commands.put(keyBindings.getMoveRightKey(), new MoveRightCommand(player));
        commands.put(keyBindings.getJumpKey(), new JumpCommand(player));
    }

    /**
     * Définit si l'état du jeu doit être sauvegardé.
     *
     * @param needSave Indique si le jeu nécessite une sauvegarde.
     */
    public void setNeedSave(Boolean needSave) {
        this.needSave = needSave;
    }

    /**
     * Récupère l'indicateur qui spécifie si une sauvegarde est nécessaire.
     *
     * @return {@code true} si le jeu nécessite une sauvegarde, sinon {@code false}.
     */
    public boolean getNeedSave() {
        return needSave;
    }

    /**
     * Récupère l'instance de l'EntityFactory.
     *
     * @return L'instance de l'EntityFactory.
     */
    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    /**
     * Récupère la liste des niveaux disponibles.
     *
     * @return La liste des niveaux.
     */
    public Array<Level> getLevels() {
        return levels;
    }

    /**
     * Récupère l'instance des KeyBindings, qui définit les configurations des touches.
     *
     * @return L'instance des KeyBindings.
     */
    public KeyBindings getKeyBindings() {
        return keyBindings;
    }

    /**
     * Récupère l'instance principale du jeu.
     *
     * @return L'instance principale du jeu.
     */
    public Main getGame() {
        return game;
    }

    /**
     * Définit le nombre de pièces du joueur.
     *
     * @param coin Le nombre de pièces à définir.
     */
    public void setCoin(int coin) {
        this.coin = coin;
    }

    /**
     * Ajoute des pièces au total des pièces du joueur.
     *
     * @param coin Le nombre de pièces à ajouter.
     */
    public void addCoin(int coin) {
        this.coin += coin;
    }

    /**
     * Récupère le nombre actuel de pièces du joueur.
     *
     * @return Le nombre de pièces du joueur.
     */
    public int getCoin() {
        return coin;
    }

    /**
     * Sauvegarde l'état actuel du jeu (niveau et pièces).
     *
     * @param level Le niveau actuel.
     * @param coin Le nombre de pièces du joueur.
     */
    public void saveGame(Level level, int coin) {
        SaveData saveData = new SaveData(level, coin);
        SaveManager.saveGame(saveData);
    }

    /**
     * Réinitialise l'instance de GameManager, utilisé pour recommencer le jeu.
     */
    public static void resetInstance() {
        instance = null;
    }
}
