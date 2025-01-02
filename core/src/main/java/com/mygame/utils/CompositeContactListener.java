package com.mygame.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.mygame.utils.ContactListener.CollectibleListener;

/**
 * La classe {@code CompositeContactListener} permet de combiner plusieurs écouteurs (listeners) de collisions.
 * Elle implémente l'interface {@link ContactListener} et délègue les appels de méthodes de gestion des contacts
 * à une liste d'écouteurs de collisions passés en paramètre.
 * <p>
 * Cette classe est utile pour gérer plusieurs types d'événements de collisions en utilisant un seul écouteur composite.
 * </p>
 */
public class CompositeContactListener implements ContactListener {

    private final ContactListener[] listeners;  // Liste des écouteurs de contacts

    /**
     * Constructeur qui prend une liste d'écouteurs de collisions à combiner.
     *
     * @param listeners Liste d'écouteurs de collisions. Ces écouteurs seront appelés lors des événements de collision.
     */
    public CompositeContactListener(ContactListener... listeners) {
        this.listeners = listeners;  // Initialisation de la liste des écouteurs
    }

    /**
     * Récupère le {@link CollectibleListener} parmi les écouteurs de contact.
     *
     * @return Le {@link CollectibleListener} s'il existe parmi les écouteurs, sinon {@code null}.
     */
    public CollectibleListener getCollectibleListener() {
        CollectibleListener collectibleListener = null;
        for (ContactListener listener : listeners) {
            if (listener instanceof CollectibleListener) {
                return (CollectibleListener) listener;  // Retourne le CollectibleListener trouvé
            }
        }
        return collectibleListener;  // Retourne null si aucun CollectibleListener n'est trouvé
    }

    @Override
    public void beginContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.beginContact(contact);  // Délégation à chaque écouteur
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.endContact(contact);  // Délégation à chaque écouteur
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        for (ContactListener listener : listeners) {
            listener.preSolve(contact, oldManifold);  // Délégation à chaque écouteur
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (ContactListener listener : listeners) {
            listener.postSolve(contact, impulse);  // Délégation à chaque écouteur
        }
    }

    // Ajoutez ici une méthode update() pour appeler la méthode update sur chaque écouteur, si nécessaire
}
