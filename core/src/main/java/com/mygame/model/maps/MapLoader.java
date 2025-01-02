package com.mygame.model.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * La classe {@code MapLoader} est responsable du chargement des cartes Tiled (.tmx) dans le jeu.
 * Elle permet de charger une carte Tiled depuis un fichier et de gérer les ressources associées.
 */
public class MapLoader {

    private TiledMap map; // Carte Tiled chargée

    /**
     * Constructeur de la classe {@code MapLoader}.
     * Initialise un chargeur de carte sans carte chargée au départ.
     */
    public MapLoader() {
        // Constructeur simple
    }

    /**
     * Charge une carte Tiled depuis un fichier .tmx.
     * Cette méthode utilise le chargeur de cartes Tiled de LibGDX pour charger la carte à partir du fichier spécifié.
     *
     * @param mapFile Le chemin du fichier .tmx à charger.
     * @return La carte Tiled chargée.
     */
    public TiledMap loadMap(String mapFile) {
        map = new TmxMapLoader().load(mapFile); // Charge la carte
        return map; // Retourne la carte chargée
    }

    /**
     * Libère les ressources associées à la carte chargée.
     * Cette méthode doit être appelée lorsque la carte n'est plus nécessaire pour libérer la mémoire.
     */
    public void dispose() {
        if (map != null) {
            map.dispose(); // Libère les ressources de la carte Tiled
        }
    }
}
