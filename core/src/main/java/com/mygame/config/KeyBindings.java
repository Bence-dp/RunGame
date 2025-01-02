package com.mygame.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;

/**
 * Classe de gestion des bindings de touches du jeu.
 * Permet de récupérer et d'enregistrer les touches utilisées pour les actions du joueur.
 */
public class KeyBindings {

    private static final String PREFS_NAME = "key_bindings";
    private static Preferences preferences;

    // Bloc statique pour initialiser les préférences
    static {
        preferences = Gdx.app.getPreferences(PREFS_NAME);
    }

    /**
     * Constructeur par défaut. Si les préférences de touches n'existent pas, elles sont initialisées
     * avec des valeurs par défaut (flèches gauche/droite et espace pour sauter).
     */
    public KeyBindings() {
        if (!preferences.contains("move_right")) {
            setMoveRightKey(Input.Keys.RIGHT);
            setMoveLeftKey(Input.Keys.LEFT);
            setJumpKey(Input.Keys.SPACE);
        }
    }

    /**
     * Récupère la touche associée au mouvement vers la droite.
     *
     * @return Le code de la touche associée au mouvement vers la droite.
     */
    public int getMoveRightKey() {
        return preferences.getInteger("move_right", Input.Keys.RIGHT);
    }

    /**
     * Récupère la touche associée au mouvement vers la gauche.
     *
     * @return Le code de la touche associée au mouvement vers la gauche.
     */
    public int getMoveLeftKey() {
        return preferences.getInteger("move_left", Input.Keys.LEFT);
    }

    /**
     * Récupère la touche associée au saut.
     *
     * @return Le code de la touche associée au saut.
     */
    public int getJumpKey() {
        return preferences.getInteger("jump", Input.Keys.SPACE);
    }

    /**
     * Définit la touche à utiliser pour le mouvement vers la droite.
     *
     * @param keyCode Le code de la touche à associer au mouvement vers la droite.
     */
    public void setMoveRightKey(int keyCode) {
        preferences.putInteger("move_right", keyCode);
        preferences.flush();
    }

    /**
     * Définit la touche à utiliser pour le mouvement vers la gauche.
     *
     * @param keyCode Le code de la touche à associer au mouvement vers la gauche.
     */
    public void setMoveLeftKey(int keyCode) {
        preferences.putInteger("move_left", keyCode);
        preferences.flush();
    }

    /**
     * Définit la touche à utiliser pour le saut.
     *
     * @param keyCode Le code de la touche à associer au saut.
     */
    public void setJumpKey(int keyCode) {
        preferences.putInteger("jump", keyCode);
        preferences.flush();
    }
}
