package com.mygame.validation;

public abstract class AbstractValidator<T> implements Validator<T> {
    protected AbstractValidator<T> next;

    public AbstractValidator<T> setNext(AbstractValidator<T> next) {
        this.next = next;
        return next;
    }

    public abstract boolean validate(T input);
}
