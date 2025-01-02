package com.mygame.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Représente un joueur dans le jeu. Le joueur peut se déplacer horizontalement et sauter.
 * Cette classe étend {@link GameEntity} et ajoute des comportements spécifiques au joueur,
 * tels que le mouvement, le saut, la collecte d'objets et la gestion du score.
 */
public class Player extends GameEntity {

    private Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));
    private float moveSpeed = 4f;   // Vitesse de déplacement horizontal du joueur
    private float jumpForce = 9f;   // Force du saut du joueur
    private static final float JUMP_VELOCITY_THRESHOLD = 0.1f;  // Seuil pour considérer que la vélocité Y est proche de zéro, permettant un nouveau saut
    private String direction;
    private String prevDirection;
    private int score = 0;  // Score du joueur, incrémenté lors de la collecte d'objets

    /**
     * Constructeur du joueur.
     * Ce constructeur initialise le joueur avec une position, un sprite et un monde Box2D.
     * Le type de corps physique du joueur est défini comme {@link BodyDef.BodyType#DynamicBody},
     * ce qui permet au joueur de se déplacer et d'interagir avec le monde physique.
     *
     * @param x La position X initiale du joueur.
     * @param y La position Y initiale du joueur.
     * @param sprite Le sprite représentant visuellement le joueur.
     * @param world Le monde Box2D dans lequel le joueur sera placé.
     */
    public Player(float x, float y, Sprite sprite, World world) {
        super(x, y, sprite, world, BodyDef.BodyType.DynamicBody);
        createSensor();  // Créer le capteur autour du joueur pour détecter les objets collectables
        this.direction = "right";
        this.prevDirection = "right";
    }

    /**
     * Mise à jour spécifique à la physique du joueur.
     * Cette méthode peut être étendue si nécessaire, mais actuellement, elle ne fait rien de particulier.
     */
    @Override
    public void updateSprite() {
        if (direction != prevDirection){
            getSprite().flip(true, false);
            prevDirection = direction;
        }
    }

    /**
     * Déplace le joueur vers la droite.
     * Le joueur se déplace à une vitesse horizontale fixe définie par {@link #moveSpeed}.
     */
    public void moveRight() {
        Vector2 velocity = getBody().getLinearVelocity();
        getBody().setLinearVelocity(moveSpeed, velocity.y); // Définit la vitesse horizontale
        direction = "right";

    }

    /**
     * Déplace le joueur vers la gauche.
     * Le joueur se déplace à une vitesse horizontale fixe définie par {@link #moveSpeed}.
     */
    public void moveLeft() {
        Vector2 velocity = getBody().getLinearVelocity();
        getBody().setLinearVelocity(-moveSpeed, velocity.y); // Définit la vitesse horizontale
        direction = "left";


    }

    /**
     * Fait sauter le joueur.
     * Le saut est effectué uniquement si la vélocité verticale du joueur est proche de zéro, ce qui évite des sauts multiples en l'air.
     */
    public void jump() {
        if (Math.abs(getBody().getLinearVelocity().y) < JUMP_VELOCITY_THRESHOLD) {
            jumpSound.play();
            getBody().applyLinearImpulse(new Vector2(0, jumpForce), getBody().getWorldCenter(), true); // Applique une impulsion verticale pour le saut
        }
    }

    /**
     * Crée un capteur autour du joueur pour détecter les objets collectables.
     * Le capteur est de forme circulaire et ne bloque pas la physique.
     * Il est utilisé pour détecter les objets (comme les pièces) à collecter lorsque le joueur entre en collision avec eux.
     */
    private void createSensor() {
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f); // Taille du capteur, ajustez cette valeur si nécessaire
        shape.setPosition(new Vector2(0, getSprite().getHeight() / 2)); // Positionner le capteur au centre du joueur

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;  // Le capteur ne bloque pas la physique, il détecte simplement les objets en collision

        // Crée la fixture sensorielle autour du joueur
        Fixture fixture = getBody().createFixture(fixtureDef);
        fixture.setUserData("player");  // Définir un identifiant pour la fixture

        shape.dispose(); // Libérer la mémoire utilisée par la forme du capteur
    }

    /**
     * Méthode appelée lorsque le joueur collecte une pièce.
     * Cette méthode incrémente le score du joueur à chaque fois qu'un objet collectable est touché.
     */
    @Override
    public void collectPiece() {
        score++;  // Incrémenter le score
        System.out.println("Score: " + score);  // Afficher le score dans la console
    }

    /**
     * Définit le score du joueur.
     * Cette méthode peut être utilisée pour définir le score du joueur (par exemple, après une partie).
     *
     * @param score Le score à définir pour le joueur.
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void updatePhysics() {

    }

    // Getters et Setters pour les attributs du joueur (moveSpeed, jumpForce, score)

    /**
     * Récupère la vitesse de déplacement horizontale du joueur.
     *
     * @return La vitesse de déplacement horizontale du joueur.
     */
    public float getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Définit la vitesse de déplacement horizontale du joueur.
     *
     * @param moveSpeed La nouvelle vitesse de déplacement horizontale du joueur.
     */
    public void setMoveSpeed(float moveSpeed) {
        if (moveSpeed > 0) {
            this.moveSpeed = moveSpeed;  // La vitesse doit être positive
        }
    }

    /**
     * Récupère la force du saut du joueur.
     *
     * @return La force du saut du joueur.
     */
    public float getJumpForce() {
        return jumpForce;
    }

    /**
     * Définit la force du saut du joueur.
     *
     * @param jumpForce La nouvelle force de saut du joueur.
     */
    public void setJumpForce(float jumpForce) {
        if (jumpForce > 0) {
            this.jumpForce = jumpForce;  // La force doit être positive
        }
    }


    /**
     * Récupère le score actuel du joueur.
     *
     * @return Le score actuel du joueur.
     */
    public int getScore() {
        return score;
    }
}
