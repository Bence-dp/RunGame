package com.mygame.model.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Enemy extends GameEntity {
    float speed;
    public Enemy(float x, float y, Sprite sprite, World world,BodyDef.BodyType type) {
        super(x, y, sprite,world, type);
        createSensor();
    }

    @Override
    public void update(float deltaTime) {
        // Logique spécifique aux ennemis.
    }

    @Override
    public void updatePhysics() {

    }

    private void createSensor() {
        // Définir une forme de capteur autour du joueur pour interagir avec les collectibles
        CircleShape shape = new CircleShape();
        shape.setRadius(getSprite().getHeight()/1.4f); // Taille du capteur (ajustez si nécessaire)
        shape.setPosition(new Vector2(0, getSprite().getHeight()/2)); // Vector2(0, 0) centre le capteur

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;  // Le capteur ne bloque pas la physique

        // Crée la fixture sensorielle autour du joueur

        Fixture fixture = getBody().createFixture(fixtureDef);
        fixture.setUserData("deadzone");


        shape.dispose(); // Libère la mémoire utilisée par la forme du capteur
    }
}
