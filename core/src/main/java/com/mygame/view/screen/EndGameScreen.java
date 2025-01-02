package com.mygame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygame.Main;
import com.mygame.controller.GameManager;

/**
 * L'écran de fin de jeu qui s'affiche lorsque le joueur termine une partie.
 * Il permet au joueur de redémarrer le jeu ou de retourner au menu principal.
 */
public class EndGameScreen implements Screen {
    private final Main game;                   // Référence à l'application principale pour changer d'écran
    private final GameManager gameManager;     // Gestionnaire de jeu pour accéder aux niveaux et à l'état du jeu
    private final Stage stage;                 // Stage pour afficher les éléments graphiques
    private final BitmapFont font;             // Police utilisée pour afficher les textes
    private final SpriteBatch batch;           // SpriteBatch utilisé pour dessiner les éléments du stage
    private Label gameOverLabel;               // Label pour afficher le message de fin de jeu

    /**
     * Constructeur de {@code EndGameScreen}.
     *
     * Initialisation du jeu, du gestionnaire de jeu, du stage et des ressources graphiques
     * nécessaires pour l'écran de fin de jeu.
     *
     * @param game L'instance de l'application principale.
     * @param gameManager Le gestionnaire de jeu pour accéder aux niveaux et autres ressources.
     */
    public EndGameScreen(Main game, GameManager gameManager) {
        this.game = game;
        this.gameManager = gameManager;
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }

    /**
     * Cette méthode est appelée lorsque l'écran est montré pour la première fois.
     * Elle configure les éléments UI du screen (label "Game Over", instructions, et bouton de menu).
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Définit le stage pour gérer les entrées

        // Crée une table pour organiser les éléments
        Table table = new Table();
        table.top().center(); // Aligner les éléments au centre de l'écran
        table.setFillParent(true);

        // Crée un label "Good Game!"
        gameOverLabel = new Label("Good Game!", new Label.LabelStyle(font, null));
        gameOverLabel.setFontScale(2f); // Augmente la taille du texte
        gameOverLabel.setAlignment(Align.center); // Aligner le texte au centre
        table.add(gameOverLabel).expandX().padTop(100); // Ajouter le label à la table
        table.row();

        // Ajouter un label pour l'instruction "Appuyez sur Entrée"
        Label restartLabel = new Label("Appuyez sur Entrée pour recommencer le jeu", new Label.LabelStyle(font, null));
        restartLabel.setFontScale(1f); // Taille de police adaptée
        restartLabel.setAlignment(Align.center); // Centrer le texte
        table.add(restartLabel).expandX().padTop(20); // Ajouter un espacement entre le titre et l'instruction
        table.row();

        // Crée un bouton pour retourner au menu
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font; // Utiliser la police existante

        TextButton menuButton = new TextButton("Retour au Menu", buttonStyle);
        menuButton.getLabel().setFontScale(1.5f); // Augmenter la taille du texte
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(gameManager)); // Remplacer par votre écran de menu principal
            }
        });
        table.add(menuButton).padTop(50); // Ajouter le bouton à la table avec un espacement

        // Ajout du tableau au stage
        stage.addActor(table);
    }

    /**
     * Cette méthode est appelée à chaque frame pour dessiner l'écran.
     * Elle efface l'écran et dessine le contenu du stage. Elle gère également les entrées clavier
     * pour redémarrer le jeu ou quitter l'application.
     *
     * @param delta Temps écoulé depuis la dernière frame.
     */
    @Override
    public void render(float delta) {
        // Efface l'écran
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner le contenu du stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Mise à jour du stage
        stage.draw(); // Dessiner le stage

        // Gérer l'entrée utilisateur
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Redémarrer le niveau ou faire autre chose, comme revenir au menu
            gameManager.setCoin(0); // Réinitialiser les pièces collectées
            gameManager.setCurrentLevel(gameManager.getLevels().get(0)); // Redémarrer le niveau initial
            game.setScreen(new LevelScreen(gameManager, gameManager.getLevels().get(0))); // Remplacer par votre logique de redémarrage
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Quitter l'application
        }
    }

    /**
     * Cette méthode est appelée lorsque la taille de l'écran change.
     * Elle ajuste le viewport du stage pour s'assurer qu'il s'adapte correctement.
     *
     * @param width Largeur de la fenêtre.
     * @param height Hauteur de la fenêtre.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }

    /**
     * Cette méthode est appelée lorsque l'écran doit libérer des ressources.
     * Elle dispose des objets graphiques utilisés par l'écran.
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }
}
