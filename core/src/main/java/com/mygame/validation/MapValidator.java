package com.mygame.validation;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * La classe {@code MapValidator} est responsable de la validation d'une carte TiledMap.
 * Elle vérifie si la carte respecte un ensemble de règles définies à travers plusieurs validateurs.
 * Ces règles peuvent inclure la vérification de la présence de certaines couches, l'existence d'une sortie,
 * ou encore la validation du nombre de joueurs autorisés.
 * <p>
 * La validation est effectuée par une chaîne de responsabilités de validateurs, où chaque validateur
 * vérifie une règle spécifique et passe au suivant si la règle est respectée.
 * </p>
 */
public class MapValidator extends AbstractValidator<TiledMap> {

    /**
     * Méthode principale qui valide la carte en chaîne à l'aide des validateurs spécifiques.
     *
     * <p>
     * Cette méthode crée des instances des validateurs nécessaires et les chaîne entre eux
     * pour vérifier si la carte respecte toutes les règles définies. Si un validateur échoue,
     * la validation s'arrête et retourne {@code false}. Si tous les validateurs passent,
     * la méthode retourne {@code true}.
     * </p>
     *
     * @param map La carte à valider, représentée sous forme d'un objet {@link TiledMap}.
     * @return {@code true} si tous les validateurs passent et que la carte est valide,
     *         {@code false} sinon.
     */
    @Override
    public boolean validate(TiledMap map) {
        // Création des validateurs
        LayersExistValidator layersValidator = new LayersExistValidator();
        ExitExistsValidator exitValidator = new ExitExistsValidator();
        SinglePlayerValidator singlePlayerValidator = new SinglePlayerValidator();

        // Chaînage des validateurs
        layersValidator.setNext(exitValidator);
        exitValidator.setNext(singlePlayerValidator);

        // Validation des validateurs
        return layersValidator.validate(map);  // Retourne true si tous les validateurs réussissent
    }
}
