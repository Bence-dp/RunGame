package com.mygame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.mygame.config.KeyBindings;
import com.mygame.controller.GameManager;
import com.mygame.model.maps.Level;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygame.Main;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * L'écran des paramètres où les utilisateurs peuvent remapper les contrôles du jeu.
 */
public class SettingsScreen implements Screen {

    private final Stage stage;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final KeyBindings keyBindings;
    private String message = "Cliquez sur Remap pour remapper";
    private boolean isRemapping = false;
    private String remappingAction = null;
    private GameManager gameManager;
    private Main game;

    /**
     * Initialise l'écran des paramètres avec les configurations nécessaires.
     *
     * @param gameManager Le gestionnaire du jeu pour accéder aux configurations et niveaux.
     */
    public SettingsScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.game = gameManager.getGame();
        this.keyBindings = gameManager.getKeyBindings();
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        Gdx.input.setInputProcessor(stage);

        createButton("Remap Move Right", 100, 300, "MoveRight");
        createButton("Remap Move Left", 100, 250, "MoveLeft");
        createButton("Remap Jump", 100, 200, "Jump");

        createBackToMenuButton();
    }

    /**
     * Crée un bouton pour remapper une action spécifique.
     *
     * @param text   Le texte du bouton.
     * @param x      La position X du bouton.
     * @param y      La position Y du bouton.
     * @param action L'action à remapper (par exemple, "MoveRight").
     */
    private void createButton(String text, float x, float y, String action) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton button = new TextButton(text, buttonStyle);
        button.setPosition(x, y);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startRemapping(action);
            }
        });

        stage.addActor(button);
    }

    /**
     * Crée un bouton pour revenir au menu principal.
     */
    private void createBackToMenuButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton backButton = new TextButton("Retour au Menu", buttonStyle);
        float x = Gdx.graphics.getWidth() - backButton.getWidth() - 20;
        float y = Gdx.graphics.getHeight() - backButton.getHeight() - 20;
        backButton.setPosition(x, y);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(gameManager));
            }
        });

        stage.addActor(backButton);
    }

    /**
     * Démarre le processus de remappage pour l'action spécifiée.
     *
     * @param action L'action à remapper (par exemple, "MoveRight").
     */
    private void startRemapping(String action) {
        isRemapping = true;
        remappingAction = action;
        message = "Appuyez sur une touche pour " + action;
    }

    @Override
    public void show() {

    }

    /**
     * Rendu de l'écran des paramètres et mise à jour chaque frame.
     *
     * @param delta Le temps écoulé depuis la dernière frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        batch.begin();
        font.draw(batch, "Paramètres", 100, 400);
        font.draw(batch, "Déplacer à droite : " + Input.Keys.toString(keyBindings.getMoveRightKey()), 100, 300);
        font.draw(batch, "Déplacer à gauche : " + Input.Keys.toString(keyBindings.getMoveLeftKey()), 100, 250);
        font.draw(batch, "Sauter : " + Input.Keys.toString(keyBindings.getJumpKey()), 100, 200);
        font.draw(batch, message, 100, 150);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Met à jour l'état du remappage en vérifiant si l'utilisateur appuie sur une nouvelle touche.
     */
    private void update() {
        if (isRemapping) {
            int newKey = getNewKeyPressed();
            if (newKey != -1) {
                if ("MoveRight".equals(remappingAction)) {
                    keyBindings.setMoveRightKey(newKey);
                    message = "Déplacer à droite remappé sur : " + Input.Keys.toString(newKey);
                } else if ("MoveLeft".equals(remappingAction)) {
                    keyBindings.setMoveLeftKey(newKey);
                    message = "Déplacer à gauche remappé sur : " + Input.Keys.toString(newKey);
                } else if ("Jump".equals(remappingAction)) {
                    keyBindings.setJumpKey(newKey);
                    message = "Sauter remappé sur : " + Input.Keys.toString(newKey);
                }

                isRemapping = false;
                remappingAction = null;
            }
        }
    }

    /**
     * Vérifie si une nouvelle touche a été pressée par l'utilisateur.
     *
     * @return Le code de la touche pressée, ou -1 si aucune touche n'a été pressée.
     */
    private int getNewKeyPressed() {
        for (int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                return key;
            }
        }

        for (int key = Input.Keys.NUM_0; key <= Input.Keys.NUM_9; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                return key;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            return Input.Keys.SPACE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            return Input.Keys.LEFT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            return Input.Keys.RIGHT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            return Input.Keys.UP;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            return Input.Keys.DOWN;
        }

        return -1;
    }

    /**
     * Crée un arrière-plan avec la couleur spécifiée.
     *
     * @param width  La largeur de l'arrière-plan.
     * @param height La hauteur de l'arrière-plan.
     * @param r      Le composant rouge de la couleur.
     * @param g      Le composant vert de la couleur.
     * @param b      Le composant bleu de la couleur.
     * @param a      Le composant alpha de la couleur.
     * @return Un Drawable représentant l'arrière-plan.
     */
    private Drawable createBackgroundDrawable(int width, int height, int r, int g, int b, int a) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r / 255f, g / 255f, b / 255f, a / 255f);
        pixmap.fillRectangle(0, 0, width, height);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(texture);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }
}
