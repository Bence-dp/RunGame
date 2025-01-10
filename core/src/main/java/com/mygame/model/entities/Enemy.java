package com.mygame.model.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * La classe {@code Enemy} représente un ennemi dans le jeu.
 * Un ennemi est une entité du jeu qui possède une position, une texture (sprite),
 * et un comportement physique dans le monde Box2D. Il peut interagir avec d'autres entités,
 * notamment le joueur et les objets collectables.
 *
 * <p>Les ennemis peuvent avoir un mouvement spécifique ou un comportement de détection,
 * mais cette classe de base fournit les fonctionnalités générales pour un ennemi dans le jeu.</p>
 */
public class Enemy extends GameEntity {

    // Vitesse de l'ennemi
    float speed;

    /**
     * Constructeur de la classe {@code Enemy}.
     *
     * @param x      La position X de l'ennemi dans le monde du jeu (en pixels).
     * @param y      La position Y de l'ennemi dans le monde du jeu (en pixels).
     * @param sprite Le sprite (texture) de l'ennemi.
     * @param world  Le monde physique Box2D dans lequel l'ennemi sera placé.
     * @param type   Le type du corps physique (statique, dynamique, etc.) de l'ennemi.
     */
    public Enemy(float x, float y, Sprite sprite, World world, BodyDef.BodyType type) {
        super(x, y, sprite, world, type);  // Appelle le constructeur de la classe parent (GameEntity)
        createSensor();  // Crée un capteur autour de l'ennemi pour détecter les collisions
    }

    /**
     * Met à jour l'ennemi à chaque frame. Cette méthode peut contenir la logique spécifique
     * aux ennemis, comme les déplacements ou les animations.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour (en secondes).
     */
    @Override
    public void update(float deltaTime) {
        // Logique spécifique aux ennemis (par exemple, déplacement, attaque, etc.)
    }

    /**
     * Met à jour la physique de l'ennemi. Cette méthode peut être utilisée pour mettre à jour
     * le comportement physique de l'ennemi, mais ici elle est vide car les ennemis peuvent
     * ne pas avoir de comportements physiques complexes.
     */
    @Override
    public void updatePhysics() {
        // Pas de mise à jour physique spécifique pour l'ennemi dans cette version.
    }

    /**
     * Crée un capteur autour de l'ennemi pour détecter des objets, tels que des collectibles ou des zones d'attaque.
     * Ce capteur est utilisé pour activer des interactions sans modifier la physique de l'ennemi.
     * Le capteur est créé sur la première fixture du corps et est configuré comme un "sensor".
     * Un "sensor" est une fixture qui ne génère pas de force physique mais peut déclencher des événements de collision.
     */
    public void createSensor() {
        // Récupère la première fixture de l'ennemi (qui représente sa forme physique)
        getBody().getFixtureList().get(0).setSensor(true);  // Définir cette fixture comme un capteur (sensor)
        getBody().getFixtureList().get(0).setUserData("deadzone");  // Associe un nom de "deadzone" à ce capteur
    }
}
