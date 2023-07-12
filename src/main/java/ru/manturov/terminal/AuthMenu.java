package ru.manturov.terminal;

import lombok.Getter;

public enum AuthMenu {
    AUTHORIZATION(1),
    REGISTRATION(2);

    @Getter
    private final int numOfOperation;

    AuthMenu(int n) {
        this.numOfOperation = n;
    }
}
