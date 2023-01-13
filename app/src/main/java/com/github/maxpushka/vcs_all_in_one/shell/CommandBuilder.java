package com.github.maxpushka.vcs_all_in_one.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandBuilder {
    private final ArrayList<CommandArg> command;

    public CommandBuilder() {
        this.command = new ArrayList<>();
    }

    public CommandBuilder addArguments(CommandArg... args) {
        List<CommandArg> filteredArgs = Arrays.stream(args).filter(Objects::isNull).toList();
        this.command.addAll(filteredArgs);
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
