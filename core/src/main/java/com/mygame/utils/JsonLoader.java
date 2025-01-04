package com.mygame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;
import com.mygame.model.maps.Level;

/**
 * Classe utilitaire pour charger les données JSON, spécifiquement les niveaux du jeu.
 */
public class JsonLoader {

    /**
     * Charge les niveaux à partir d'un fichier JSON.
     *
     * @param filePath Le chemin vers le fichier JSON.
     * @return Une liste d'objets de type Level.
     */
    public static Array<Level> loadLevels(String filePath) {
        Json json = new Json();

        try {
            // Charger les niveaux à partir du fichier JSON
            Array<Level> levels = json.fromJson(Array.class, Level.class, Gdx.files.internal(filePath));

            // Lier les niveaux entre eux pour former une chaîne de niveaux
            for (int i = 0; i < levels.size - 1; i++) {
                levels.get(i).setNext(levels.get(i + 1));
            }

            return levels;

        } catch (Exception e) {
            Gdx.app.error("JsonLoader", "Erreur lors du chargement des niveaux depuis " + filePath, e);
            return new Array<>(); // Retourne une liste vide en cas d'erreur
        }
    }
}
