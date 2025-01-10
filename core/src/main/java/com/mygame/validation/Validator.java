package com.mygame.validation;

public interface Validator<T> {
    boolean validate(T object); // Méthode qui valide l'objet de type T
}
