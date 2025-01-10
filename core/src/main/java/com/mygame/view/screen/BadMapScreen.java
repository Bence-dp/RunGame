package com.mygame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygame.controller.GameManager;
import com.badlogic.gdx.Screen;

/**
 * La classe {@code BadMapScreen} représente l'écran affiché lorsque les cartes dans le fichier JSON
 * ne sont pas valides. Cet écran affiche un message d'erreur à l'utilisateur pour l'informer du problème.
 *
 * <p>Le message d'erreur est affiché en rouge et centré à l'écran. L'écran permet à l'utilisateur
 * de quitter ou de fermer l'application après avoir pris connaissance du message.</p>
 */
public class BadMapScreen implements Screen {

    private Stage stage;  // Stage pour gérer les éléments d'interface utilisateur (UI)
    private GameManager gameManager;  // Référence au gestionnaire du jeu

    /**
     * Constructeur de la classe {@code BadMapScreen}.
     *
     * @param gameManager Le gestionnaire de jeu permettant de gérer les niveaux et autres éléments du jeu.
     */
    public BadMapScreen(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Méthode appelée lorsque l'écran est affiché pour la première fois.
     * Elle crée et configure les éléments à afficher sur l'écran, notamment le message d'erreur.
     */
    @Override
    public void show() {
        // Créer un stage pour afficher des éléments à l'écran
        stage = new Stage(new ScreenViewport());

        // Créer un style de Label avec une police et une couleur rouge
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);

        // Créer un label pour le message d'erreur
        Label errorLabel = new Label("Aucune map valide est présente dans le JSON, veuillez les modifier et relancer le jeu", labelStyle);

        // Centrer le message à l'écran
        errorLabel.setPosition(Gdx.graphics.getWidth() / 2 - errorLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        // Ajouter le label au stage pour l'afficher
        stage.addActor(errorLabel);

        // Mettre en place l'input pour que l'utilisateur puisse quitter l'écran en cliquant
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Méthode appelée pour dessiner l'écran à chaque frame.
     * Elle efface l'écran et dessine le contenu du stage (ici, le message d'erreur).
     *
     * @param delta Le temps écoulé entre deux frames, utilisé pour les animations et les mises à jour.
     */
    @Override
    public void render(float delta) {
        // Effacer l'écran et dessiner le stage
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Couleur de fond noire
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner le contenu du stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Méthode appelée lorsqu'il y a un redimensionnement de l'écran.
     * Elle met à jour la taille du viewport pour s'adapter aux nouvelles dimensions de l'écran.
     *
     * @param width  La nouvelle largeur de l'écran.
     * @param height La nouvelle hauteur de l'écran.
     */
    @Override
    public void resize(int width, int height) {
        // Mettre à jour la taille du viewport lors du redimensionnement de l'écran
        stage.getViewport().update(width, height, true);
    }

    /**
     * Méthode appelée lorsque l'écran est caché.
     * Elle libère les ressources associées au stage.
     */
    @Override
    public void hide() {
        // Libérer les ressources lorsque l'écran est caché
        stage.dispose();
    }

    /**
     * Méthode appelée lors de la destruction de l'écran.
     * Elle est généralement utilisée pour nettoyer les ressources, mais ici elle est vide.
     */
    @Override
    public void dispose() {
        // Libération des ressources si nécessaire
    }

    /**
     * Méthode appelée lorsque le jeu est mis en pause.
     * Elle peut être utilisée pour gérer le comportement en pause si nécessaire.
     */
    @Override
    public void pause() {
        // Gérer la pause si nécessaire
    }

    /**
     * Méthode appelée lorsque le jeu reprend après une pause.
     * Elle peut être utilisée pour gérer la reprise du jeu si nécessaire.
     */
    @Override
    public void resume() {
        // Gérer la reprise si nécessaire
    }
}
