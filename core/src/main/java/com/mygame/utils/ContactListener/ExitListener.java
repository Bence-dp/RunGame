package com.mygame.utils.ContactListener;

import com.badlogic.gdx.physics.box2d.*;
import com.mygame.controller.GameManager;
import com.mygame.model.LevelLoader;
import com.mygame.view.screen.LevelScreen;
import com.mygame.Main;

/**
 * La classe {@code ExitListener} écoute les contacts entre le joueur et la sortie d'un niveau.
 * Elle est responsable de la détection lorsqu'un joueur touche une sortie et marque le niveau comme terminé.
 * <p>
 * Lorsque le joueur entre en contact avec une sortie, le niveau est marqué comme terminé et le jeu peut procéder à la transition vers le niveau suivant.
 * </p>
 */
public class ExitListener implements ContactListener {

    private final Main game;                // Référence à l'objet principal du jeu
    private final LevelLoader levelLoader;  // Référence au chargeur de niveau
    private final GameManager gameManager;  // Référence au gestionnaire de jeu
    private boolean levelCompleted;         // Indique si le niveau est terminé

    /**
     * Constructeur de {@code ExitListener}.
     *
     * @param game L'objet principal du jeu (Main).
     * @param levelLoader Le chargeur de niveau (LevelLoader).
     * @param gameManager Le gestionnaire de jeu (GameManager).
     */
    public ExitListener(Main game, LevelLoader levelLoader, GameManager gameManager) {
        this.game = game;
        this.levelLoader = levelLoader;
        this.gameManager = gameManager;
        this.levelCompleted = false;
    }

    /**
     * Méthode appelée lorsque le contact entre deux objets commence.
     * Si le joueur touche une sortie, le niveau est marqué comme terminé.
     *
     * @param contact L'objet représentant le contact entre deux objets.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            if (isPlayer(fixtureA) && isExit(fixtureB)) {
                levelCompleted = true; // Marque que le niveau est terminé
                System.out.println("Niveau terminé - Player a touché une sortie.");
            } else if (isPlayer(fixtureB) && isExit(fixtureA)) {
                levelCompleted = true; // Marque que le niveau est terminé
                System.out.println("Niveau terminé - Player a touché une sortie.");
            }
        }
    }

    /**
     * Vérifie si la fixture donnée représente une sortie.
     *
     * @param fixture La fixture à vérifier.
     * @return {@code true} si la fixture est une sortie, sinon {@code false}.
     */
    private boolean isExit(Fixture fixture) {
        return fixture.getUserData().equals("exit");
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
     * Met à jour l'état du niveau lorsque le joueur a touché une sortie.
     * Cette méthode marque le niveau comme terminé et passe à l'écran suivant.
     */
    public void update() {
        if (levelCompleted) {
            gameManager.getSoundFactory().playSound("win");
            this.levelCompleted = false;
            // Marque le niveau comme terminé dans le GameManager
            levelLoader.getMap().getWorld().step(0, 0, 0);

            gameManager.levelCompleted();  // Indique que le niveau est terminé

            // Nettoie les ressources associées au niveau actuel
            // Passe à l'écran LevelScreen (niveau suivant)
            System.out.println("Changement d'écran vers LevelScreen");
            //game.setScreen(new LevelScreen(gameManager, gameManager.getCurrentLevel()));
        }
    }
}
