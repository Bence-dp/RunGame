package com.mygame.utils.ContactListener;

import com.badlogic.gdx.physics.box2d.*;
import com.mygame.common.EntityFactory;
import com.mygame.common.SoundFactory;
import com.mygame.model.entities.Collectible;
import com.mygame.model.entities.GameEntity;
import com.mygame.model.entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code CollectibleListener} est un écouteur de contacts qui gère la collecte des objets (collectibles)
 * lorsqu'un joueur entre en collision avec eux.
 * <p>
 * Lorsqu'un joueur entre en contact avec un objet collectible, l'objet est collecté et ajouté à la liste des objets à détruire.
 * Après avoir collecté l'objet, ce dernier est marqué comme inactif et retiré de la simulation physique.
 */
public class CollectibleListener implements ContactListener {

    private final List<GameEntity> collectiblesToDestroy = new ArrayList<>();  // Liste des objets à détruire
    private SoundFactory soundFactory;
    private EntityFactory entityFactory;  // Factory d'entités permettant de récupérer les entités du jeu

    /**
     * Constructeur de {@code CollectibleListener}.
     *
     * @param entityFactory La factory d'entités permettant d'accéder aux entités du jeu.
     */
    public CollectibleListener(EntityFactory entityFactory, SoundFactory soundFactory) {
        this.entityFactory = entityFactory;
        this.soundFactory = soundFactory;
    }

    /**
     * Méthode appelée lorsqu'un contact débute. Si le joueur entre en contact avec un objet collectible,
     * l'objet est collecté et marqué pour destruction.
     *
     * @param contact L'objet représentant le contact.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            if (isPlayer(fixtureA) && isCollectible(fixtureB)) {
                collectItem(fixtureB, fixtureA);
            } else if (isPlayer(fixtureB) && isCollectible(fixtureA)) {
                collectItem(fixtureA, fixtureB);
            }
        }
    }

    /**
     * Vérifie si la fixture donnée représente un objet collectible.
     *
     * @param fixture La fixture à vérifier.
     * @return {@code true} si la fixture représente un collectible, sinon {@code false}.
     */
    private boolean isCollectible(Fixture fixture) {
        return fixture.getUserData().equals("collectible");
    }

    /**
     * Vérifie si la fixture donnée représente le joueur.
     *
     * @param fixture La fixture à vérifier.
     * @return {@code true} si la fixture représente le joueur, sinon {@code false}.
     */
    private boolean isPlayer(Fixture fixture) {
        return fixture.getUserData().equals("player");
    }

    /**
     * Gère la collecte d'un objet lorsque le joueur entre en contact avec celui-ci.
     * L'objet collectible est désactivé et marqué pour destruction.
     *
     * @param collectableFixture La fixture représentant l'objet collectible.
     * @param playerFixture La fixture représentant le joueur.
     */
    private void collectItem(Fixture collectableFixture, Fixture playerFixture) {
        Body c_body = collectableFixture.getBody();
        Body p_body = playerFixture.getBody();
        System.err.println("collecting item");
        GameEntity collectible = null;
        GameEntity player = null;

        // Trouver les entités correspondantes à partir des corps physiques
        for (GameEntity entity : entityFactory.getEntities()) {
            if(entity.getBody() == c_body) {
                collectible = (Collectible)entity;
            }
            if(entity.getBody() == p_body) {
                player = (Player) entity;
            }
            if (collectible != null && player != null) {
                break;
            }
        }

        // Si l'objet collectible est actif, le joueur le collecte
        if (collectible != null && collectible.isActive()) {
            player.collectPiece();  // Le joueur collecte l'objet
            soundFactory.playSound("piece");
            collectible.setActive(false);  // Désactive l'objet
            collectiblesToDestroy.add(collectible);  // Marque l'objet pour destruction
        }
    }

    /**
     * Méthode appelée lorsque le contact entre les objets se termine.
     * Aucun traitement particulier n'est nécessaire ici pour les objets collectables.
     *
     * @param contact L'objet représentant le contact.
     */
    @Override
    public void endContact(Contact contact) {
        // Aucun traitement nécessaire lorsque la collision se termine
    }

    /**
     * Méthode appelée avant la solution de la collision.
     * Aucun traitement particulier n'est nécessaire ici.
     *
     * @param contact L'objet représentant le contact.
     * @param oldManifold L'ancien manifold de la collision.
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Aucun traitement nécessaire avant la solution de la collision
    }

    /**
     * Méthode appelée après la solution de la collision.
     * Aucun traitement particulier n'est nécessaire ici.
     *
     * @param contact L'objet représentant le contact.
     * @param impulse L'impact de la collision.
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Aucun traitement nécessaire après la solution de la collision
    }

    /**
     * Traite les objets collectables en attente de destruction.
     * Supprime les objets collectés de la simulation physique et les enlève de la liste des entités.
     *
     * @param world Le monde physique où les objets sont détruits.
     */
    public void processPendingDestructions(World world) {
        for (GameEntity collectible : collectiblesToDestroy) {
            world.destroyBody(collectible.getBody());  // Supprime le corps physique de l'objet
            entityFactory.getEntities().removeValue(collectible, true);  // Supprime l'objet collectible de la liste des entités
        }
        collectiblesToDestroy.clear();  // Vide la liste des objets à détruire
    }
}
