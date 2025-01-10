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

    public void createSensor() {
        // Définir une forme de capteur autour du joueur pour interagir avec les collectibles
        getBody().getFixtureList().get(0).setSensor(true);
        getBody().getFixtureList().get(0).setUserData("deadzone");


    }
}
