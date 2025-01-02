package com.mygame.controller.commands;

import com.mygame.model.entities.Player;

/**
 * Classe représentant la commande de mouvement à droite du joueur.
 * Cette commande appelle la méthode `moveRight()` de l'entité `Player` pour déplacer le joueur vers la droite.
 */
public class MoveRightCommand implements Command {

    private final Player player;

    /**
     * Constructeur de la commande de déplacement à droite.
     *
     * @param player L'entité `Player` qui exécute l'action de mouvement à droite.
     */
    public MoveRightCommand(Player player) {
        this.player = player;
    }

    /**
     * Exécute la commande de mouvement à droite.
     * Cette méthode appelle la méthode `moveRight()` sur l'entité `Player` pour que le joueur se déplace vers la droite.
     */
    @Override
    public void execute() {
        player.moveRight();
    }
}
