package ru.manturov.exception;

public class NoEntityException extends RuntimeException {

    public NoEntityException(Long id) {
        super(String.valueOf(id));
    }

    public NoEntityException(String s) {
        super(String.valueOf(s));
    }
}
