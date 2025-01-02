package com.mygame.controller.commands;

import com.mygame.model.entities.Player;

/**
 * Classe représentant la commande de mouvement à gauche du joueur.
 * Cette commande appelle la méthode `moveLeft()` de l'entité `Player` pour déplacer le joueur vers la gauche.
 */
public class MoveLeftCommand implements Command {

    private final Player player;

    /**
     * Constructeur de la commande de déplacement à gauche.
     *
     * @param player L'entité `Player` qui exécute l'action de mouvement à gauche.
     */
    public MoveLeftCommand(Player player) {
        this.player = player;
    }

    /**
     * Exécute la commande de mouvement à gauche.
     * Cette méthode appelle la méthode `moveLeft()` sur l'entité `Player` pour que le joueur se déplace vers la gauche.
     */
    @Override
    public void execute() {
        player.moveLeft();
    }
}
