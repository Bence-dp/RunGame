package com.mygame.utils.saver;

import com.mygame.model.maps.Level;

import java.io.Serial;
import java.io.Serializable;

/**
 * La classe {@code SaveData} représente les données de sauvegarde d'un jeu.
 * Elle contient des informations sur le niveau courant du joueur et le nombre de pièces collectées.
 * <p>
 * Cette classe est utilisée pour sérialiser et désérialiser les données de sauvegarde afin de permettre au joueur de reprendre la partie là où il l'a laissée.
 * </p>
 */
public class SaveData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;  // Version UID pour la compatibilité de la sérialisation

    private Level lastLevel;  // Le dernier niveau atteint par le joueur
    private int coins;        // Le nombre de pièces collectées par le joueur

    /**
     * Constructeur pour initialiser les données de sauvegarde avec un niveau et un nombre de pièces.
     *
     * @param lastLevel Le dernier niveau atteint par le joueur.
     * @param coins Le nombre de pièces collectées par le joueur.
     */
    public SaveData(Level lastLevel, int coins) {
        this.lastLevel = lastLevel;
        this.coins = coins;
    }

    /**
     * Retourne le dernier niveau atteint par le joueur.
     *
     * @return Le dernier niveau.
     */
    public Level getLastLevel() {
        return lastLevel;
    }

    /**
     * Définit le dernier niveau atteint par le joueur.
     *
     * @param lastLevel Le dernier niveau à définir.
     */
    public void setLastLevel(Level lastLevel) {
        this.lastLevel = lastLevel;
    }

    /**
     * Retourne le nombre de pièces collectées par le joueur.
     *
     * @return Le nombre de pièces.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Définit le nombre de pièces collectées par le joueur.
     *
     * @param coins Le nombre de pièces à définir.
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères des données de sauvegarde.
     * Cela permet de visualiser facilement l'état de la sauvegarde.
     *
     * @return Une chaîne de caractères représentant les données de sauvegarde.
     */
    @Override
    public String toString() {
        return "SaveData{" +
            "lastLevel=" + lastLevel +
            ", coins=" + coins +
            '}';
    }
}
