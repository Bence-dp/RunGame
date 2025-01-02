package com.mygame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygame.Main;
import com.mygame.controller.GameManager;
import com.mygame.model.maps.Level;

/**
 * L'écran d'accueil du jeu qui s'affiche avant le menu principal.
 * Cet écran affiche le titre du jeu et une invite à appuyer sur "Entrée" pour passer au menu principal.
 */
public class FirstScreen implements Screen {

    private SpriteBatch batch;               // SpriteBatch utilisé pour dessiner les éléments à l'écran
    private BitmapFont font;                 // Police utilisée pour afficher les textes
    private Main game;                       // Référence à l'application principale pour changer d'écran
    private GameManager gameManager;         // Gestionnaire de jeu pour accéder aux niveaux et à l'état du jeu
    private final Array<Level> levels;       // Liste des niveaux du jeu

    /**
     * Constructeur de {@code FirstScreen}.
     *
     * Initialisation du gestionnaire de jeu, de l'application principale, et des niveaux.
     * Initialise également les ressources graphiques nécessaires à l'écran d'accueil.
     *
     * @param gameManager Le gestionnaire de jeu pour accéder aux niveaux et autres ressources.
     */
    public FirstScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.game = gameManager.getGame();
        this.levels = gameManager.getLevels();
        batch = new SpriteBatch();
        font = new BitmapFont(); // Police par défaut
    }

    /**
     * Cette méthode est appelée lorsque l'écran est montré pour la première fois.
     * Elle est utilisée pour initialiser les ressources spécifiques à cet écran, bien qu'il n'y en ait pas
     * dans cet exemple.
     */
    @Override
    public void show() {
        // Initialisation des ressources ou des objets spécifiques à cet écran.
    }

    /**
     * Cette méthode est appelée à chaque frame pour dessiner l'écran.
     * Elle efface l'écran et dessine le titre du jeu ainsi que l'instruction "Press ENTER to go to Menu".
     * Elle gère également l'entrée utilisateur pour changer d'écran lorsque "ENTER" est pressé.
     *
     * @param delta Temps écoulé depuis la dernière frame.
     */
    @Override
    public void render(float delta) {
        // Effacer l'écran avec une couleur (par exemple noir)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "CourseGame", 250, 400); // Affiche le titre du jeu
        font.draw(batch, "Press ENTER to go to Menu", 0, 200); // Affiche l'instruction pour passer au menu
        batch.end();

        // Détecter l'entrée utilisateur pour changer d'écran
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(gameManager)); // Changer d'écran pour le menu principal
        }
    }

    /**
     * Cette méthode est appelée lorsque la taille de l'écran change.
     * Elle est utilisée pour gérer la redimension de l'écran, mais n'est pas utilisée dans cet exemple.
     *
     * @param width Largeur de la fenêtre.
     * @param height Hauteur de la fenêtre.
     */
    @Override
    public void resize(int width, int height) {
        // Gestion de la redimension
    }

    /**
     * Cette méthode est appelée lorsque l'écran est mis en pause (non utilisé ici).
     */
    @Override
    public void pause() {
    }

    /**
     * Cette méthode est appelée lorsque l'écran est repris après avoir été mis en pause (non utilisé ici).
     */
    @Override
    public void resume() {
    }

    /**
     * Cette méthode est appelée lorsque l'écran est caché (non utilisé ici).
     */
    @Override
    public void hide() {
        // Libération des ressources spécifiques à cet écran si nécessaire.
    }

    /**
     * Cette méthode est appelée lorsque l'écran doit libérer des ressources.
     * Elle dispose des objets graphiques utilisés par l'écran.
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
