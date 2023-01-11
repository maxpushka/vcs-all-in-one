package com.github.maxpushka.vcs_all_in_one.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.util.concurrent.Callable;

@Command(name = "vcs",
        mixinStandardHelpOptions = true,
        version = "VCS all-in-one 0.1.0",
        subcommands = {
                Commit.class,
                Repositories.class
        })
public class RootCommand implements Callable<Integer> {
    @Spec
    CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        this.spec.commandLine().usage(System.out);
        return 0;
    }
}