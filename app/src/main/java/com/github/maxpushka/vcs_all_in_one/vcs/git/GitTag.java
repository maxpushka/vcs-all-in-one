package com.github.maxpushka.vcs_all_in_one.vcs.git;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.git.Git.gitBuilder;

public class GitTag implements Callable<ArrayList<String>> {
    private final String tag;

    public GitTag(String tag) {
        this.tag = tag;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = gitBuilder().addArguments(
                new CommandArg("tag"),
                new CommandArg(tag));
        return new CommandLine(builder).call();
    }
}
