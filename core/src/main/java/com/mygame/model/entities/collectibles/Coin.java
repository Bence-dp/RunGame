package com.mygame.model.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.model.entities.Collectible;

/**
 * Représente une pièce (coin) dans le jeu, qui peut être collectée par le joueur.
 * La classe hérite de la classe {@link Collectible} et surcharge la méthode {@link #collect()}
 * pour ajouter la logique spécifique à la collecte d'une pièce.
 */
public class Coin extends Collectible {

    /**
     * Constructeur de la pièce. Initialise les propriétés de la pièce à collecter.
     *
     * @param x La position en X de la pièce dans le monde du jeu.
     * @param y La position en Y de la pièce dans le monde du jeu.
     * @param sprite Le sprite qui représente la pièce à afficher.
     * @param world Le monde physique dans lequel la pièce est ajoutée.
     */
    public Coin(float x, float y, Sprite sprite, World world) {
        super(x, y, sprite, "coin", world);  // Spécifie que c'est une "coin"
    }

    /**
     * Logique spécifique de collecte d'une pièce. Appelle la méthode {@link Collectible#collect()}
     * de la classe parente et ajoute des actions supplémentaires pour la collecte de la pièce,
     * comme l'affichage d'un message ou l'ajout au score du joueur.
     */
    @Override
    public void collect() {
        super.collect();  // Appeler la méthode collect() de la classe parent
        // Logique spécifique à la collecte d'une pièce
        System.out.println("Vous avez collecté une pièce!");
        // Ajouter à votre score ou autre effet
    }
}
