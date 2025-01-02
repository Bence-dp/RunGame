package com.mygame.utils.saver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * La classe {@code SaveManager} est responsable de la sauvegarde des données de jeu.
 * Elle permet de sérialiser les données de jeu et de les enregistrer dans un fichier.
 * <p>
 * Cette classe utilise un fichier binaire pour stocker l'état du jeu afin que le joueur puisse reprendre la partie plus tard.
 * </p>
 */
public class SaveManager {

    private static final String SAVE_FILE = "savegame.ser";  // Le nom du fichier de sauvegarde

    /**
     * Sauvegarde les données du jeu dans un fichier binaire.
     * La méthode sérialise l'objet {@code SaveData} et l'enregistre dans un fichier.
     *
     * @param data Les données de sauvegarde à enregistrer. Il s'agit d'un objet de type {@link SaveData}.
     */
    public static void saveGame(SaveData data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(data);  // Sérialisation de l'objet de sauvegarde dans le fichier
            System.out.println("Game saved successfully!");  // Message de confirmation
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());  // Gestion des erreurs
        }
    }
}
