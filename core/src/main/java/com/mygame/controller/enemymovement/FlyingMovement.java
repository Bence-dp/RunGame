package com.mygame.controller.enemymovement;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Implémentation de la stratégie de mouvement pour un ennemi volant, qui suit un
 * chemin en forme de "8" horizontal. L'ennemi se déplace à la fois sur l'axe X et
 * l'axe Y de manière sinusoïdale pour créer un mouvement fluide et circulaire.
 */
public class FlyingMovement implements MovementStrategy {
    private float speed;
    private float patrolWidth;
    private float patrolHeight;
    private float startX;
    private float startY;
    private float time;
    private float frequencyX;
    private float frequencyY;

    /**
     * Constructeur pour initialiser le mouvement de l'ennemi volant.
     *
     * @param startX La position initiale en X de l'ennemi.
     * @param startY La position initiale en Y de l'ennemi.
     * @param patrolWidth La largeur de la zone de patrouille.
     * @param patrolHeight La hauteur de la zone de patrouille.
     * @param speed La vitesse du mouvement.
     */
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

    /**
     * Met à jour la position de l'ennemi en fonction de son mouvement sinusoïdal dans
     * la zone de patrouille.
     *
     * @param body L'objet Body représentant l'ennemi.
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void updateMovement(Body body, float deltaTime) {
        if (body == null) return;

        time += deltaTime;

        Vector2 position = body.getPosition();

        float offsetX = (float) Math.sin(time * frequencyX) * patrolWidth / 2;
        float offsetY = (float) Math.cos(time * frequencyY) * patrolHeight / 2;

        float newX = startX + patrolWidth / 2 + offsetX;
        float newY = startY + offsetY + patrolHeight / 2;

        newX = Math.max(startX, Math.min(newX, startX + patrolWidth));
        newY = Math.max(startY - patrolHeight, Math.min(newY, startY + patrolHeight));

        body.setTransform(newX, newY, body.getAngle());
    }
}
