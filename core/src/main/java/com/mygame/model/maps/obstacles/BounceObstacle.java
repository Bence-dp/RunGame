package com.mygame.model.maps.obstacles;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * La classe {@code BounceObstacle} représente un obstacle rebondissant dans le jeu.
 * Cet obstacle est défini comme un corps statique dans le monde physique Box2D
 * avec une restitution élevée, permettant aux objets de rebondir fortement lorsqu'ils le touchent.
 *
 * <p>Cette classe hérite de {@link Obstacle} et utilise les propriétés de Box2D pour
 * définir le comportement physique de l'obstacle.</p>
 */
public class BounceObstacle extends Obstacle {

    /**
     * Constructeur de la classe {@code BounceObstacle}.
     *
     * @param world Le monde physique Box2D dans lequel l'obstacle sera ajouté.
     * @param PPM   Le facteur Pixels par Mètre (PPM) pour la conversion des coordonnées.
     */
    public BounceObstacle(World world, float PPM) {
        super(world, PPM);
    }

    /**
     * Crée le corps physique de l'obstacle rebondissant dans le monde Box2D.
     *
     * <p>Le corps est défini comme statique et utilise une restitution élevée (1.2f)
     * pour créer un effet de rebond important. Les dimensions de l'obstacle
     * sont définies en pixels et converties en mètres à l'aide du facteur PPM.</p>
     *
     * @param x      La position X (en pixels) de l'obstacle.
     * @param y      La position Y (en pixels) de l'obstacle.
     * @param width  La largeur (en pixels) de l'obstacle.
     * @param height La hauteur (en pixels) de l'obstacle.
     */
    @Override
    public void createBody(float x, float y, float width, float height) {
        // Définir le corps statique
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + width / 2, y + height / 2);

        body = world.createBody(bodyDef);

        // Définir la forme rectangulaire de l'obstacle
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        // Définir les propriétés physiques
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1.2f; // Restitution élevée pour un rebond fort
        fixtureDef.friction = 0.0f;    // Friction nulle

        // Créer la fixture et associer la forme au corps
        body.createFixture(fixtureDef);
        shape.dispose(); // Libérer les ressources de la forme
    }
}
