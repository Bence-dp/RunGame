package com.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;
import com.mygame.controller.GameManager;
import com.mygame.model.maps.Level;
import com.mygame.utils.JsonLoader;
import com.mygame.utils.saver.SaveData;
import com.mygame.utils.saver.SaveManager;
import com.mygame.view.screen.BadMapScreen;
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
        levels = JsonLoader.loadLevels("levels.json");

        // Initialiser le GameManager avec les niveaux chargés et le premier niveau
        if (!levels.isEmpty()) {
            gameManager = GameManager.getInstance(this,levels ,levels.get(0));
            // Lancer l'écran de démarrage
            setScreen(new FirstScreen(gameManager));

        }
        else {
            setScreen(new BadMapScreen(gameManager));
        }

    }



    /**
     * Méthode appelée lors de la fermeture du jeu.
     * Sauvegarde les données du jeu (niveau actuel et nombre de pièces).
     */
    @Override
    public void dispose() {
        // Sauvegarder les données du jeu avant la fermeture
        if (gameManager != null) {
            if (gameManager.getNeedSave()) {
                SaveData saveData = new SaveData(gameManager.getCurrentLevel(), gameManager.getCoin());
                SaveManager.saveGame(saveData);


                System.out.println("Game saved during shutdown.");
            }
        }
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
