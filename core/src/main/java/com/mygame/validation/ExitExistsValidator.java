package com.mygame.validation;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Un validateur qui vérifie la présence d'un layer "teleporter" dans une carte.
 * Ce layer est supposé représenter la sortie du niveau. Si ce layer est manquant
 * ou vide, la carte est considérée comme invalide.
 */
public class ExitExistsValidator extends AbstractValidator<TiledMap> {

    /**
     * Valide la carte en vérifiant si le layer "teleporter" existe et contient des objets.
     *
     * @param map La carte à valider.
     * @return true si le layer "teleporter" est présent et contient des objets, sinon false.
     */
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
