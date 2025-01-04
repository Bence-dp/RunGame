package com.mygame.model.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.mygame.model.maps.obstacles.*;

/**
 * La classe {@code GameMap} permet de gérer la carte du jeu,
 * y compris la gestion des obstacles et la simulation physique via Box2D.
 * Elle utilise une carte Tiled pour charger les éléments de la carte et
 * créer les corps physiques correspondants dans le monde Box2D.
 * <p>
 * Cette classe prend en charge la création d'obstacles solides et rebondissants, ainsi que
 * la création de zones de mort et de téléporteurs à partir des données de la carte.
 */
public class GameMap {

    private final TiledMap map;
    private TiledMapTileLayer obstacleLayer;
    private MapLayer entityLayer;
    private World world; // Le monde physique Box2D

    // Facteur Pixels par Mètre
    private float PPM = 16f;  // Pixels par mètre, ajustez en fonction de vos tuiles

    /**
     * Constructeur de la classe {@code GameMap}.
     *
     * @param map La carte Tiled qui contient les éléments du niveau.
     * @param world Le monde physique Box2D dans lequel la carte et les objets seront créés.
     */
    public GameMap(TiledMap map, World world) {
        this.map = map;
        this.entityLayer = (MapLayer) map.getLayers().get("entity");
        this.obstacleLayer = (TiledMapTileLayer) map.getLayers().get("obstacles");
        this.world = world;
        createBox2DObstacles();
        createExit();
    }

    /**
     * Crée les obstacles physiques dans le monde Box2D à partir des informations de la couche des obstacles de la carte.
     * Les obstacles sont définis par des tuiles et peuvent être solides ou rebondissants.
     * Une zone de mort est également créée pour le bas de la carte.
     */
    public void createBox2DObstacles() {
        float tileWidth = obstacleLayer.getTileWidth();
        float tileHeight = obstacleLayer.getTileHeight();

        for (int x = 0; x < obstacleLayer.getWidth(); x++) {
            for (int y = 0; y < obstacleLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = obstacleLayer.getCell(x, y);
                if (cell != null) {
                    Obstacle obstacle;
                    if (cell.getTile().getId() != 41) {
                        obstacle = new SolidObstacle(world, PPM); // Crée un obstacle solide
                    } else {
                        obstacle = new BounceObstacle(world, PPM); // Crée un obstacle rebondissant
                    }
                    obstacle.createBody(x * tileWidth / PPM, y * tileHeight / PPM, tileWidth / PPM, tileHeight / PPM);
                }
            }
        }
        createBorderObstacles();

        // Crée la zone de mort en bas de la carte
        DeadZone deadZone = new DeadZone(world, PPM);
        deadZone.createBody(0, 0, getMapWidth(), 1f);
    }
    private void createBorderObstacles() {
        float tileWidth = obstacleLayer.getTileWidth();
        float tileHeight = obstacleLayer.getTileHeight();
        Obstacle obstacle;
        // Obstacle du haut de la carte
        for (int x = 0; x < getMapWidth(); x++) {
            obstacle= new SolidObstacle(world, PPM); // Bord supérieur
            obstacle.createBody(x * tileWidth / PPM, getMapHeight()-1 * tileHeight / PPM, tileWidth / PPM, tileHeight / PPM);

        }

        // Obstacle du bas de la carte
        for (int x = 0; x < getMapWidth(); x++) {
            obstacle = new SolidObstacle(world, PPM); // Bord inférieur
            obstacle.createBody(x * tileWidth / PPM, -1 * tileHeight / PPM, tileWidth / PPM, tileHeight / PPM);

        }

        // Obstacle à gauche de la carte
        for (int y = 0; y < getMapHeight(); y++) {
            obstacle = new SolidObstacle(world, PPM); // Bord inférieur
            obstacle.createBody(-1*tileWidth / PPM, y * tileHeight / PPM, tileWidth / PPM, tileHeight / PPM);
        }

        // Obstacle à droite de la carte
        for (int y = 0; y < getMapHeight(); y++) {
            obstacle = new SolidObstacle(world, PPM);
            obstacle.createBody(getMapWidth()  * tileWidth / PPM, y * tileHeight / PPM, tileWidth / PPM, tileHeight / PPM);
        }
    }

    /**
     * Crée les téléporteurs dans le monde physique à partir de la couche 'teleporter' de la carte.
     * Chaque téléporteur est défini comme un capteur dans le monde Box2D.
     */
    public void createExit() {
        MapLayer teleporterLayer = map.getLayers().get("teleporter");
        if (teleporterLayer == null) {
            System.err.println("La couche 'teleporter' n'existe pas dans la carte.");
            return;
        }

        // Parcourt chaque objet de la couche 'teleporter' et crée un téléporteur
        for (MapObject object : teleporterLayer.getObjects()) {
            if (!(object instanceof RectangleMapObject)) {
                System.err.println("Un objet non rectangulaire a été ignoré dans la couche 'teleporter'.");
                continue;
            }

            RectangleMapObject rectObject = (RectangleMapObject) object;
            float x = rectObject.getRectangle().x / PPM;
            float y = rectObject.getRectangle().y / PPM;
            float width = rectObject.getRectangle().width / PPM;
            float height = rectObject.getRectangle().height / PPM;

            Teleporter teleporter = new Teleporter(world, PPM);
            teleporter.createBody(x, y, width, height);
        }

        System.out.println("Les capteurs 'exit' ont été créés avec succès.");
    }

    /**
     * Obtient la largeur de la carte en termes de nombre de tuiles.
     *
     * @return La largeur de la carte en tuiles.
     */
    public int getMapWidth() {
        return obstacleLayer.getWidth();
    }

    /**
     * Obtient la hauteur de la carte en termes de nombre de tuiles.
     *
     * @return La hauteur de la carte en tuiles.
     */
    public int getMapHeight() {
        return obstacleLayer.getHeight();
    }

    /**
     * Obtient la couche des obstacles de la carte.
     *
     * @return La couche des obstacles.
     */
    public TiledMapTileLayer getObstacleLayer() {
        return this.obstacleLayer;
    }

    /**
     * Obtient la couche des entités de la carte.
     *
     * @return La couche des entités.
     */
    public MapLayer getEntityLayer() {
        return this.entityLayer;
    }

    /**
     * Obtient la carte Tiled.
     *
     * @return La carte Tiled utilisée par le jeu.
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Définit la gravité du monde physique Box2D.
     *
     * @param newGravity Le nouveau vecteur de gravité à appliquer au monde.
     */
    public void setGravity(Vector2 newGravity) {
        world.setGravity(newGravity);
    }

    /**
     * Libère les ressources utilisées par la carte et le monde physique.
     */
    public void dispose() {
        if (map != null) {
            map.dispose();
        }
        if (world != null) {
            world.dispose();
        }
    }

    /**
     * Obtient le monde physique Box2D.
     *
     * @return Le monde physique Box2D associé à la carte.
     */
    public World getWorld() {
        return world;
    }
}
