package com.github.maxpushka.vcs_all_in_one.shell;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    private final ArrayList<CommandArg> command;

    public CommandBuilder() {
        this.command = new ArrayList<>();
    }

    public CommandBuilder addArguments(CommandArg... args) {
        // TODO: filter out null values
        this.command.addAll(List.of(args));
        return this;
    }

    public String[] compile() {
        return this.command.stream()
                .map(CommandArg::toString)
                .toArray(String[]::new);
    }

    @Override
    public String toString() {
        return String.join(" ", compile());
    }
}
