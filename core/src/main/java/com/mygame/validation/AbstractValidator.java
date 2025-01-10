package com.mygame.validation;

/**
 * Classe abstraite qui implémente le pattern de validation en chaîne.
 * Elle définit la structure pour valider un objet de type générique T et permet de chaîner plusieurs validateurs.
 *
 * @param <T> Le type de l'objet à valider.
 */
public abstract class AbstractValidator<T> implements Validator<T> {
    protected AbstractValidator<T> next;

    /**
     * Définit le prochain validateur dans la chaîne de validation.
     *
     * @param next Le validateur suivant.
     * @return Le validateur suivant, permettant de chaîner les validateurs.
     */
    public AbstractValidator<T> setNext(AbstractValidator<T> next) {
        this.next = next;
        return next;
    }

    /**
     * Méthode abstraite qui valide un objet de type T.
     * Cette méthode doit être implémentée par les classes concrètes pour effectuer la validation spécifique.
     *
     * @param input L'objet à valider.
     * @return true si l'objet est valide, false sinon.
     */
    public abstract boolean validate(T input);
}
