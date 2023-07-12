package ru.manturov.terminal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    public final Main main;

    public CommandLineRunnerImpl(Main main) {
        this.main = main;
    }

    @Override
    public void run(String... args) throws Exception {
        main.start();
    }
}
