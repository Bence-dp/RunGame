package com.mygame.model.maps.obstacles;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Classe abstraite {@code Obstacle} pour représenter un obstacle générique dans une carte de jeu.
 *
 * <p>Cette classe fournit une base pour les différents types d'obstacles, qu'ils soient
 * statiques ou interactifs. Les sous-classes doivent implémenter la méthode {@link #createBody}
 * pour définir les propriétés spécifiques de chaque type d'obstacle.</p>
 *
 * <p>Chaque obstacle est associé à un corps physique {@link Body} dans le monde physique
 * {@link World} de Box2D. Le facteur {@code PPM} (Pixels par Mètre) est utilisé pour convertir
 * les dimensions en pixels de l'obstacle en unités physiques de Box2D.</p>
 */
public abstract class Obstacle {

    /**
     * Le corps physique Box2D associé à cet obstacle.
     */
    protected Body body;

    /**
     * Le monde physique Box2D dans lequel l'obstacle existe.
     */
    protected World world;

    /**
     * Le facteur Pixels par Mètre utilisé pour la conversion des dimensions.
     */
    protected float PPM;

    /**
     * Constructeur de la classe {@code Obstacle}.
     *
     * @param world Le monde physique Box2D dans lequel l'obstacle sera ajouté.
     * @param PPM   Le facteur Pixels par Mètre pour la conversion des dimensions.
     */
    public Obstacle(World world, float PPM) {
        this.world = world;
        this.PPM = PPM;
    }

    /**
     * Méthode abstraite pour créer le corps physique de l'obstacle.
     *
     * <p>Les sous-classes doivent implémenter cette méthode pour définir les propriétés
     * spécifiques du corps, comme sa position, ses dimensions, et ses fixtures.</p>
     *
     * @param x      La position X (en pixels) de l'obstacle.
     * @param y      La position Y (en pixels) de l'obstacle.
     * @param width  La largeur (en pixels) de l'obstacle.
     * @param height La hauteur (en pixels) de l'obstacle.
     */
    public abstract void createBody(float x, float y, float width, float height);

    /**
     * Retourne le corps physique {@link Body} associé à cet obstacle.
     *
     * @return Le corps physique de l'obstacle.
     */
    public Body getBody() {
        return body;
    }
}
