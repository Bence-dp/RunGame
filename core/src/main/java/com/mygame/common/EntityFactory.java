package com.mygame.common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygame.controller.GameManager;
import com.mygame.model.entities.*;
import com.mygame.model.entities.collectibles.Coin;
import com.mygame.model.entities.enemies.FlyingEnemy;
import com.mygame.model.entities.enemies.PatrollingEnemy;
import com.mygame.controller.enemymovement.FlyingMovement;
import com.mygame.controller.enemymovement.PatrollingMovement;
import com.mygame.model.maps.GameMap;

/**
 * Factory responsable de la création et de la gestion des entités du jeu.
 * Implémente un modèle Singleton pour garantir une instance unique.
 */
public class EntityFactory {

    private static EntityFactory instance;

    private final ObjectMap<String, Texture> textureCache = new ObjectMap<>();
    private final Array<GameEntity> entities = new Array<>();
    private final GameManager gameManager;

    /**
     * Constructeur privé pour empêcher l'instanciation extérieure.
     *
     * @param gameManager Le gestionnaire de jeu associé à cette factory.
     */
    private EntityFactory(GameManager gameManager) {
        this.gameManager = gameManager;
        textureCache.put("player", new Texture("Entities/player.png"));
        textureCache.put("zombie", new Texture("Entities/enemy.png"));
        textureCache.put("bird", new Texture("Entities/flyingenemy.png"));
        textureCache.put("flying_enemy", new Texture("Entities/enemy.png"));
        textureCache.put("coin", new Texture("Entities/coin.png"));
    }

    /**
     * Récupère l'instance unique de la factory.
     *
     * @param gameManager Le gestionnaire de jeu à associer à la factory.
     * @return L'instance unique de EntityFactory.
     */
    public static EntityFactory getInstance(GameManager gameManager) {
        if (instance == null) {
            instance = new EntityFactory(gameManager);
        }
        return instance;
    }

    /**
     * Charge les entités à partir de la carte fournie.
     *
     * @param map La carte du jeu contenant les définitions des entités.
     * @return Une liste des entités créées.
     */
    public Array<GameEntity> loadEntitiesFromMap(GameMap map) {
        clearEntities();

        if (map.getEntityLayer() == null) {
            System.err.println("Aucune couche 'Spawners' trouvée dans la carte.");
            return entities;
        }

        for (MapObject object : map.getEntityLayer().getObjects()) {
            if (!(object instanceof RectangleMapObject)) {
                continue;
            }

            RectangleMapObject rectObject = (RectangleMapObject) object;
            MapProperties properties = object.getProperties();

            String type = (String) properties.get("type");
            if (type == null) {
                System.err.println("Objet sans type trouvé : " + object.getName());
                continue;
            }

            float x = rectObject.getRectangle().x;
            float y = rectObject.getRectangle().y;

            GameEntity entity = createEntity(type, x, y, map, properties);
            if (entity != null) {
                entities.add(entity);
            }
        }

        return entities;
    }

    /**
     * Crée une entité de jeu en fonction de son type et de ses propriétés.
     *
     * @param type       Le type de l'entité (ex. : joueur, ennemi, collectible).
     * @param x          La position x de l'entité.
     * @param y          La position y de l'entité.
     * @param gamemap    La carte du jeu où l'entité se trouve.
     * @param properties Les propriétés supplémentaires de l'entité.
     * @return L'entité créée ou null si le type est inconnu.
     */
    private GameEntity createEntity(String type, float x, float y, GameMap gamemap, MapProperties properties) {
        GameEntity entity = null;
        Sprite sprite;
        TiledMap map = gamemap.getMap();

        float tileWidth = map.getProperties().get("tilewidth", Integer.class);
        float tileHeight = map.getProperties().get("tileheight", Integer.class);

        float unitX = x / tileWidth;
        float unitY = y / tileHeight;

        switch (type) {
            case "player":
                    sprite = new Sprite(textureCache.get("player"));
                    sprite.setSize(0.8f, 0.8f);
                    entity = new Player(unitX, unitY, sprite, gamemap.getWorld());
                    entity.setScore(gameManager.getCoin());

                break;

            case "enemy":
                // Vérification des propriétés nécessaires pour un ennemi
                if (properties.containsKey("width") && properties.containsKey("speed") && properties.containsKey("name")) {
                    float patrolWidth = properties.get("width", Float.class) / tileWidth;
                    float speed = properties.get("speed", Float.class);
                    String texture = properties.get("name", String.class);
                    String enemyType = properties.get("subtype", String.class); // "subtype" est optionnel

                    sprite = new Sprite(textureCache.get(texture));
                    sprite.setSize(1, 1);

                    if ("walk".equals(enemyType)) {
                        entity = new PatrollingEnemy(unitX, unitY, sprite, gamemap.getWorld(), new PatrollingMovement(unitX, patrolWidth, speed));
                    } else if ("fly".equals(enemyType)) {
                        if (properties.containsKey("height")) {
                            float patrolHeight = properties.get("height", Float.class) / tileHeight;
                            entity = new FlyingEnemy(unitX, unitY, sprite, gamemap.getWorld(), new FlyingMovement(unitX, unitY, patrolWidth, patrolHeight, speed));
                        } else {
                            System.err.println("Propriété 'height' manquante pour l'entité flying enemy.");
                        }
                    } else {
                        entity = new PatrollingEnemy(unitX, unitY, sprite, gamemap.getWorld(), new PatrollingMovement(unitX, patrolWidth, speed));
                    }
                } else {
                    System.err.println("Propriétés manquantes pour l'entité enemy : 'width', 'speed' ou 'name'.");
                }
                break;

            case "collectible":
                // Vérification de la propriété "subtype"
                if (properties.containsKey("subtype")) {
                    String subtype = (String) properties.get("subtype");
                    sprite = new Sprite(textureCache.get(subtype));
                    sprite.setSize(0.5f, 0.5f);

                    if ("coin".equals(subtype)) {
                        entity = new Coin(unitX, unitY, sprite, gamemap.getWorld());
                    } else {
                        System.err.println("Type de collectible inconnu : " + subtype);
                    }
                } else {
                    System.err.println("Propriété 'subtype' manquante pour l'entité collectible.");
                }
                break;

            default:
                System.err.println("Type d'entité inconnu : " + type);
                break;
        }

        return entity;
    }


    /**
     * Récupère le joueur depuis la liste des entités.
     *
     * @return Une instance de Player si trouvée, sinon null.
     */
    public Player getPlayer() {
        for (GameEntity entity : entities) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }

    /**
     * Récupère toutes les entités chargées.
     *
     * @return Une liste des entités.
     */
    public Array<GameEntity> getEntities() {
        return this.entities;
    }

    /**
     * Vide la liste des entités et libère leurs ressources.
     */
    private void clearEntities() {
        for (GameEntity entity : entities) {
            entity.dispose();
        }
        entities.clear();
    }

    /**
     * Libère les ressources (textures) utilisées par la factory.
     */
    public void dispose() {
        for (Texture texture : textureCache.values()) {
            texture.dispose();
        }
        textureCache.clear();
    }
}
