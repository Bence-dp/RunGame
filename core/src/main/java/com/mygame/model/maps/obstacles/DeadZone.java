package com.mygame.model.maps.obstacles;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * La classe {@code DeadZone} représente une zone spéciale dans le jeu
 * où les objets ou personnages entrant sont détectés pour déclencher un comportement spécifique,
 * tel qu'une élimination ou une action particulière.
 *
 * <p>Cette classe hérite de {@link Obstacle} et utilise un capteur Box2D (sensor)
 * pour détecter les collisions sans générer de réponse physique directe.</p>
 */
public class DeadZone extends Obstacle {

    /**
     * Constructeur de la classe {@code DeadZone}.
     *
     * @param world Le monde physique Box2D dans lequel la zone sera ajoutée.
     * @param PPM   Le facteur Pixels par Mètre (PPM) pour la conversion des coordonnées.
     */
    public DeadZone(World world, float PPM) {
        super(world, PPM);
    }

    /**
     * Crée le corps physique de la zone morte (DeadZone) dans le monde Box2D.
     *
     * <p>Le corps est défini comme statique et utilise un capteur (sensor)
     * pour détecter les collisions sans appliquer de forces physiques.
     * Les dimensions de la zone sont définies en pixels et converties en mètres à l'aide du facteur PPM.</p>
     *
     * @param x      La position X (en pixels) de la zone.
     * @param y      La position Y (en pixels) de la zone.
     * @param width  La largeur (en pixels) de la zone.
     * @param height La hauteur (en pixels) de la zone.
     */
    @Override
    public void createBody(float x, float y, float width, float height) {
        // Définir le corps statique
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + width / 2, y + height / 2);

        body = world.createBody(bodyDef);

        // Définir la forme rectangulaire de la zone
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        // Définir les propriétés du capteur
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true; // Défini comme capteur (sensor)

        // Créer la fixture et associer la donnée utilisateur "deadzone"
        body.createFixture(fixtureDef).setUserData("deadzone");
        shape.dispose(); // Libérer les ressources de la forme
    }
}
