package com.github.maxpushka.vcs_all_in_one.vcs.git;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.git.Git.gitBuilder;

class GitAdd implements Callable<ArrayList<String>> {
    private final ArrayList<String> files;

    public GitAdd(ArrayList<String> files) {
        this.files = files;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = gitBuilder().addArguments(new CommandArg("add"));
        this.files.stream().map(CommandArg::new).forEach(builder::addArguments);

        return new CommandLine(builder).call();
    }
}
