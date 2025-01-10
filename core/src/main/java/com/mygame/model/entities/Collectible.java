package com.mygame.model.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Représente un objet collectable dans le jeu, comme un "coin" ou une "life".
 * Les collectables sont des entités statiques avec un capteur pour détecter
 * la collecte par le joueur.
 */
public class Collectible extends GameEntity {
    private String subtype;

    /**
     * Constructeur pour créer un collectible avec un sous-type spécifique.
     *
     * @param x La position X du collectible dans le monde.
     * @param y La position Y du collectible dans le monde.
     * @param sprite Le sprite représentant le collectible.
     * @param subtype Le sous-type du collectible (par exemple "coin", "life").
     * @param world Le monde Box2D dans lequel le collectible est ajouté.
     */
    public Collectible(float x, float y, Sprite sprite, String subtype, World world) {
        super(x, y, sprite, world, BodyDef.BodyType.StaticBody);
        this.subtype = subtype;
        createCollectibleSensor();
    }

    /**
     * Crée un capteur pour la collecte du collectible. Ce capteur ne bloque pas la physique
     * mais détecte les collisions avec d'autres objets, comme le joueur.
     */
    public void createCollectibleSensor() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getSprite().getWidth() / 2, getSprite().getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        getBody().destroyFixture(getBody().getFixtureList().get(0)); // Retirer l'ancienne fixture si présente
        Fixture fixture = getBody().createFixture(fixtureDef); // Créer une nouvelle fixture sensorielle
        fixture.setUserData("collectible");
        shape.dispose(); // Libérer les ressources de la forme après la création
    }

    /**
     * Mise à jour du collectible. La logique spécifique pour chaque collectible
     * peut être ajoutée ici.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void update(float deltaTime) {
        // Logique spécifique aux collectibles si nécessaire.
    }

    /**
     * Mise à jour de la physique pour ce collectible. Puisque ce collectible est statique,
     * la physique n'a pas besoin d'être mise à jour.
     */
    @Override
    public void updatePhysics() {
        // Pas besoin de mettre à jour la physique pour les collectibles statiques
    }

    /**
     * Récupère le sous-type du collectible.
     *
     * @return Le sous-type du collectible.
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * Collecte le collectible, le marquant comme inactif et le supprimant du monde Box2D.
     */
    public void collect() {
        System.out.println("Collecté : " + subtype);
        this.setActive(false);
        removeFromWorld();
    }

    /**
     * Supprime le collectible du monde Box2D en détruisant son corps physique.
     */
    private void removeFromWorld() {
        if (this.isActive()) {
            Body body = getBody();
            body.getWorld().destroyBody(body);
        }
    }
}
