package com.github.maxpushka.vcs_all_in_one.vcs.git;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.git.Git.gitBuilder;

class GitLog implements Callable<ArrayList<VCSCommit>> {
    static ArrayList<VCSCommit> customLog(CommandArg... customOpts) throws Exception {
        CommandBuilder builder = gitBuilder()
                .addArguments(new CommandArg("log"))
                .addArguments(customOpts);
        var rawLog = new CommandLine(builder).call();
        return Git.logParser.parse(rawLog);
    }

    @Override
    public ArrayList<VCSCommit> call() throws Exception {
        return customLog(new CommandArg("--pretty", "format:\"%h | %s\""));
    }
}
