package com.mygame.validation;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class LayersExistValidator extends AbstractValidator<TiledMap> {
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
