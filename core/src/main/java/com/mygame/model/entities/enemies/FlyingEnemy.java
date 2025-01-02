package com.mygame.model.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygame.model.entities.Enemy;

/**
 * Représente un ennemi volant dans le jeu qui se déplace selon un motif en forme de 8.
 * L'ennemi suit une trajectoire oscillante dans une zone de patrouille définie par une largeur et une hauteur.
 * Il est basé sur un corps cinématique dans le moteur physique Box2D.
 */
public class FlyingEnemy extends Enemy {

    private float speed = 2; // Vitesse de déplacement
    private float patrolWidth; // Largeur de la zone de patrouille
    private float patrolHeight; // Hauteur de la zone de patrouille
    private float startX; // Position de départ en X
    private float startY; // Position de départ en Y
    private float time; // Variable de temps pour animer le mouvement en forme de 8
    private float frequencyX; // Fréquence du mouvement sur l'axe X
    private float frequencyY; // Fréquence du mouvement sur l'axe Y

    /**
     * Constructeur de l'ennemi volant.
     * Initialise les paramètres du FlyingEnemy avec la position de départ, la largeur et la hauteur de la zone de patrouille,
     * la vitesse de l'ennemi, ainsi que la fréquence de son mouvement.
     *
     * @param x La position X de départ dans le monde du jeu.
     * @param y La position Y de départ dans le monde du jeu.
     * @param sprite Le sprite représentant visuellement l'ennemi.
     * @param world Le monde physique dans lequel l'ennemi est ajouté.
     * @param patrolWidth La largeur de la zone dans laquelle l'ennemi patrouille.
     * @param patrolHeight La hauteur de la zone dans laquelle l'ennemi patrouille.
     * @param speed La vitesse à laquelle l'ennemi se déplace.
     */
    public FlyingEnemy(float x, float y, Sprite sprite, World world, float patrolWidth, float patrolHeight, float speed) {
        super(x, y, sprite, world, BodyDef.BodyType.KinematicBody);
        this.speed = speed;
        this.startX = getSprite().getX(); // Position de départ X
        this.startY = getSprite().getY(); // Position de départ Y
        this.patrolWidth = patrolWidth; // Largeur de la zone de patrouille
        this.patrolHeight = patrolHeight; // Hauteur de la zone de patrouille
        this.time = 0; // Initialiser le temps à 0
        this.frequencyX = speed; // Fréquence du mouvement en X
        this.frequencyY = speed; // Fréquence du mouvement en Y

        // Créer un corps cinématique (pas affecté par la gravité)
    }

    /**
     * Met à jour la physique de l'ennemi, calculant sa nouvelle position en fonction de son mouvement sinusoïdal.
     * L'ennemi suit un motif de mouvement en forme de 8 sur les axes X et Y.
     * La position de l'ennemi est ajustée en fonction de la largeur et de la hauteur de sa zone de patrouille.
     */
    @Override
    public void updatePhysics() {
        if (getBody() == null) {
            return; // Ne rien faire si le corps est null
        }

        Vector2 position = getBody().getPosition();

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
        newY = Math.max(startY - patrolHeight, Math.min(newY, startY + patrolHeight)); // Limiter à la hauteur

        // Mettre à jour la position du corps du FlyingEnemy
        getBody().setTransform(newX, newY, getBody().getAngle());

        // Incrémenter le temps pour faire osciller l'ennemi dans la forme de 8
        time += 1 / 60f; // Supposons que le jeu tourne à 60 FPS
    }

    /**
     * Mise à jour de l'ennemi pendant chaque frame.
     * Cette méthode appelle la mise à jour de la physique.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour (en secondes).
     */
    @Override
    public void update(float deltaTime) {
        updatePhysics();
    }
}
