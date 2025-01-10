package com.mygame.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygame.Main;

/**
 * Lance l'application de bureau (LWJGL3). Cette classe est utilisée pour démarrer
 * le jeu sur une plateforme de bureau en utilisant LWJGL3 pour l'interface graphique.
 */
public class Lwjgl3Launcher {

    /**
     * Point d'entrée principal de l'application de bureau.
     * Vérifie si un nouveau JVM doit être démarré pour des raisons de compatibilité (notamment macOS) et lance l'application.
     *
     * @param args Arguments passés au programme depuis la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // Gère le support macOS et aide sous Windows.
        createApplication(); // Crée l'application et démarre l'exécution.
    }

    /**
     * Crée une nouvelle instance de l'application et la lance.
     *
     * @return L'instance de Lwjgl3Application lancée.
     */
    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration()); // Crée et démarre l'application principale avec les configurations par défaut.
    }

    /**
     * Obtient la configuration par défaut pour l'application LWJGL3.
     * Configure le titre de la fenêtre, la synchronisation verticale (Vsync), la fréquence de rafraîchissement,
     * la taille de la fenêtre et l'icône de l'application.
     *
     * @return La configuration par défaut de l'application LWJGL3.
     */
    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("CourseGame"); // Définit le titre de la fenêtre de l'application.

        // Active la synchronisation verticale pour limiter les FPS et éviter le déchirement de l'écran.
        configuration.useVsync(true);

        // Définit le nombre de FPS à la fréquence de rafraîchissement de l'écran actif, avec une légère compensation.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);

        // Définit la taille de la fenêtre en mode fenêtré (640x480 pixels).
        configuration.setWindowedMode(640, 480);

        // Définit les icônes de l'application à partir des fichiers ressources.
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return configuration;
    }
}
