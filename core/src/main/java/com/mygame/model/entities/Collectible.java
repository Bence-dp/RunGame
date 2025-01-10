// Collectible.java
package com.mygame.model.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;

public class Collectible extends GameEntity {
    private String subtype; // Ex. "coin", "life"

    public Collectible(float x, float y, Sprite sprite, String subtype, World world) {
        super(x, y, sprite, world, BodyDef.BodyType.StaticBody); // Rendre ce collectible statique
        this.subtype = subtype;
        createCollectibleSensor();
    }

    // Crée le capteur pour la collecte
    public void createCollectibleSensor() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getSprite().getWidth()/2, getSprite().getHeight()/2); // Ajustez la taille en fonction de votre collectible

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;  // C'est un capteur, il ne bloque pas la physique

        // Création de la fixture (capteur)
        getBody().destroyFixture(getBody().getFixtureList().get(0)); // Retirer l'ancienne fixture si présente
        Fixture fixture = getBody().createFixture(fixtureDef); // Créer une nouvelle fixture sensorielle
        fixture.setUserData("collectible");
        shape.dispose(); // Libérer les ressources de la forme après la création
    }

    @Override
    public void update(float deltaTime) {
        // Logique spécifique aux collectibles (si nécessaire).
    }

    @Override
    public void updatePhysics() {
        // Pas besoin de mettre à jour la physique pour les collectibles statiques
    }

    public String getSubtype() {
        return subtype;
    }

    // Méthode pour collecter l'objet
    public void collect() {
        System.out.println("Collecté : " + subtype);
        this.setActive(false);  // Marquer comme inactif après la collecte
        removeFromWorld(); // Supprimer du monde Box2D
    }

    // Méthode pour supprimer le collectible du monde Box2D
    private void removeFromWorld() {
        if (this.isActive()) {
            Body body = getBody();
            body.getWorld().destroyBody(body); // Détruire le corps physique
        }
    }

}
