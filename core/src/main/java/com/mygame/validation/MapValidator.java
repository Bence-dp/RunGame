package com.mygame.validation;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class MapValidator extends AbstractValidator<TiledMap> {
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
