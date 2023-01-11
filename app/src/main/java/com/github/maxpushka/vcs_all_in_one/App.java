package com.github.maxpushka.vcs_all_in_one;

import com.github.maxpushka.vcs_all_in_one.commands.RootCommand;
import picocli.CommandLine;

public class App {
    public static void main(String[] args) {
        var root = new CommandLine(new RootCommand());
        System.exit(root.execute(args));
    }
}