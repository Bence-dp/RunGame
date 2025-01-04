package com.mygame.model.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygame.model.entities.Enemy;
import com.mygame.controller.enemymovement.MovementStrategy;

/**
 * Représente un ennemi patrouillant qui utilise une stratégie de mouvement horizontal.
 */
public class PatrollingEnemy extends Enemy {

    private final MovementStrategy movementStrategy;

    /**
     * Constructeur de l'ennemi patrouillant.
     * Initialise les paramètres et la stratégie de mouvement de l'ennemi.
     *
     * @param x La position X de départ dans le monde du jeu.
     * @param y La position Y de départ dans le monde du jeu.
     * @param sprite Le sprite représentant visuellement l'ennemi.
     * @param world Le monde physique dans lequel l'ennemi est ajouté.
     * @param movementStrategy La stratégie de mouvement utilisée par l'ennemi.
     */
    public PatrollingEnemy(float x, float y, Sprite sprite, World world, MovementStrategy movementStrategy) {
        super(x, y, sprite, world, BodyDef.BodyType.DynamicBody);
        this.movementStrategy = movementStrategy;
    }

    /**
     * Met à jour la physique de l'ennemi.
     */
    @Override
    public void updatePhysics() {
        if (getBody() == null || movementStrategy == null) {
            return;
        }

        Vector2 currentPosition = getBody().getPosition();
        movementStrategy.updateMovement(getBody(), Gdx.graphics.getDeltaTime());

    }

    /**
     * Mise à jour de l'ennemi pendant chaque frame.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour (en secondes).
     */
    @Override
    public void update(float deltaTime) {
        updatePhysics();
    }
}
