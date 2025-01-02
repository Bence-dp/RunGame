package com.mygame.model.maps.obstacles;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * La classe {@code SolidObstacle} représente un obstacle solide statique dans la carte du jeu.
 *
 * <p>Ce type d'obstacle est conçu pour représenter des éléments de la carte qui sont
 * immobiles et non traversables par les entités. Par exemple, des murs, des rochers,
 * ou d'autres objets solides qui bloquent le passage.</p>
 *
 * <p>Les obstacles solides ont une faible restitution (rebond) et aucune friction par défaut,
 * ce qui les rend idéaux pour les collisions simples dans les jeux.</p>
 */
public class SolidObstacle extends Obstacle {

    /**
     * Constructeur de la classe {@code SolidObstacle}.
     *
     * @param world Le monde physique Box2D dans lequel l'obstacle sera ajouté.
     * @param PPM   Le facteur Pixels par Mètre utilisé pour la conversion des dimensions.
     */
    public SolidObstacle(World world, float PPM) {
        super(world, PPM);
    }

    /**
     * Crée le corps physique de l'obstacle solide.
     *
     * <p>Cette méthode définit un corps statique avec une forme rectangulaire.
     * Le corps est positionné et dimensionné en fonction des paramètres fournis.</p>
     *
     * @param x      La position X (en pixels) de l'obstacle.
     * @param y      La position Y (en pixels) de l'obstacle.
     * @param width  La largeur (en pixels) de l'obstacle.
     * @param height La hauteur (en pixels) de l'obstacle.
     */
    @Override
    public void createBody(float x, float y, float width, float height) {
        // Définir les propriétés du corps physique
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + width / 2, y + height / 2);

        // Créer le corps dans le monde physique
        body = world.createBody(bodyDef);

        // Définir la forme de l'obstacle (rectangle)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        // Définir les propriétés de la fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0.1f; // Faible rebond
        fixtureDef.friction = 0.0f;   // Pas de friction

        // Ajouter la fixture au corps
        body.createFixture(fixtureDef);

        // Libérer les ressources de la forme après utilisation
        shape.dispose();
    }
}
