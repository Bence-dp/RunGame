package com.mygame.utils.ContactListener;

import com.badlogic.gdx.physics.box2d.*;
import com.mygame.controller.GameManager;
import com.mygame.model.LevelLoader;
import com.mygame.view.screen.LoseScreen;
import com.mygame.Main;

/**
 * La classe {@code LoseListener} écoute les contacts entre le joueur et la zone de mort dans le jeu.
 * Elle est responsable de la détection lorsque le joueur entre en collision avec une zone de mort et marque le jeu comme terminé.
 * <p>
 * Si le joueur entre en contact avec une zone de mort, le jeu est considéré comme perdu et l'écran de défaite est affiché.
 * </p>
 */
public class LoseListener implements ContactListener {

    private final Main game;                // Référence à l'objet principal du jeu
    private final LevelLoader levelLoader;  // Référence au chargeur de niveau
    private final GameManager gameManager;  // Référence au gestionnaire de jeu
    private boolean gameOver;               // Indique si le jeu est terminé (perdu)

    /**
     * Constructeur de {@code LoseListener}.
     *
     * @param game L'objet principal du jeu (Main).
     * @param levelLoader Le chargeur de niveau (LevelLoader).
     * @param gameManager Le gestionnaire de jeu (GameManager).
     */
    public LoseListener(Main game, LevelLoader levelLoader, GameManager gameManager) {
        this.game = game;
        this.levelLoader = levelLoader;
        this.gameManager = gameManager;
        this.gameOver = false;
    }

    /**
     * Méthode appelée lorsque le contact entre deux objets commence.
     * Si le joueur touche une zone de mort, le jeu est marqué comme terminé (perdu).
     *
     * @param contact L'objet représentant le contact entre deux objets.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            if (isPlayer(fixtureA) && isDeadzone(fixtureB)) {
                gameOver = true; // Marque que le joueur a perdu
                System.err.println("mort");
            } else if (isPlayer(fixtureB) && isDeadzone(fixtureA)) {
                gameOver = true; // Marque que le joueur a perdu
                System.err.println("mort");
            }
        }
    }

    /**
     * Vérifie si la fixture donnée représente une zone de mort.
     *
     * @param fixture La fixture à vérifier.
     * @return {@code true} si la fixture est une zone de mort, sinon {@code false}.
     */
    private boolean isDeadzone(Fixture fixture) {
        return fixture.getUserData().equals("deadzone");
    }

    /**
     * Vérifie si la fixture donnée représente un joueur.
     *
     * @param fixture La fixture à vérifier.
     * @return {@code true} si la fixture est un joueur, sinon {@code false}.
     */
    private boolean isPlayer(Fixture fixture) {
        return fixture.getUserData().equals("player");
    }

    /**
     * Méthode appelée lorsque le contact entre les objets se termine.
     * Aucune logique spécifique n'est nécessaire dans ce cas.
     *
     * @param contact L'objet représentant le contact qui a pris fin.
     */
    @Override
    public void endContact(Contact contact) {
        // Pas de logique nécessaire ici pour ce cas
    }

    /**
     * Méthode appelée avant la solution de la collision.
     * Aucune logique spécifique n'est nécessaire ici pour ce cas.
     *
     * @param contact L'objet représentant le contact.
     * @param manifold L'ancien manifold de la collision.
     */
    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        // Pas de logique nécessaire ici pour ce cas
    }

    /**
     * Méthode appelée après la solution de la collision.
     * Aucune logique spécifique n'est nécessaire ici pour ce cas.
     *
     * @param contact L'objet représentant le contact.
     * @param contactImpulse L'impact de la collision.
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        // Pas de logique nécessaire ici pour ce cas
    }

    /**
     * Met à jour l'état du jeu lorsque le joueur est mort.
     * Cette méthode arrête la simulation physique, réinitialise le nombre de pièces et passe à l'écran de défaite (LoseScreen).
     */
    public void update() {
        if (gameOver) {
            // Arrête la simulation physique du monde
            gameManager.getSoundFactory().playSound("lose");
            this.gameOver = false;
            levelLoader.getMap().getWorld().step(0, 0, 0);  // Arrêt de la simulation physique
            gameManager.setCoin(0);  // Réinitialise le nombre de pièces

            // Passe à l'écran de défaite (LoseScreen)
            System.err.println("Changement d'écran vers LoseScreen");
            game.setScreen(new LoseScreen(game, gameManager));  // Affiche l'écran de défaite
        }
    }
}
