package com.mygame.utils;

/**
 * La classe {@code Coord} représente une coordonnée dans un espace bidimensionnel (x, y).
 * Elle permet de stocker et de manipuler les coordonnées d'un point, souvent utilisé dans les jeux
 * pour définir des positions, des vitesses, ou d'autres valeurs géométriques.
 * <p>
 * Cette classe fournit des méthodes pour accéder et modifier les valeurs de x et y, ainsi qu'une méthode
 * pour obtenir une représentation sous forme de chaîne de caractères de l'objet {@code Coord}.
 * </p>
 */
public class Coord {
    private float x;  // Coordonnée en x
    private float y;  // Coordonnée en y

    /**
     * Constructeur pour initialiser les coordonnées avec des valeurs données.
     *
     * @param x La valeur de la coordonnée en x.
     * @param y La valeur de la coordonnée en y.
     */
    public Coord(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la valeur de la coordonnée x.
     *
     * @return La valeur de la coordonnée x.
     */
    public float getX() {
        return x;
    }

    /**
     * Modifie la valeur de la coordonnée x.
     *
     * @param x La nouvelle valeur pour la coordonnée x.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Retourne la valeur de la coordonnée y.
     *
     * @return La valeur de la coordonnée y.
     */
    public float getY() {
        return y;
    }

    /**
     * Modifie la valeur de la coordonnée y.
     *
     * @param y La nouvelle valeur pour la coordonnée y.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet {@code Coord}.
     * Cette méthode est utile pour le débogage et l'affichage des coordonnées.
     *
     * @return La chaîne de caractères représentant l'objet {@code Coord} sous la forme "Coord{x=..., y=...}".
     */
    @Override
    public String toString() {
        return "Coord{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
