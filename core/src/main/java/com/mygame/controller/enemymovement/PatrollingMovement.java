package com.mygame.controller.enemymovement;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;

/**
 * La classe {@code PatrollingMovement} représente une stratégie de mouvement pour un ennemi qui effectue
 * une patrouille d'un côté à l'autre sur une certaine largeur. L'ennemi se déplace de manière linéaire
 * sur un axe horizontal, allant d'un point de départ à un point d'arrêt, puis inversant la direction.
 *
 * <p>Cette stratégie de mouvement est utilisée pour simuler un ennemi qui garde une zone définie,
 * se déplaçant d'un côté à l'autre dans un comportement de patrouille.</p>
 */
public class PatrollingMovement implements MovementStrategy {

    private float speed;  // Vitesse de déplacement de l'ennemi
    private float patrolWidth;  // Largeur de la zone de patrouille (distance parcourue de part et d'autre du point de départ)
    private float startX;  // Position de départ en X de l'ennemi
    private boolean movingRight;  // Indicateur de direction : true si l'ennemi va vers la droite, false vers la gauche

    /**
     * Constructeur de la classe {@code PatrollingMovement}.
     *
     * @param startX       La position X de départ de l'ennemi, où il commence à patrouiller.
     * @param patrolWidth  La largeur de la zone de patrouille (la distance maximale parcourue dans chaque direction).
     * @param speed        La vitesse à laquelle l'ennemi se déplace pendant la patrouille.
     */
    public PatrollingMovement(float startX, float patrolWidth, float speed) {
        this.speed = speed;
        this.startX = startX;
        this.patrolWidth = patrolWidth;
        this.movingRight = true;
    }

    /**
     * Met à jour la position et la vitesse du corps de l'ennemi pour le déplacer selon la stratégie de patrouille.
     * L'ennemi se déplace à gauche et à droite dans la zone de patrouille définie, et inverse sa direction
     * lorsqu'il atteint l'une des bornes de la zone.
     *
     * @param body       Le corps physique de l'ennemi, utilisé pour mettre à jour sa position.
     * @param deltaTime  Le temps écoulé entre les mises à jour (utile pour les calculs de physique et de mouvement).
     */
    @Override
    public void updateMovement(Body body, float deltaTime) {
        if (body == null) return;

        // Récupérer la position actuelle du corps
        Vector2 position = body.getPosition();
        Vector2 velocity = body.getLinearVelocity();

        // Vérifier si l'ennemi a atteint l'une des limites de la patrouille
        if (movingRight && position.x > startX + patrolWidth) {
            movingRight = false;  // Inverser la direction si l'ennemi dépasse la limite de la patrouille vers la droite
        } else if (!movingRight && position.x < startX) {
            movingRight = true;  // Inverser la direction si l'ennemi dépasse la limite de la patrouille vers la gauche
        }

        // Définir la nouvelle vitesse horizontale en fonction de la direction
        float newVelocityX = movingRight ? speed : -speed;

        // Mettre à jour la vitesse du corps
        body.setLinearVelocity(newVelocityX, velocity.y);
    }
}
