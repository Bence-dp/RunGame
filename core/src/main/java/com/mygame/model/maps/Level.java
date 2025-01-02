package com.mygame.model.maps;

import java.io.Serial;
import java.io.Serializable;

/**
 * La classe {@code Level} représente un niveau dans le jeu.
 * Un niveau contient des informations concernant le chemin de la carte associée et son nom.
 * Il permet aussi de lier plusieurs niveaux ensemble grâce à une référence vers le niveau suivant.
 */
public class Level implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Identifiant de version pour la sérialisation

    private String mapPath; // Chemin du niveau (fichier de carte Tiled)
    private String name; // Nom du niveau (facultatif)
    private Level next;  // Référence au niveau suivant

    /**
     * Constructeur par défaut de la classe {@code Level}.
     * Initialise un niveau sans chemin, sans nom, et sans niveau suivant.
     */
    public Level() {
    }

    /**
     * Retourne le chemin du niveau, qui représente l'emplacement du fichier de carte.
     *
     * @return Le chemin du niveau sous forme de chaîne de caractères.
     */
    public String getPath() {
        return mapPath;
    }

    /**
     * Retourne le nom du niveau, s'il est défini.
     *
     * @return Le nom du niveau, ou {@code null} si aucun nom n'est défini.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le niveau suivant auquel ce niveau est lié.
     *
     * @return Le niveau suivant, ou {@code null} si ce niveau n'a pas de niveau suivant.
     */
    public Level getNext() {
        return next;
    }

    /**
     * Définit le niveau suivant auquel ce niveau doit être lié.
     *
     * @param next Le niveau suivant à lier.
     */
    public void setNext(Level next) {
        this.next = next;
    }
}
