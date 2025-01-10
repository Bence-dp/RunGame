package com.mygame.validation;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * La classe {@code LayersExistValidator} est un validateur qui vérifie la présence des layers nécessaires dans une carte {@code TiledMap}.
 * <p>
 * Ce validateur s'assure que les layers suivants existent dans la carte : "entity", "teleporter" et "obstacles". Si l'un de ces layers
 * est manquant, la validation échoue et la carte est considérée comme invalide.
 * </p>
 *
 * <p>
 * Si un validateur suivant est présent dans la chaîne de responsabilité, il sera également exécuté pour valider la carte. La validation
 * échoue si l'un des validateurs échoue.
 * </p>
 *
 * @see AbstractValidator
 * @see TiledMap
 */
public class LayersExistValidator extends AbstractValidator<TiledMap> {

    /**
     * Valide la carte {@code TiledMap} en vérifiant la présence des layers "entity", "teleporter" et "obstacles".
     *
     * <p>
     * Cette méthode vérifie si les layers requis existent dans la carte. Si l'un des layers est manquant, la validation échoue
     * et la méthode retourne {@code false}.
     * </p>
     *
     * @param map La carte {@code TiledMap} à valider.
     * @return {@code true} si tous les layers nécessaires existent, {@code false} sinon.
     */
    @Override
    public boolean validate(TiledMap map) {
        // Vérification de la présence des layers nécessaires
        if (map.getLayers().get("entity") == null ||
            map.getLayers().get("teleporter") == null ||
            map.getLayers().get("obstacles") == null) {
            return false;  // La carte est invalide
        }

        // Si un validateur suivant existe, on le vérifie aussi
        if (next != null && !next.validate(map)) {
            return false;  // Si l'un des validateurs suivants échoue, la carte est invalide
        }

        return true;  // La carte est valide
    }
}
