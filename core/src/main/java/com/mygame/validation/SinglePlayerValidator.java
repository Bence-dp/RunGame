package com.mygame.validation;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class SinglePlayerValidator extends AbstractValidator<TiledMap> {
    @Override
    public boolean validate(TiledMap map) {
        // Récupérer le layer "entity"
        MapLayer entityLayer = map.getLayers().get("entity");
        if (entityLayer == null || entityLayer.getObjects().getCount() == 0) {
            return false;  // Layer "entity" manquant ou vide
        }

        // Compter les objets avec la propriété "name" égale à "player"
        int playerCount = 0;
        for (MapObject object : entityLayer.getObjects()) {
            // Vérifier si l'objet contient une propriété "name"
            String name = object.getProperties().get("type", String.class);
            if ("player".equals(name)) {
                playerCount++;
            }
        }

        // Valider qu'il y a exactement un "player"
        if (playerCount != 1) {
            return false;  // Il doit y avoir exactement un objet "player"
        }

        // Si un validateur suivant existe, le valider également
        if (next != null && !next.validate(map)) {
            return false;  // Si l'un des validateurs échoue, la carte est invalide
        }

        return true;  // La validation a réussi
    }
}
