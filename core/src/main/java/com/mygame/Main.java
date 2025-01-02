package com.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;
import com.mygame.controller.GameManager;
import com.mygame.model.maps.Level;
import com.mygame.utils.saver.SaveData;
import com.mygame.utils.saver.SaveManager;
import com.mygame.view.screen.FirstScreen;

/**
 * Classe principale du jeu qui gère la création et la fermeture du jeu.
 * Cette classe charge les niveaux, crée le gestionnaire de jeu, et lance l'écran initial.
 */
public class Main extends Game {

    private GameManager gameManager;
    private Array<Level> levels;

    /**
     * Méthode appelée lors de la création du jeu.
     * Charge les niveaux à partir du fichier JSON et initialise le gestionnaire de jeu.
     * Ensuite, elle lance l'écran principal du jeu (FirstScreen).
     */
    @Override
    public void create() {
        // Charger les niveaux depuis le fichier JSON
        levels = loadLevels("levels.json");

        // Initialiser le GameManager avec les niveaux chargés et le premier niveau
        gameManager = GameManager.getInstance(this, levels, levels.get(0));

        // Lancer l'écran de démarrage
        setScreen(new FirstScreen(gameManager));
    }

    /**
     * Charge les niveaux à partir d'un fichier JSON.
     *
     * @param path Le chemin du fichier JSON contenant la description des niveaux.
     * @return Une liste de niveaux (Array) chargée à partir du fichier JSON.
     */
    private Array<Level> loadLevels(String path) {
        Json json = new Json();

        // Charger les niveaux depuis le fichier JSON
        Array<Level> levels = json.fromJson(Array.class, Level.class, Gdx.files.internal(path));

        // Lier les niveaux entre eux (chaînage des niveaux)
        for (int i = 0; i < levels.size - 1; i++) {
            levels.get(i).setNext(levels.get(i + 1));  // Chaque niveau a un niveau suivant
        }

        return levels;
    }

    /**
     * Méthode appelée lors de la fermeture du jeu.
     * Sauvegarde les données du jeu (niveau actuel et nombre de pièces).
     */
    @Override
    public void dispose() {
        // Sauvegarder les données du jeu avant la fermeture
        SaveData saveData = new SaveData(gameManager.getCurrentLevel(), gameManager.getCoin());
        SaveManager.saveGame(saveData);

        System.out.println("Game saved during shutdown.");

        // Appeler dispose() sur les autres ressources
        super.dispose();
    }

    /**
     * Obtient la liste des niveaux du jeu.
     *
     * @return La liste des niveaux.
     */
    public Array<Level> getLevels() {
        return levels;
    }
}
