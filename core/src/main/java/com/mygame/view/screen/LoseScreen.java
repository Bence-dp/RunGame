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
 * L'écran de la perte de jeu (Game Over).
 * Cet écran est affiché lorsque le joueur perd, avec une option pour recommencer ou quitter le jeu.
 */
public class LoseScreen implements Screen {

    private final Main game;           // Référence à l'objet principal du jeu
    private final GameManager gameManager; // Référence au gestionnaire de jeu
    private final Stage stage;         // Le stage pour afficher les éléments UI
    private final BitmapFont font;     // Police utilisée pour le texte
    private final SpriteBatch batch;   // SpriteBatch pour le rendu des éléments graphiques
    private Label gameOverLabel;       // Le label affichant "Game Over"

    /**
     * Constructeur pour initialiser l'écran de la perte.
     *
     * @param game Le jeu principal.
     * @param gameManager Le gestionnaire de jeu.
     */
    public LoseScreen(Main game, GameManager gameManager) {
        this.game = game;
        this.gameManager = gameManager;
        this.stage = new Stage();  // Crée un nouveau stage pour afficher les éléments UI
        this.batch = new SpriteBatch(); // Initialise un SpriteBatch pour les rendus
        this.font = new BitmapFont(); // Crée une nouvelle police par défaut
    }

    /**
     * Cette méthode est appelée lorsque l'écran est affiché pour la première fois.
     * Elle configure les éléments visuels comme le label "Game Over", les boutons et autres composants UI.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Définit le stage pour gérer les entrées de l'utilisateur

        // Crée une table pour organiser les éléments sur l'écran
        Table table = new Table();
        table.top().center(); // Aligne les éléments au centre de l'écran
        table.setFillParent(true); // Remplir tout l'écran

        // Crée un label "Game Over"
        gameOverLabel = new Label("Game Over", new Label.LabelStyle(font, null));
        gameOverLabel.setFontScale(2f); // Taille du texte agrandie
        gameOverLabel.setAlignment(Align.center); // Aligne le texte au centre
        table.add(gameOverLabel).expandX().padTop(100); // Ajoute le label à la table avec un espacement
        table.row(); // Nouvelle ligne dans la table

        // Crée un label avec une instruction
        Label restartLabel = new Label("Appuyez sur Entrée pour recommencer le niveau", new Label.LabelStyle(font, null));
        restartLabel.setFontScale(1f); // Taille de police adaptée
        restartLabel.setAlignment(Align.center); // Centrer le texte
        table.add(restartLabel).expandX().padTop(20); // Ajouter un espacement entre les éléments
        table.row();

        // Crée un bouton pour retourner au menu principal
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font; // Utiliser la même police pour le bouton

        TextButton menuButton = new TextButton("Retour au Menu", buttonStyle);
        menuButton.getLabel().setFontScale(1.5f); // Taille du texte du bouton augmentée
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(gameManager)); // Remplacer par l'écran du menu principal
            }
        });
        table.add(menuButton).padTop(50); // Ajouter le bouton à la table avec un espacement

        // Ajouter la table au stage
        stage.addActor(table);
    }

    /**
     * Cette méthode est appelée à chaque frame pour rendre l'écran.
     * Elle met à jour le stage et gère les entrées utilisateur (par exemple, redémarrer ou quitter).
     *
     * @param delta Temps écoulé depuis la dernière frame.
     */
    @Override
    public void render(float delta) {
        // Efface l'écran en arrière-plan
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // Définit une couleur de fond sombre
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Efface le contenu précédent de l'écran

        // Met à jour et dessine les éléments du stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Mise à jour du stage avec le deltaTime
        stage.draw(); // Dessiner les éléments du stage

        // Gérer les entrées utilisateur
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Si "Entrée" est pressé, redémarrer le niveau ou revenir à l'écran du menu
            game.setScreen(new LevelScreen(gameManager, gameManager.getCurrentLevel())); // Redémarrer le niveau
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Quitter l'application si "Échap" est pressé
        }
    }

    /**
     * Cette méthode est appelée lorsque la taille de la fenêtre change.
     * Elle met à jour le viewport du stage pour s'adapter à la nouvelle taille de l'écran.
     *
     * @param width La nouvelle largeur de l'écran.
     * @param height La nouvelle hauteur de l'écran.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Met à jour le viewport du stage
    }

    /**
     * Cette méthode est appelée lorsque l'écran est mis en pause. (Non utilisée ici)
     */
    @Override
    public void pause() {}

    /**
     * Cette méthode est appelée lorsque l'écran reprend après une pause. (Non utilisée ici)
     */
    @Override
    public void resume() {}

    /**
     * Cette méthode est appelée lorsque l'écran est caché. (Non utilisée ici)
     */
    @Override
    public void hide() {}

    /**
     * Cette méthode est appelée pour libérer les ressources utilisées par cet écran.
     * Cela inclut la libération des objets comme le SpriteBatch, la police, et le stage.
     */
    @Override
    public void dispose() {
        batch.dispose();  // Libérer le SpriteBatch
        font.dispose();   // Libérer la police
        stage.dispose();  // Libérer le stage
    }
}
