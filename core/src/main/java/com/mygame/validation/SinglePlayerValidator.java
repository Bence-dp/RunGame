package com.mygame.validation;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * La classe {@code SinglePlayerValidator} est un validateur qui vérifie qu'il y a exactement un objet "player"
 * dans le layer "entity" d'une carte de type {@code TiledMap}.
 * <p>
 * Ce validateur s'assure que le layer "entity" existe et contient au moins un objet ayant une propriété
 * "name" égale à "player". Il valide également que ce layer contient exactement un objet "player".
 * </p>
 *
 * <p>
 * Si un validateur suivant est présent dans la chaîne de responsabilité, il sera également exécuté pour valider
 * la carte. La validation échoue si le nombre d'objets "player" est incorrect ou si le layer "entity" est manquant
 * ou vide.
 * </p>
 *
 * @see AbstractValidator
 * @see TiledMap
 */
public class SinglePlayerValidator extends AbstractValidator<TiledMap> {

    /**
     * Valide la carte {@code TiledMap} en vérifiant qu'il existe un et un seul objet "player" dans le layer "entity".
     *
     * <p>
     * Cette méthode récupère le layer "entity" de la carte, puis parcourt les objets de ce layer pour compter ceux
     * qui ont une propriété "type" égale à "player". Si ce nombre n'est pas exactement 1, la validation échoue.
     * </p>
     *
     * @param map La carte {@code TiledMap} à valider.
     * @return {@code true} si la carte contient exactement un objet "player" dans le layer "entity", {@code false}
     *         sinon.
     */
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
            // Vérifier si l'objet contient une propriété "type"
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
