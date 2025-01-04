package com.mygame.model.entities.enemies.movement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class FlyingMovement implements MovementStrategy {
    private float speed;
    private float patrolWidth;
    private float patrolHeight;
    private float startX;
    private float startY;
    private float time;
    private float frequencyX;
    private float frequencyY;

    public FlyingMovement(float startX, float startY, float patrolWidth, float patrolHeight, float speed) {
        this.speed = speed;
        this.startX = startX;
        this.startY = startY;
        this.patrolWidth = patrolWidth;
        this.patrolHeight = patrolHeight;
        this.time = 0;
        this.frequencyX = speed;
        this.frequencyY = speed;
    }

    @Override
    public void updateMovement(Body body, float deltaTime) {
        if (body == null) return;

        time += deltaTime;

        Vector2 position = body.getPosition();

        // Calculer un mouvement en forme de 8 horizontal
        // L'idée est de faire osciller X avec sinus et Y avec cosinus
        // Cela va créer une trajectoire en forme de 8 tourné horizontalement.
        float offsetX = (float) Math.sin(time * frequencyX) * patrolWidth / 2; // Mouvement sinusoïdal sur X
        float offsetY = (float) Math.cos(time * frequencyY) * patrolHeight / 2; // Mouvement cosinus modéré sur Y

        // Calculer la nouvelle position avec des décalages sur X et Y
        float newX = startX + patrolWidth / 2 + offsetX; // Déplacer la zone de patrouille vers la droite
        float newY = startY + offsetY + patrolHeight / 2;

        // Assurer que l'ennemi reste dans les limites de la zone de patrouille
        newX = Math.max(startX, Math.min(newX, startX + patrolWidth)); // Limiter à la largeur (déplacée à droite)
        newY = Math.max(startY - patrolHeight, Math.min(newY, startY + patrolHeight));

        body.setTransform(newX, newY, body.getAngle());
    }
}
