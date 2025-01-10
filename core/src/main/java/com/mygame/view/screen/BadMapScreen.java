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

public class BadMapScreen implements Screen {

    private Stage stage;
    private GameManager gameManager;

    public BadMapScreen(GameManager gameManager) {
        this.gameManager = gameManager;
    }

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

    @Override
    public void render(float delta) {
        // Effacer l'écran et dessiner le stage
        Gdx.gl.glClearColor(0, 0, 0, 1); // couleur de fond noire
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner le contenu du stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Mettre à jour la taille du viewport lors du redimensionnement de l'écran
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Libérer les ressources lorsque l'écran est caché
        stage.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {
        // Gérer la pause si nécessaire
    }

    @Override
    public void resume() {
        // Gérer la reprise si nécessaire
    }


}
