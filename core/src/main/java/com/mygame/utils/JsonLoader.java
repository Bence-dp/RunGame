package com.mygame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;
import com.mygame.model.maps.Level;
import com.mygame.model.maps.MapLoader;
import com.mygame.validation.*;

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

        // Créer la chaîne de validateurs
        MapValidator validator = new MapValidator();

        MapLoader mapLoader = new MapLoader();
        Array<Level> levels = null;
        Array<Level> validLevels = new Array<>(); // Liste des niveaux valides

        try {
            // Charger les niveaux depuis le fichier JSON
            levels = json.fromJson(Array.class, Level.class, Gdx.files.internal(filePath));

            // Valider les niveaux et ne garder que les valides
            for (int i = 0; i < levels.size; i++) {
                // Charger la carte du niveau
                TiledMap map = mapLoader.loadMap(levels.get(i).getPath());

                // Valider la carte avec la chaîne de validateurs
                if (validator.validate(map)) {
                    validLevels.add(levels.get(i)); // Ajouter le niveau à la liste des niveaux valides
                } else {
                    System.err.println("Niveau " + levels.get(i).getName() + " invalide. Il sera ignoré.");
                }
                map.dispose();
            }

            // Lier les niveaux valides entre eux
            for (int i = 0; i < validLevels.size - 1; i++) {
                validLevels.get(i).setNext(validLevels.get(i + 1)); // Chaîner les niveaux valides
            }

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des niveaux : " + e.getMessage());
            // En cas d'erreur, retourner une liste vide
            validLevels = new Array<>();
        }

        return validLevels; // Retourner la liste des niveaux valides, chaînés entre eux
    }

}
