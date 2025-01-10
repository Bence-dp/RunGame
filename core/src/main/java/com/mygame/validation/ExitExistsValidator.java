package com.mygame.validation;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class ExitExistsValidator extends AbstractValidator<TiledMap> {
    @Override
    public boolean validate(TiledMap map) {
        MapLayer exitLayer = map.getLayers().get("teleporter");
        if (exitLayer == null || exitLayer.getObjects().getCount() == 0) {
            return false;  // La carte est invalide si le layer "exit" est manquant ou vide
        }

        // Si un validateur suivant existe, on le vérifie aussi
        if (next != null && !next.validate(map)) {
            return false;  // Si l'un des validateurs suivants échoue, la carte est invalide
        }

        return true;  // La carte est valide
    }

}
