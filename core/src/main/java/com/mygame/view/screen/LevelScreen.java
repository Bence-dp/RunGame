package com.mygame.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.controller.GameManager;
import com.mygame.model.LevelLoader;
import com.mygame.model.entities.GameEntity;
import com.mygame.model.maps.Level;
import com.mygame.view.screen.HUD.GameHUD;

/**
 * L'écran du niveau qui gère l'affichage du jeu, la caméra et les interactions avec le joueur.
 * Cet écran est responsable du rendu du niveau, du suivi du joueur avec la caméra,
 * de la gestion des entités et de l'interface utilisateur (HUD).
 */
public class LevelScreen implements Screen {

    private Level level;                // Le niveau actuel
    private String mapPath;             // Chemin de la carte du niveau
    private GameHUD gameHUD;            // L'interface utilisateur pour afficher des informations sur le jeu
    private LevelLoader levelLoader;    // Le LevelLoader qui va gérer le chargement du niveau
    private GameEntity player;          // Le joueur pour déplacer la caméra
    private SpriteBatch spriteBatch;    // Utilisé pour rendre les entités et la carte

    // Variables pour la gestion de la carte
    private float mapWidth;             // Largeur de la carte
    private float mapHeight;            // Hauteur de la carte

    private final float BASE_VIEWPORT_WIDTH = 16f;  // Largeur fixe du viewport pour maintenir un FOV constant
    private final float BASE_VIEWPORT_HEIGHT = 16f; // Hauteur fixe du viewport pour maintenir un FOV constant

    /**
     * Constructeur pour initialiser l'écran du niveau.
     *
     * @param gameManager Le gestionnaire de jeu contenant l'état du jeu et les niveaux.
     * @param level Le niveau à afficher.
     */
    public LevelScreen(GameManager gameManager, Level level) {
        this.level = gameManager.getCurrentLevel();
        this.mapPath = gameManager.getCurrentLevel().getPath();
        this.levelLoader = new LevelLoader(gameManager, level); // Initialiser le LevelLoader
        this.gameHUD = new GameHUD(levelLoader);
    }

    /**
     * Cette méthode est appelée lorsque l'écran est montré pour la première fois.
     * Elle initialise les entités du niveau et les objets nécessaires.
     */
    @Override
    public void show() {
        // Charger les entités et la carte via le LevelLoader
        player = levelLoader.getEntityFactory().getPlayer(); // Récupérer le joueur du niveau

        // Initialiser le SpriteBatch pour dessiner les entités et la carte
        spriteBatch = new SpriteBatch();

        // Charger les dimensions de la carte
        mapWidth = levelLoader.getMap().getMapWidth();  // Supposons que le LevelLoader a cette méthode
        mapHeight = levelLoader.getMap().getMapHeight(); // Idem pour la hauteur
    }

    /**
     * Cette méthode met à jour la caméra pour qu'elle suive le joueur à l'écran.
     * Elle ajuste la position de la caméra pour centrer sur le joueur, tout en s'assurant
     * que la caméra ne sort pas des limites de la carte.
     */
    private void updateCamera() {
        // Récupérer la position du joueur
        float playerX = player.getX();
        float playerY = player.getY();

        // Récupérer la caméra
        OrthographicCamera camera = levelLoader.getCamera();

        // Calculer la position de la caméra pour centrer sur le joueur
        float cameraX = playerX - camera.viewportWidth / 2;
        float cameraY = playerY - camera.viewportHeight / 2;

        // Limiter la position de la caméra aux bords de la carte
        if (cameraX < 0) {
            cameraX = 0;
        }
        if (cameraX > mapWidth - camera.viewportWidth) {
            cameraX = mapWidth - camera.viewportWidth;
        }

        // Mettre à jour la position de la caméra
        camera.position.set(cameraX + camera.viewportWidth / 2, cameraY + camera.viewportHeight / 2, 0);
        camera.update(); // Appliquer la mise à jour de la caméra
    }

    /**
     * Cette méthode est appelée lorsque la taille de l'écran change.
     * Elle ajuste le viewport de la caméra pour maintenir un rapport constant.
     *
     * @param width La nouvelle largeur de l'écran.
     * @param height La nouvelle hauteur de l'écran.
     */
    @Override
    public void resize(int width, int height) {
        // Garder le FOV constant, nous ajustons uniquement le viewport sans toucher au FOV
        OrthographicCamera camera = levelLoader.getCamera();

        // Garder un ratio constant (vous pouvez ajuster la taille du jeu à votre convenance)
        float aspectRatio = (float) width / (float) height;

        // Ajuster le viewport en fonction de la taille de la fenêtre
        camera.viewportWidth = BASE_VIEWPORT_WIDTH * aspectRatio;
        camera.viewportHeight = BASE_VIEWPORT_HEIGHT;

        camera.update(); // Appliquer la mise à jour de la caméra
    }

    /**
     * Cette méthode est appelée lorsque l'écran est mis en pause (non utilisé ici).
     */
    @Override
    public void pause() {}

    /**
     * Cette méthode est appelée lorsque l'écran est repris après avoir été mis en pause (non utilisé ici).
     */
    @Override
    public void resume() {}

    /**
     * Cette méthode est appelée lorsque l'écran est caché (non utilisé ici).
     */
    @Override
    public void hide() {}

    /**
     * Cette méthode est appelée pour le rendu de l'écran à chaque frame.
     * Elle gère le rendu des entités, de la carte et de l'interface utilisateur (HUD),
     * et met à jour la caméra pour suivre le joueur.
     *
     * @param delta Temps écoulé depuis la dernière frame.
     */
    @Override
    public void render(float delta) {
        // Effacer l'écran avec une couleur sombre
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rendre la carte et les entités
        levelLoader.render();
        gameHUD.render(spriteBatch); // Afficher l'interface utilisateur
        gameHUD.update(delta);       // Mettre à jour l'interface utilisateur

        // Appliquer la mise à jour de la physique du monde
        levelLoader.getMap().getWorld().step(delta, 2, 2);
        levelLoader.getCompositeContactListener().getCollectibleListener().processPendingDestructions(levelLoader.getMap().getWorld());

        // Mise à jour de la caméra pour suivre le joueur
        if (player != null) {
            levelLoader.updateCamera(player);  // Met à jour la caméra pour suivre le joueur
        }

        // Appliquer la caméra au renderer de la carte et rendre le niveau
    }

    /**
     * Libère les ressources utilisées par cet écran.
     * Cela inclut le SpriteBatch, le LevelLoader, et toute autre ressource allouée.
     */
    @Override
    public void dispose() {
        gameHUD.dispose();
        if (spriteBatch != null) {
            spriteBatch.dispose(); // Libérer le SpriteBatch
        }
        if (levelLoader != null) {
            levelLoader.dispose(); // Libérer les ressources du LevelLoader
        }
    }
}
