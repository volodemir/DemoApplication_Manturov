package ru.manturov.terminal;

import lombok.Getter;

public enum MainMenu {
    CREATE_ACCOUNT(1),
    DELETE_ACCOUNT(2),
    SHOW_ACCOUNTS(3),
    CREATE_CATEGORY(4),
    SHOW_REPORT(5),
    ADD_TRANSATION(6);


    @Getter
    private final int numOfOperation;

    MainMenu(int n) {
        this.numOfOperation = n;
    }
}
