package com.mygame.model.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.mygame.utils.Coord;

/**
 * Représente une entité dans le jeu, incluant à la fois un sprite et un corps physique Box2D.
 * Les entités peuvent être statiques ou dynamiques, et sont mises à jour à chaque frame.
 * Cette classe est abstraite et doit être étendue pour définir des comportements spécifiques.
 */
public abstract class GameEntity {

    private Coord coord; // Coordonnées de l'entité
    private boolean active = true; // Statut de l'entité (active ou inactive)

    private Sprite sprite; // Sprite de l'entité
    private Rectangle bounds; // Limites de l'entité (utilisées pour la détection de collisions)

    // Propriétés Box2D
    private Body body; // Le corps Box2D associé à l'entité

    // Nouveau paramètre pour définir si l'entité est statique ou dynamique
    private BodyDef.BodyType bodyType; // Type de corps physique de l'entité

    /**
     * Constructeur de l'entité de jeu.
     * Ce constructeur initialise les coordonnées, le sprite, et le type de corps physique (statique ou dynamique).
     * Un corps Box2D est également créé et attaché à l'entité.
     *
     * @param x La position X de l'entité.
     * @param y La position Y de l'entité.
     * @param sprite Le sprite représentant l'entité.
     * @param world Le monde Box2D dans lequel l'entité sera ajoutée.
     * @param bodyType Le type de corps physique (statique ou dynamique).
     */
    public GameEntity(float x, float y, Sprite sprite, World world, BodyDef.BodyType bodyType) {
        this.coord = new Coord(x, y);
        this.sprite = sprite;
        this.bodyType = bodyType; // Initialiser le type de corps

        // Configurer le sprite
        this.sprite.setPosition(x, y);
        this.sprite.setOrigin(0, 0); // Origine en bas à gauche (à ajuster si nécessaire)

        // Définir les limites basées sur la taille du sprite
        this.bounds = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());

        // Créer le corps Box2D (statique ou dynamique selon le paramètre)
        createBox2DBody(world);
    }

    /**
     * Crée un corps Box2D pour l'entité. Le corps peut être statique ou dynamique.
     *
     * @param world Le monde Box2D dans lequel le corps sera créé.
     */
    private void createBox2DBody(World world) {
        // Définition du BodyDef
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(coord.getX() + sprite.getWidth() / 2, coord.getY() + sprite.getHeight() / 2); // Centrer le corps Box2D sur le sprite
        bodyDef.type = bodyType; // Utiliser le type défini dans le constructeur (statique ou dynamique)

        // Créer le corps physique
        body = world.createBody(bodyDef);

        // Créer une forme (ici un rectangle basé sur la taille du sprite)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2); // Ajuster la taille pour qu'elle soit centrée sur l'origine de la Box2D

        // Définir la fixture physique
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f; // Densité par défaut
        fixtureDef.friction = 0.0f; // Friction
        fixtureDef.restitution = 0.00f; // Élasticité

        // Créer la fixture pour le corps
        body.createFixture(fixtureDef);
        shape.dispose(); // Libérer la forme après l'ajout
    }

    /**
     * Mise à jour de l'entité, appelée à chaque frame.
     * Cette méthode synchronise la position du sprite avec celle du corps Box2D.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour (en secondes).
     */
    public void update(float deltaTime) {
        // Synchroniser la position du sprite avec celle du corps Box2D
        Vector2 position = body.getPosition();
        coord.setX(position.x);
        coord.setY(position.y);
        bounds.setPosition(position.x, position.y); // Mettre à jour les limites également

        // Positionner le sprite pour qu'il soit centré sur le corps Box2D
        sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2); // Centrer le sprite sur le corps Box2D
    }

    /**
     * Rendu de l'entité (affichage du sprite à l'écran).
     *
     * @param batch Le SpriteBatch utilisé pour dessiner l'entité.
     */
    public void render(SpriteBatch batch) {
        // Positionner le sprite pour qu'il soit centré sur le corps Box2D
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.draw(batch); // Utiliser directement le sprite pour l'affichage
    }

    // Getters et Setters pour coord, sprite, etc.

    /**
     * Récupère la position X de l'entité.
     *
     * @return La position X de l'entité.
     */
    public float getX() {
        if (body != null) {
            return body.getPosition().x;
        }
        return 0;
    }

    /**
     * Récupère les coordonnées de l'entité.
     *
     * @return Les coordonnées de l'entité.
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * Définit la position X de l'entité.
     *
     * @param x La nouvelle position X de l'entité.
     */
    public void setX(float x) {
        this.coord.setX(x);
        bounds.setX(x);
        sprite.setX(x); // Synchroniser avec le sprite
        body.setTransform(x, body.getPosition().y, body.getAngle()); // Synchroniser avec le corps Box2D
    }

    /**
     * Récupère la position Y de l'entité.
     *
     * @return La position Y de l'entité.
     */
    public float getY() {
        if (body != null) {
            return body.getPosition().y;
        }
        return 0;
    }

    /**
     * Définit la position Y de l'entité.
     *
     * @param y La nouvelle position Y de l'entité.
     */
    public void setY(float y) {
        this.coord.setY(y);
        bounds.setY(y);
        sprite.setY(y); // Synchroniser avec le sprite
        body.setTransform(body.getPosition().x, y, body.getAngle()); // Synchroniser avec le corps Box2D
    }

    /**
     * Récupère le sprite de l'entité.
     *
     * @return Le sprite de l'entité.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Définit le sprite de l'entité.
     *
     * @param sprite Le nouveau sprite de l'entité.
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.sprite.setPosition(coord.getX(), coord.getY()); // Synchronisation
    }

    /**
     * Vérifie si l'entité est active.
     *
     * @return True si l'entité est active, sinon false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Définit l'état actif de l'entité.
     *
     * @param active True pour activer l'entité, sinon false pour la désactiver.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Définit le monde Box2D de l'entité (optionnel).
     *
     * @param world Le nouveau monde Box2D.
     */
    public void setWorld(World world) {
        // Optionnel si vous voulez changer le monde de Box2D plus tard
    }

    /**
     * Récupère les limites de l'entité.
     *
     * @return Les limites de l'entité sous forme de Rectangle.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Définit le corps Box2D de l'entité.
     *
     * @param body Le nouveau corps Box2D de l'entité.
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Récupère le corps Box2D de l'entité.
     *
     * @return Le corps Box2D de l'entité.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Méthode appelée lorsqu'une entité est collectée (par exemple, une pièce).
     * Elle peut être implémentée dans les sous-classes pour des actions spécifiques.
     */
    public void collectPiece() {
        // Implémentation spécifique dans les sous-classes
    }

    /**
     * Définit le score associé à l'entité.
     * Cette méthode peut être utilisée pour les entités ayant un score (comme les ennemis ou objets à collecter).
     *
     * @param score Le score à définir.
     */
    public void setScore(int score) {
        // Implémentation spécifique dans les sous-classes
    }

    /**
     * Libère les ressources associées à l'entité (par exemple, destruction du corps Box2D).
     */
    public void dispose() {
        if (body != null && body.getWorld() != null) {
            body.getWorld().destroyBody(body);
            for (Fixture fixture : body.getFixtureList()) {
                body.destroyFixture(fixture);
            }
            body = null;
        }
        sprite.setTexture(null); // Libérer la texture du sprite
    }
    public void updateSprite() {}

    /**
     * Méthode abstraite pour mettre à jour la physique de l'entité.
     * Cette méthode doit être implémentée dans les classes concrètes.
     */
    public abstract void updatePhysics();


}
