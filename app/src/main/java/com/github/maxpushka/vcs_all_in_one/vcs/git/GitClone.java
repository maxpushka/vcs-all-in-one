package com.github.maxpushka.vcs_all_in_one.vcs.git;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.git.Git.gitBuilder;

class GitClone implements Callable<ArrayList<String>> {
    private final String url;
    private final String path;

    public GitClone(final String url, final String path) {
        this.url = url;
        this.path = path;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = gitBuilder().addArguments(
                new CommandArg("clone"),
                new CommandArg("--recurse-submodules"),
                new CommandArg(url));
        if (path != null) {
            builder.addArguments(new CommandArg(path));
        }

        return new CommandLine(builder).call();
    }
}
