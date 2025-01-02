package com.mygame.utils.saver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * La classe {@code LoadManager} est responsable du chargement des données de sauvegarde du jeu.
 * Elle lit les informations de sauvegarde à partir d'un fichier binaire et les transforme en un objet {@link SaveData}.
 * <p>
 * Cette classe offre une méthode statique pour charger les données de sauvegarde à partir du fichier de sauvegarde.
 * </p>
 */
public class LoadManager {

    private static final String SAVE_FILE = "savegame.ser";  // Nom du fichier de sauvegarde

    /**
     * Charge les données de sauvegarde depuis le fichier de sauvegarde.
     * <p>
     * Si le fichier de sauvegarde est trouvé et est valide, les données seront chargées et renvoyées sous forme d'un objet {@link SaveData}.
     * Si une erreur se produit (par exemple, fichier manquant ou corrompu), les valeurs par défaut sont retournées.
     * </p>
     *
     * @return Un objet {@link SaveData} contenant les informations du jeu (niveau, pièces, etc.).
     */
    public static SaveData loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            SaveData data = (SaveData) ois.readObject();
            System.out.println("Jeu chargé avec succès : " + data);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Échec du chargement du jeu : " + e.getMessage());
            return new SaveData(null, 0);  // Valeurs par défaut (niveau 1, 0 pièces)
        }
    }
}
