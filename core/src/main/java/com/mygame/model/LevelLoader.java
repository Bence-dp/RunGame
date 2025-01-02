package com.mygame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygame.common.EntityFactory;
import com.mygame.common.SoundFactory;
import com.mygame.controller.GameManager;
import com.mygame.controller.InputHandler;
import com.mygame.model.maps.GameMap;
import com.mygame.model.maps.Level;
import com.mygame.model.maps.MapLoader;
import com.mygame.model.entities.GameEntity;
import com.mygame.utils.CompositeContactListener;
import com.mygame.utils.ContactListener.CollectibleListener;
import com.mygame.utils.ContactListener.ExitListener;
import com.mygame.utils.ContactListener.LoseListener;
import com.badlogic.gdx.physics.box2d.*;

/**
 * La classe {@code LevelLoader} est responsable du chargement et de la gestion des niveaux dans le jeu.
 * Elle s'occupe du chargement des cartes, de la gestion des entités, du rendu de la carte et des entités,
 * ainsi que de la gestion de la caméra.
 */
public class LevelLoader {
    private GameManager gameManager;  // Gestionnaire de jeu
    private MapLoader mapLoader;  // Chargeur de carte
    private GameMap gameMap;  // Carte du jeu
    private Level level;  // Niveau actuel
    private String mapPath;  // Chemin de la carte
    private SoundFactory soundFactory;
    private EntityFactory entityFactory;  // Factory d'entités
    private OrthographicCamera camera;  // Caméra pour la vue du jeu
    private SpriteBatch spriteBatch;  // SpriteBatch pour dessiner les entités
    private OrthogonalTiledMapRenderer mapRenderer;  // Rendu de la carte Tiled
    private CompositeContactListener compositeContactListener;  // Gestionnaire des contacts physiques
    private LoseListener loseListener;  // Écouteur des événements de perte
    private ExitListener exitListener;  // Écouteur des événements de sortie
    private CollectibleListener collectibleListener;  // Écouteur des événements de collecte

    /**
     * Constructeur de la classe {@code LevelLoader}.
     * Initialise les composants nécessaires pour charger et afficher le niveau du jeu.
     *
     * @param gameManager Le gestionnaire du jeu.
     * @param level Le niveau à charger.
     */
    public LevelLoader(GameManager gameManager, Level level) {
        mapLoader = new MapLoader();
        this.level = level;
        this.mapPath = level.getPath();

        // Charger la carte à partir du chemin
        gameMap = new GameMap(mapLoader.loadMap(mapPath), new World(new Vector2(0, -15f), true));

        // Initialiser le contrôleur de jeu et la factory d'entités
        this.gameManager = gameManager;
        this.entityFactory = gameManager.getEntityFactory();
        this.soundFactory = gameManager.getSoundFactory();

        // Charger les entités du niveau
        entityFactory.loadEntitiesFromMap(gameMap);
        gameManager.setupCommands(entityFactory.getPlayer());
        Gdx.input.setInputProcessor(new InputHandler(entityFactory.getPlayer()));
        loseListener = new LoseListener(gameManager.getGame(), this, gameManager);
        collectibleListener = new CollectibleListener(entityFactory,soundFactory);
        exitListener = new ExitListener(gameManager.getGame(), this, gameManager);

        this.compositeContactListener = new CompositeContactListener(loseListener, collectibleListener, exitListener);
        getMap().getWorld().setContactListener(compositeContactListener);

        // Initialiser la caméra et le SpriteBatch
        camera = new OrthographicCamera();
        spriteBatch = new SpriteBatch();

        // Configurer la caméra pour un ratio 16:9
        camera.setToOrtho(false, 16, 9);  // Ajustez si nécessaire pour votre jeu
        camera.update();

        // Initialiser le mapRenderer pour afficher la carte
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap.getMap(), 1 / 16f);
    }

    /**
     * Retourne la factory d'entités utilisée pour gérer les entités du jeu.
     *
     * @return La factory d'entités.
     */
    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    /**
     * Retourne la caméra utilisée pour afficher la vue du jeu.
     *
     * @return La caméra.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Retourne la carte du jeu.
     *
     * @return La carte.
     */
    public GameMap getMap() {
        return gameMap;
    }

    /**
     * Retourne le gestionnaire des contacts physiques.
     *
     * @return Le gestionnaire des contacts.
     */
    public CompositeContactListener getCompositeContactListener() {
        return compositeContactListener;
    }

    /**
     * Met à jour la position de la caméra pour suivre le joueur.
     * La caméra suit le joueur tout en restant à l'intérieur des limites de la carte.
     *
     * @param player L'entité représentant le joueur.
     */
    public void updateCamera(GameEntity player) {
        // Récupérer la position du joueur
        float playerX = player.getX();
        float playerY = player.getY();

        // Calculer la position de la caméra pour centrer sur le joueur
        float cameraX = playerX - camera.viewportWidth / 2;
        float cameraY = playerY - camera.viewportHeight / 2;

        // Limiter la position de la caméra aux bords de la carte
        if (cameraX < 0) {
            cameraX = 0;
        }
        if (cameraX > gameMap.getMapWidth() - camera.viewportWidth) {
            cameraX = gameMap.getMapWidth() - camera.viewportWidth;
        }

        if (cameraY < 0) {
            cameraY = 0;
        }
        if (cameraY > gameMap.getMapHeight() - camera.viewportHeight) {
            cameraY = gameMap.getMapHeight() - camera.viewportHeight;
        }

        // Mettre à jour la position de la caméra
        camera.position.set(cameraX + camera.viewportWidth / 2, cameraY + camera.viewportHeight / 2, 0);
        camera.update();  // Appliquer la mise à jour de la caméra
    }

    /**
     * Retourne le niveau actuel du jeu.
     *
     * @return Le niveau actuel.
     */
    public Level getLevel() {
        return this.level;
    }

    /**
     * Effectue le rendu du niveau et des entités à l'écran.
     */
    public void render() {
        // Utiliser la caméra pour la vue du niveau
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        exitListener.update();
        loseListener.update();

        // Appliquer la caméra au mapRenderer pour afficher la carte
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Dessiner les entités (par exemple, les ennemis, collectables, etc.)
        for (GameEntity entity : entityFactory.getEntities()) {
            entity.render(spriteBatch);
            entity.updatePhysics();
        }

        spriteBatch.end();
    }

    /**
     * Libère les ressources utilisées par le niveau et ses composants.
     */
    public void dispose() {
        gameMap.dispose();
        // Nettoyer les ressources
        spriteBatch.dispose();
        mapRenderer.dispose();
        mapLoader.dispose();
    }
}
