package com.mygame.common;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe SoundFactory est utilisée pour charger et gérer les effets sonores dans le jeu.
 * Elle permet de centraliser le chargement des sons et de les jouer facilement à différents moments.
 * Cette classe est un Singleton afin d'avoir une seule instance pour gérer les sons.
 */
public class SoundFactory {
    // Dictionnaire pour stocker les sons chargés
    private static final Map<String, Sound> soundMap = new HashMap<>();

    // Instance unique de SoundFactory
    private static SoundFactory instance = null;

    // Constructeur privé pour empêcher l'instanciation directe
    private SoundFactory() {}

    /**
     * Méthode pour obtenir l'instance unique de SoundFactory.
     * Si l'instance n'existe pas, elle est créée.
     *
     * @return L'instance unique de SoundFactory
     */
    public static SoundFactory getInstance() {
        if (instance == null) {
            instance = new SoundFactory();
        }
        return instance;
    }

    /**
     * Charge un son à partir d'un fichier.
     * Si le son a déjà été chargé, il ne sera pas rechargé.
     *
     * @param soundName Le nom du fichier sonore (sans extension).
     */
    public void loadSound(String soundName) {
        if (!soundMap.containsKey(soundName)) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + soundName + ".mp3"));
            soundMap.put(soundName, sound);
        }
    }

    /**
     * Joue un son.
     * Le son doit d'abord être chargé via la méthode loadSound().
     *
     * @param soundName Le nom du fichier sonore (sans extension).
     */
    public void playSound(String soundName) {
        if (soundMap.containsKey(soundName)) {
            soundMap.get(soundName).play();
        } else {
            System.err.println("Le son " + soundName + " n'a pas été chargé !");
        }
    }

    /**
     * Libère les ressources associées aux sons chargés.
     * Cette méthode doit être appelée lorsque le jeu ou la scène est terminée.
     */
    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        soundMap.clear();
    }
}
