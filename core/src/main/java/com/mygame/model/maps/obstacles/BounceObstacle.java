package com.mygame.model.maps.obstacles;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * La classe {@code BounceObstacle} représente un obstacle dans le jeu qui
 * offre un effet de rebond lorsqu'un autre objet entre en contact avec lui.
 * Cet obstacle est défini comme un corps statique dans le monde physique Box2D.
 * <p>
 * L'obstacle utilise une restitution élevée pour permettre aux objets d'y rebondir
 * fortement lorsqu'ils le touchent. Il est principalement utilisé pour créer des obstacles
 * dans le monde du jeu qui influencent la physique du joueur ou d'autres objets.
 * </p>
 */
public class BounceObstacle extends Obstacle {

    /**
     * Constructeur de la classe {@code BounceObstacle}.
     *
     * @param world Le monde physique Box2D dans lequel l'obstacle sera ajouté.
     * @param PPM   Le facteur Pixels par Mètre (PPM) pour la conversion des coordonnées
     *              du jeu de pixels vers des unités physiques.
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
     * @param x      La position X (en pixels) de l'obstacle dans le monde du jeu.
     * @param y      La position Y (en pixels) de l'obstacle dans le monde du jeu.
     * @param width  La largeur (en pixels) de l'obstacle.
     * @param height La hauteur (en pixels) de l'obstacle.
     */
    @Override
    public void createBody(float x, float y, float width, float height) {
        // Définir le corps statique pour l'obstacle
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;  // Corps statique
        bodyDef.position.set(x + width / 2, y + height / 2);  // Position du corps

        // Créer le corps physique dans le monde Box2D
        body = world.createBody(bodyDef);

        // Définir la forme de l'obstacle (rectangle)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);  // La forme est un rectangle de dimensions (width, height)

        // Définir les propriétés physiques de la fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1.2f;  // Restitution élevée pour un rebond fort
        fixtureDef.friction = 0.0f;     // Friction nulle (pas d'effet de glissement)

        // Créer la fixture avec les propriétés définies
        body.createFixture(fixtureDef);

        // Libérer les ressources de la forme après l'avoir utilisée
        shape.dispose();
    }
}
