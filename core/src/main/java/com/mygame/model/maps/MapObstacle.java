package com.mygame.model.maps;

/**
 * La classe {@code MapObstacle} représente un obstacle sur la carte du jeu.
 * Chaque obstacle a une position (x, y) et une taille (largeur, hauteur).
 */
public class MapObstacle {

    private final float x;      // Position X de l'obstacle
    private final float y;      // Position Y de l'obstacle
    private final float width;  // Largeur de l'obstacle
    private final float height; // Hauteur de l'obstacle

    /**
     * Constructeur de la classe {@code MapObstacle}.
     * Initialise les propriétés de l'obstacle avec les valeurs fournies.
     *
     * @param x La position X de l'obstacle.
     * @param y La position Y de l'obstacle.
     * @param width La largeur de l'obstacle.
     * @param height La hauteur de l'obstacle.
     */
    public MapObstacle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Retourne la position X de l'obstacle.
     *
     * @return La position X de l'obstacle.
     */
    public float getX() {
        return x;
    }

    /**
     * Retourne la position Y de l'obstacle.
     *
     * @return La position Y de l'obstacle.
     */
    public float getY() {
        return y;
    }

    /**
     * Retourne la largeur de l'obstacle.
     *
     * @return La largeur de l'obstacle.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur de l'obstacle.
     *
     * @return La hauteur de l'obstacle.
     */
    public float getHeight() {
        return height;
    }
}
