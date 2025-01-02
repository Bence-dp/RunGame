package com.mygame.controller.commands;

import com.mygame.model.entities.Player;

/**
 * Classe représentant la commande de saut du joueur.
 * Cette commande appelle la méthode `jump()` de l'entité `Player` pour effectuer un saut.
 */
public class JumpCommand implements Command {

    private final Player player;

    /**
     * Constructeur de la commande de saut.
     *
     * @param player L'entité `Player` qui exécute l'action de saut.
     */
    public JumpCommand(Player player) {
        this.player = player;
    }

    /**
     * Exécute la commande de saut.
     * Cette méthode appelle la méthode `jump()` sur l'entité `Player` pour que le joueur effectue un saut.
     */
    @Override
    public void execute() {
        player.jump();
    }
}
