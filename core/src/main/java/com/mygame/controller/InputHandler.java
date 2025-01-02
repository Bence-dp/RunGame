package com.mygame.controller;

import com.badlogic.gdx.InputProcessor;
import com.mygame.config.KeyBindings;
import com.mygame.model.entities.Player;
import com.mygame.controller.commands.*;

/**
 * Classe responsable de la gestion des entrées utilisateur dans le jeu, telles que les
 * touches du clavier et les commandes associées au mouvement du joueur.
 * Cette classe implémente l'interface {@link InputProcessor} de LibGDX pour intercepter
 * les événements de touches et les relier aux actions du joueur.
 */
public class InputHandler implements InputProcessor {

    private Player player;
    private KeyBindings keyBindings;
    private Command moveRightCommand;
    private Command moveLeftCommand;
    private Command jumpCommand;
    private Command stopMovementCommand;

    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;
    private boolean isJumping = false;

    /**
     * Constructeur de la classe InputHandler.
     *
     * @param player Le joueur pour lequel les entrées seront traitées.
     */
    public InputHandler(Player player) {
        this.player = player;
        this.keyBindings = new KeyBindings(); // Initialisation avec les touches par défaut
        updateCommands(); // Initialiser les commandes
    }

    /**
     * Met à jour les commandes en fonction des nouvelles touches définies dans les paramètres
     * (re-mappées par l'utilisateur).
     *
     * @param keyBindings Les nouvelles configurations de touches.
     */
    public void updateKeyBindings(KeyBindings keyBindings) {
        this.keyBindings = keyBindings;
        updateCommands();  // Mettre à jour les commandes
    }

    /**
     * Met à jour les objets de commande pour chaque action possible du joueur (mouvement, saut, etc.)
     */
    private void updateCommands() {
        moveRightCommand = new MoveRightCommand(player);
        moveLeftCommand = new MoveLeftCommand(player);
        jumpCommand = new JumpCommand(player);
        stopMovementCommand = new StopCommand(player);  // Ajouter la commande pour arrêter le mouvement
    }

    /**
     * Gère l'événement de la touche enfoncée (keyDown).
     *
     * @param keycode Le code de la touche pressée.
     * @return false si l'événement a été traité, true sinon.
     */
    @Override
    public boolean keyDown(int keycode) {
        // Gestion du mouvement à droite
        if (keycode == keyBindings.getMoveRightKey()) {
            if (!isMovingRight) {  // Ne pas exécuter si déjà en mouvement vers la droite
                isMovingRight = true;
                moveRightCommand.execute();
            }
        }

        // Gestion du mouvement à gauche
        if (keycode == keyBindings.getMoveLeftKey()) {
            if (!isMovingLeft) {  // Ne pas exécuter si déjà en mouvement vers la gauche
                isMovingLeft = true;
                moveLeftCommand.execute();
            }
        }

        // Gestion du saut
        if (keycode == keyBindings.getJumpKey() && !isJumping) {
            isJumping = true;
            jumpCommand.execute();
        }

        return false;
    }

    /**
     * Gère l'événement de la touche relâchée (keyUp).
     *
     * @param keycode Le code de la touche relâchée.
     * @return false si l'événement a été traité, true sinon.
     */
    @Override
    public boolean keyUp(int keycode) {
        // Gérer le relâchement des touches de mouvement
        if (keycode == keyBindings.getMoveRightKey()) {
            isMovingRight = false;
        }
        if (keycode == keyBindings.getMoveLeftKey()) {
            isMovingLeft = false;
        }

        // Si aucune touche de mouvement n'est pressée, arrêter le mouvement
        if (!isMovingRight && !isMovingLeft) {
            stopMovementCommand.execute();
        }

        // Gérer le relâchement de la touche de saut
        if (keycode == keyBindings.getJumpKey()) {
            isJumping = false;
        }

        return false;
    }

    /**
     * Gère l'événement de la touche tapée (keyTyped). Cette méthode n'est pas utilisée
     * dans ce cas.
     *
     * @param character Le caractère tapé.
     * @return false.
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Gère l'événement de pression sur l'écran (touchDown). Cette méthode n'est pas utilisée
     * dans ce cas, mais peut être implémentée pour une interaction basée sur le tactile.
     *
     * @param screenX La position X de l'écran.
     * @param screenY La position Y de l'écran.
     * @param pointer L'identifiant du pointeur.
     * @param button Le bouton de la souris (si applicable).
     * @return false.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Gère l'événement de relâchement sur l'écran (touchUp). Cette méthode n'est pas utilisée
     * dans ce cas, mais peut être implémentée pour une interaction basée sur le tactile.
     *
     * @param screenX La position X de l'écran.
     * @param screenY La position Y de l'écran.
     * @param pointer L'identifiant du pointeur.
     * @param button Le bouton de la souris (si applicable).
     * @return false.
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Gère le déplacement de l'écran lorsque l'utilisateur le fait glisser (touchDragged).
     * Cette méthode n'est pas utilisée dans ce cas.
     *
     * @param screenX La position X de l'écran.
     * @param screenY La position Y de l'écran.
     * @param pointer L'identifiant du pointeur.
     * @return false.
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Gère le mouvement de la souris sur l'écran (mouseMoved). Cette méthode n'est pas utilisée
     * dans ce cas.
     *
     * @param screenX La position X de la souris.
     * @param screenY La position Y de la souris.
     * @return false.
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Gère l'événement de défilement de la souris (scrolled). Cette méthode n'est pas utilisée
     * dans ce cas.
     *
     * @param amountX Le déplacement en X.
     * @param amountY Le déplacement en Y.
     * @return false.
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    /**
     * Gère l'annulation d'une touche ou d'une action (touchCancelled). Cette méthode n'est pas utilisée
     * dans ce cas.
     *
     * @param screenX La position X de l'écran.
     * @param screenY La position Y de l'écran.
     * @param pointer L'identifiant du pointeur.
     * @param button Le bouton de la souris (si applicable).
     * @return false.
     */
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
