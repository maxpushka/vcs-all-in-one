package com.github.maxpushka.vcs_all_in_one.shell;

public class CommandArg {
    private final Boolean isSingleParam;
    private final String splitter;

    private final String first;
    private final String second;

    public CommandArg(String param) {
        this.isSingleParam = true;
        this.first = param;
        this.second = null;
        this.splitter = null;
    }

    public CommandArg(String optionName, String optionValue) {
        this(optionName, optionValue, "=");
    }

    public CommandArg(String optionName, String optionValue, String splitter) {
        this.isSingleParam = false;
        this.first = optionName;
        this.second = optionValue;
        this.splitter = splitter;
    }

    @Override
    public String toString() {
        if (isSingleParam) return first;
        return first + splitter + second;
    }
}
