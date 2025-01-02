package com.mygame.model.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygame.model.entities.Enemy;

/**
 * Représente un ennemi patrouillant dans une zone définie par une largeur.
 * L'ennemi se déplace de manière va-et-vient entre deux points (un mouvement horizontal).
 * Il suit une trajectoire limitée par une largeur de patrouille spécifiée.
 */
public class PatrollingEnemy extends Enemy {

    private float speed = 2; // Vitesse de déplacement
    private boolean movingRight = true; // Direction actuelle de l'ennemi (vers la droite par défaut)
    private float patrolWidth; // Largeur de la zone de patrouille
    private float startX; // Position de départ en X

    /**
     * Constructeur de l'ennemi patrouillant.
     * Initialise la position de l'ennemi, la largeur de la zone de patrouille, ainsi que la vitesse du déplacement.
     *
     * @param x La position X de départ dans le monde du jeu.
     * @param y La position Y de départ dans le monde du jeu.
     * @param sprite Le sprite représentant visuellement l'ennemi.
     * @param world Le monde physique dans lequel l'ennemi est ajouté.
     * @param patrolWidth La largeur de la zone de patrouille dans laquelle l'ennemi se déplace.
     * @param speed La vitesse de déplacement de l'ennemi.
     */
    public PatrollingEnemy(float x, float y, Sprite sprite, World world, float patrolWidth, float speed) {
        super(x, y, sprite, world, BodyDef.BodyType.DynamicBody);
        this.speed = speed;
        this.startX = x; // Position de départ en X
        this.patrolWidth = patrolWidth; // Largeur de la zone de patrouille
    }

    /**
     * Met à jour la physique de l'ennemi, en contrôlant son mouvement de patrouille.
     * L'ennemi se déplace horizontalement entre les limites de la zone de patrouille.
     * Lorsqu'il atteint une limite, il inverse sa direction.
     */
    @Override
    public void updatePhysics() {
        if (getBody() == null) {
            return; // Ne rien faire si le corps est null
        }
        Vector2 position = getBody().getPosition();
        Vector2 velocity = getBody().getLinearVelocity();

        // Vérifier les limites de la zone de patrouille
        if (movingRight && position.x > startX + patrolWidth) {
            movingRight = false; // Si l'ennemi atteint la fin de la zone, il inverse sa direction
        } else if (!movingRight && position.x < startX) {
            movingRight = true; // Si l'ennemi atteint le début de la zone, il inverse sa direction
        }

        // Appliquer une vitesse constante selon la direction
        if (movingRight) {
            getBody().setLinearVelocity(speed, velocity.y); // Déplacement vers la droite
        } else {
            getBody().setLinearVelocity(-speed, velocity.y); // Déplacement vers la gauche
        }
    }

    /**
     * Mise à jour de l'ennemi pendant chaque frame.
     * Cette méthode appelle `updatePhysics()` pour contrôler le mouvement de patrouille.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour (en secondes).
     */
    @Override
    public void update(float deltaTime) {
        updatePhysics();
    }
}
