package com.mygame.model.entities.enemies.movement;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;

public class PatrollingMovement implements MovementStrategy {
    private float speed;
    private float patrolWidth;
    private float startX;
    private boolean movingRight;

    public PatrollingMovement(float startX, float patrolWidth, float speed) {
        this.speed = speed;
        this.startX = startX;
        this.patrolWidth = patrolWidth;
        this.movingRight = true;
    }

    @Override
    public void updateMovement(Body body, float deltaTime) {
        if (body == null) return;

        Vector2 position = body.getPosition();
        Vector2 velocity = body.getLinearVelocity();

        if (movingRight && position.x > startX + patrolWidth) {
            movingRight = false;
        } else if (!movingRight && position.x < startX) {
            movingRight = true;
        }

        float newVelocityX = movingRight ? speed : -speed;
        body.setLinearVelocity(newVelocityX, velocity.y);
    }
}
