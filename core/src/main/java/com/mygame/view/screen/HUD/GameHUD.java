package com.mygame.view.screen.HUD;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.mygame.model.LevelLoader;
import com.mygame.model.entities.Player;

/**
 * La classe {@code GameHUD} est responsable de l'affichage de l'interface utilisateur
 * (HUD) dans le jeu, incluant des informations telles que le temps écoulé, le nombre de pièces collectées
 * et le nom du niveau. Elle gère également la mise à jour des éléments de l'interface et leur rendu à l'écran.
 * <p>
 * Le HUD se compose de plusieurs éléments graphiques tels que des labels pour le temps et les pièces,
 * ainsi que des images pour les icônes comme celle des pièces collectées.
 * </p>
 */
public class GameHUD {

    private Stage stage;                      // Stage qui contient les éléments du HUD
    private LevelLoader levelLoader;          // Gestionnaire de niveaux, utilisé pour récupérer des informations sur le niveau actuel
    private Player player;                    // Le joueur, utilisé pour obtenir les informations de score
    private BitmapFont font;                  // Police standard utilisée pour afficher les informations
    private BitmapFont largeFont;             // Police plus grande, utilisée pour le nom du niveau
    private Skin skin;                        // Skin utilisé pour personnaliser l'apparence des éléments UI

    private Label timeLabel;                  // Label affichant le temps écoulé
    private Label coinsLabel;                 // Label affichant le nombre de pièces collectées
    private Label levelNameLabel;             // Label affichant le nom du niveau
    private Image coinImage;                  // Image affichant l'icône de la pièce
    private float elapsedTime;                // Temps écoulé depuis le début du niveau
    private float levelNameTimer;             // Timer pour la durée d'affichage du nom du niveau
    private boolean showLevelName;            // Indicateur pour afficher ou masquer le nom du niveau
    private int coinsCollected;               // Nombre de pièces collectées par le joueur

    /**
     * Constructeur de la classe {@code GameHUD}.
     *
     * Initialise le stage, les polices, les labels, et les éléments visuels du HUD.
     * Cette méthode configure également l'affichage du nom du niveau pendant un certain
     * laps de temps au début du niveau.
     *
     * @param levelLoader Le gestionnaire de niveaux, utilisé pour récupérer les informations de niveau.
     */
    public GameHUD(LevelLoader levelLoader) {
        this.levelLoader = levelLoader;
        this.player = levelLoader.getEntityFactory().getPlayer(); // Récupérer le joueur à partir de l'EntityFactory
        this.coinsCollected = 0; // Initialiser le compteur de pièces
        this.elapsedTime = 0; // Initialiser le temps à 0
        this.levelNameTimer = 3; // Durée d'affichage du nom du niveau
        this.showLevelName = true; // Commence avec le nom du niveau visible

        // Initialisation du Stage
        stage = new Stage();

        // Initialisation des polices
        font = new BitmapFont(); // Police standard
        largeFont = new BitmapFont(); // Police pour le niveau
        largeFont.getData().setScale(2.5f); // Agrandir la taille de la police

        // Créer un Skin de manière programmatique
        skin = new Skin();
        skin.add("default-font", font);
        skin.add("large-font", largeFont);
        skin.add("white", new Color(1, 1, 1, 1));
        skin.add("yellow", new Color(1, 1, 0, 1));

        // Styles de labels
        Label.LabelStyle timeLabelStyle = new Label.LabelStyle(font, skin.get("white", Color.class));
        Label.LabelStyle coinsLabelStyle = new Label.LabelStyle(font, skin.get("yellow", Color.class));
        Label.LabelStyle levelNameStyle = new Label.LabelStyle(largeFont, skin.get("white", Color.class));

        // Labels pour le HUD
        timeLabel = new Label("Time: 00:00", timeLabelStyle);
        coinsLabel = new Label("0", coinsLabelStyle);
        levelNameLabel = new Label(levelLoader.getLevel().getName(), levelNameStyle); // Récupérer le nom du niveau

        coinImage = new Image(new Texture("Entities/coin.png"));
        coinImage.setScale(0.05f);

        // Positionnement
        timeLabel.setPosition(Gdx.graphics.getWidth() / 2 - timeLabel.getWidth() / 2, Gdx.graphics.getHeight() - 50);

        coinImage.setPosition(10, Gdx.graphics.getHeight() - 100);
        coinsLabel.setPosition(coinImage.getX() + coinImage.getWidth() /10f, Gdx.graphics.getHeight() - 95);

        levelNameLabel.setPosition(
            Gdx.graphics.getWidth() / 2 - levelNameLabel.getWidth() / 2,
            Gdx.graphics.getHeight() / 2
        ); // Centrer le label pour le nom du niveau

        // Ajouter les éléments au stage
        stage.addActor(timeLabel);
        stage.addActor(coinsLabel);
        stage.addActor(coinImage);
        stage.addActor(levelNameLabel);
    }

    /**
     * Met à jour les informations du HUD à chaque frame.
     * Cela inclut la mise à jour du temps écoulé, du nombre de pièces collectées,
     * et la gestion de l'affichage du nom du niveau.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour.
     */
    public void update(float deltaTime) {
        elapsedTime += deltaTime;

        // Mettre à jour le compteur de temps
        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);
        timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));

        // Mettre à jour le nombre de pièces collectées
        coinsCollected = player.getScore();
        coinsLabel.setText(String.valueOf(coinsCollected));

        // Gérer l'affichage du nom du niveau
        if (showLevelName) {
            levelNameTimer -= deltaTime;
            if (levelNameTimer <= 0) {
                levelNameLabel.setVisible(false); // Masquer le label après 3 secondes
                showLevelName = false; // Désactiver le timer pour éviter une mise à jour continue
            }
        }
    }

    /**
     * Rendu du HUD à l'écran.
     * Cette méthode est appelée à chaque frame pour dessiner tous les éléments
     * du HUD sur l'écran.
     *
     * @param batch SpriteBatch utilisé pour dessiner les éléments du stage.
     */
    public void render(SpriteBatch batch) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Mise à jour des actions du stage
        stage.draw(); // Dessiner les éléments du stage
    }

    /**
     * Retourne le stage associé à ce HUD.
     * Le stage contient tous les éléments UI et leur logique d'affichage.
     *
     * @return Le {@code Stage} du HUD.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Libère les ressources utilisées par le HUD, notamment les polices et le stage.
     */
    public void dispose() {
        stage.dispose();
        font.dispose();
        largeFont.dispose(); // Dispose de la police agrandie
        skin.dispose();
    }
}
