package com.mygame.model.maps.obstacles;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Représente un téléporteur dans le jeu. Cette classe hérite de la classe {@link Obstacle} et crée un objet statique
 * avec un détecteur de collision qui fonctionne comme un capteur, sans affecter la physique du jeu.
 */
public class Teleporter extends Obstacle {

    /**
     * Constructeur de la classe {@link Teleporter}.
     *
     * @param world Le monde physique dans lequel l'obstacle est placé.
     * @param PPM   Le rapport pixels/mètres (Pixels per Meter) utilisé pour les conversions de coordonnées dans le monde physique.
     */
    public Teleporter(World world, float PPM) {
        super(world, PPM);
    }

    /**
     * Crée le corps physique du téléporteur dans le monde Box2D.
     * Le corps est statique, ce qui signifie qu'il ne bouge pas.
     * Le téléporteur est défini comme un capteur, ce qui permet de détecter les objets qui entrent en collision sans
     * interférer avec leur mouvement.
     *
     * @param x      La position x de l'obstacle dans le monde.
     * @param y      La position y de l'obstacle dans le monde.
     * @param width  La largeur de l'obstacle.
     * @param height La hauteur de l'obstacle.
     */
    @Override
    public void createBody(float x, float y, float width, float height) {
        // Définition du corps physique (type statique)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + width / 2, y + height / 2);

        body = world.createBody(bodyDef);

        // Création de la forme (rectangle) du téléporteur
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        // Définition des propriétés du fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;  // Le téléporteur agit comme un capteur

        // Création du fixture pour le téléporteur avec un identifiant utilisateur
        body.createFixture(fixtureDef).setUserData("exit");

        // Libération de la mémoire utilisée par la forme
        shape.dispose();
    }
}
