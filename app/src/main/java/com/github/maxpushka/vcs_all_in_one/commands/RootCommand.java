package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.commands.branch.Branch;
import com.github.maxpushka.vcs_all_in_one.commands.repos.Repos;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.util.concurrent.Callable;

@Command(name = "vcs",
        mixinStandardHelpOptions = true,
        version = "VCS all-in-one 0.1.0",
        subcommands = {
                Repos.class,
                Clone.class,
                Status.class,
                Stage.class,
                Commit.class,
                Diff.class,
                Fetch.class,
                Push.class,
                Log.class,
                Branch.class,
                Tag.class,
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