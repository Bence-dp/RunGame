package com.mygame.validation;

/**
 * L'interface {@code Validator} définit un contrat pour les classes de validation.
 * Elle permet de valider un objet générique de type {@code T} en appliquant une règle spécifique.
 *
 * <p>
 * Cette interface peut être implémentée par différentes classes qui valident des objets spécifiques,
 * comme des cartes, des entités, ou d'autres objets du jeu.
 * </p>
 *
 * @param <T> Le type de l'objet à valider (par exemple, une carte, un personnage, etc.)
 */
public interface Validator<T> {

    /**
     * Valide un objet du type {@code T} selon des critères définis dans l'implémentation spécifique.
     *
     * <p>
     * Cette méthode est utilisée pour vérifier si l'objet respecte les règles ou conditions définies
     * dans la logique de validation de la classe implémentant cette interface.
     * </p>
     *
     * @param object L'objet à valider.
     * @return {@code true} si l'objet respecte les règles de validation, {@code false} sinon.
     */
    boolean validate(T object); // Méthode qui valide l'objet de type T
}
