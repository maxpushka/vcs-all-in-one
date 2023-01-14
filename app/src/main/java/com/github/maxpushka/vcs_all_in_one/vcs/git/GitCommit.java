package com.github.maxpushka.vcs_all_in_one.vcs.git;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;

import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.git.Git.gitBuilder;
import static com.github.maxpushka.vcs_all_in_one.vcs.git.GitLog.customLog;

class GitCommit implements Callable<VCSCommit> {
    private final String message;

    public GitCommit(String message) {
        this.message = message;
    }

    @Override
    public VCSCommit call() throws Exception {
        // check if anything is staged
        CommandBuilder statusBuilder = gitBuilder()
                .addArguments(new CommandArg("status"), new CommandArg("--porcelain", "v1"));
        var status = new CommandLine(statusBuilder).call();
        if (status.isEmpty()) {
            Out.log("Nothing to commit.");
            return null;
        }

        // run commit command
        CommandBuilder commitBuilder = gitBuilder()
                .addArguments(new CommandArg("commit"), new CommandArg("-m", "\"" + message + "\"", " "));
        new CommandLine(commitBuilder).call();

        // get info about the commit created
        return customLog(
                new CommandArg("--pretty", "format:\"%h | %s\""),
                new CommandArg("-1")
        ).get(0);
    }
}
