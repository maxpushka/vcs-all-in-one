package com.github.maxpushka.vcs_all_in_one.commands.branch;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.util.concurrent.Callable;

@Command(name = "branch",
        description = "Performs operations with branches",
        subcommands = {
                Switch.class,
                Create.class,
                Merge.class,
        })
public class Branch implements Callable<Integer> {
    @Spec
    CommandSpec spec;

    @Override
    public Integer call() {
        this.spec.commandLine().usage(System.out);
        return null;
    }
}
