package com.mygame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygame.Main;
import com.mygame.controller.GameManager;
import com.mygame.model.maps.Level;
import com.mygame.utils.saver.LoadManager;
import com.mygame.utils.saver.SaveData;

/**
 * Écran représentant le menu principal du jeu.
 * Permet à l'utilisateur de commencer une nouvelle partie,
 * reprendre une partie sauvegardée, reprendre la partie actuelle,
 * ou accéder aux paramètres.
 */
public class MenuScreen implements Screen {

    private final Main game;
    private final GameManager gameManager;
    private final Stage stage;
    private final BitmapFont font;
    private final SpriteBatch batch;

    /**
     * Constructeur de l'écran de menu.
     * Initialise les composants du menu et les boutons.
     *
     * @param gameManager Le gestionnaire du jeu, utilisé pour accéder à l'état du jeu.
     */
    public MenuScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        this.game = gameManager.getGame();
        this.stage = new Stage();
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();

        Gdx.input.setInputProcessor(stage);

        createNewGameButton("Nouvelle partie", 100, 400);
        createResumeGameButton("Reprendre Sauvegarde", 100, 300);
        createCurrentGameButton("Reprendre Partie en Cours", 100, 200);
        createSettingsButton("Paramètres", 100, 100);
    }

    /**
     * Crée et ajoute un bouton permettant de démarrer une nouvelle partie.
     *
     * @param text Le texte du bouton.
     * @param x La position X du bouton sur l'écran.
     * @param y La position Y du bouton sur l'écran.
     */
    private void createNewGameButton(String text, float x, float y) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.downFontColor = Color.GRAY;

        TextButton button = new TextButton(text, style);

        button.setPosition(x, y);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Réinitialiser le jeu au premier niveau avec 0 pièces
                Level firstLevel = gameManager.getLevels().get(0); // Premier niveau
                gameManager.setCurrentLevel(firstLevel);
                gameManager.setCoin(0); // Réinitialiser les pièces
                game.setScreen(new LevelScreen(gameManager, firstLevel));
            }
        });

        stage.addActor(button);
    }

    /**
     * Crée et ajoute un bouton permettant de reprendre la dernière sauvegarde du jeu.
     *
     * @param text Le texte du bouton.
     * @param x La position X du bouton sur l'écran.
     * @param y La position Y du bouton sur l'écran.
     */
    private void createResumeGameButton(String text, float x, float y) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.downFontColor = Color.GRAY;

        TextButton button = new TextButton(text, style);

        button.setPosition(x, y);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Charger les données de sauvegarde
                SaveData savedData = LoadManager.loadGame();
                if (savedData != null) {
                    Level savedLevel = savedData.getLastLevel();
                    if (savedLevel != null) {
                        // Vérifier si le niveau sauvegardé existe dans gameManager.getLevels()
                        boolean levelExists = false;
                        for (Level level : gameManager.getLevels()) {
                            if (level.getPath().equals(savedLevel.getPath())) {
                                levelExists = true;
                                break;
                            }
                        }

                        if (levelExists) {
                            gameManager.setCurrentLevel(savedLevel);
                            gameManager.setCoin(savedData.getCoins());
                            game.setScreen(new LevelScreen(gameManager, savedLevel));
                        } else {
                            System.out.println("Le niveau sauvegardé n'existe pas dans les niveaux disponibles.");
                        }
                    } else {
                        System.out.println("Niveau sauvegardé introuvable.");
                    }
                } else {
                    System.out.println("Aucune sauvegarde trouvée.");
                }
            }
        });

        stage.addActor(button);
    }


    /**
     * Crée et ajoute un bouton permettant de reprendre la partie actuelle.
     *
     * @param text Le texte du bouton.
     * @param x La position X du bouton sur l'écran.
     * @param y La position Y du bouton sur l'écran.
     */
    private void createCurrentGameButton(String text, float x, float y) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.downFontColor = Color.GRAY;

        TextButton button = new TextButton(text, style);

        button.setPosition(x, y);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Reprendre la partie actuelle
                Level currentLevel = gameManager.getCurrentLevel();
                if (currentLevel != null) {
                    game.setScreen(new LevelScreen(gameManager, currentLevel));
                } else {
                    System.out.println("Aucune partie en cours trouvée.");
                }
            }
        });

        stage.addActor(button);
    }

    /**
     * Crée et ajoute un bouton permettant d'accéder aux paramètres du jeu.
     *
     * @param text Le texte du bouton.
     * @param x La position X du bouton sur l'écran.
     * @param y La position Y du bouton sur l'écran.
     */
    private void createSettingsButton(String text, float x, float y) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.downFontColor = Color.GRAY;

        TextButton button = new TextButton(text, style);

        button.setPosition(x, y);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Naviguer vers l'écran des paramètres
                game.setScreen(new SettingsScreen(gameManager));
            }
        });

        stage.addActor(button);
    }

    /**
     * Rendu de l'écran de menu, où sont dessinés les éléments graphiques.
     *
     * @param delta Le temps écoulé depuis le dernier rendu.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Menu Principal", 100, 500);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Redimensionne la vue de l'écran en fonction de la taille de la fenêtre.
     *
     * @param width La largeur de la nouvelle fenêtre.
     * @param height La hauteur de la nouvelle fenêtre.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Libère les ressources lorsque l'écran est supprimé.
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }

    /**
     * Méthode appelée lors de l'affichage de l'écran.
     */
    @Override
    public void show() {}

    /**
     * Méthode appelée lors de la dissimulation de l'écran.
     */
    @Override
    public void hide() {}

    /**
     * Méthode appelée lorsque l'écran est mis en pause.
     */
    @Override
    public void pause() {}

    /**
     * Méthode appelée lorsque l'écran reprend.
     */
    @Override
    public void resume() {}
}
