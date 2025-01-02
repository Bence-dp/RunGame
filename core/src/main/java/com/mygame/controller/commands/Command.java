package com.mygame.controller.commands;

/**
 * Interface représentant une commande dans le jeu.
 * Cette interface définit la méthode `execute()` que toutes les commandes doivent implémenter.
 */
public interface Command {

    /**
     * Exécute la commande.
     * Cette méthode est appelée pour effectuer l'action associée à la commande.
     */
    void execute();
}
