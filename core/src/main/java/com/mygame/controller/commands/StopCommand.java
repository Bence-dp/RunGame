package com.mygame.controller.commands;

import com.mygame.model.entities.Player;

/**
 * Classe représentant la commande d'arrêt du joueur.
 * Cette commande appelle la méthode pour arrêter le mouvement horizontal du joueur tout en préservant sa vélocité verticale.
 */
public class StopCommand implements Command {

    private Player player;

    /**
     * Constructeur de la commande d'arrêt.
     *
     * @param player L'entité `Player` qui exécute l'action d'arrêt.
     */
    public StopCommand(Player player) {
        this.player = player;
    }

    /**
     * Exécute la commande d'arrêt.
     * Cette méthode arrête le mouvement horizontal du joueur en mettant la vélocité horizontale à 0,
     * tout en maintenant sa vélocité verticale inchangée.
     */
    @Override
    public void execute() {
        if (player != null) {
            player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
        }
    }
}
