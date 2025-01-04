package com.mygame.model.entities.enemies.movement;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Interface définissant une stratégie de mouvement pour les ennemis.
 */
public interface MovementStrategy  {
    /**
     * Met à jour le mouvement en fonction de la physique et de la logique de la stratégie.
     *
     * @param body Le corps physique de l'ennemi à déplacer.
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour.
     */
    void updateMovement(Body body, float deltaTime);
}
