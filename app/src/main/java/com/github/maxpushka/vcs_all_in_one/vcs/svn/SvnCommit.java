package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;

import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.svnBuilder;
import static com.github.maxpushka.vcs_all_in_one.vcs.svn.SvnLog.customLog;

class SvnCommit implements Callable<VCSCommit> {
    private final String message;

    public SvnCommit(String message) {
        this.message = message;
    }

    @Override
    public VCSCommit call() throws Exception {
        // check if anything is staged
        CommandBuilder statusBuilder = svnBuilder()
                .addArguments(new CommandArg("status"));
        var status = new CommandLine(statusBuilder).call();
        if (status.isEmpty()) {
            Out.log("Nothing to commit.");
            return null;
        }

        // run commit command
        CommandBuilder commitBuilder = svnBuilder()
                .addArguments(new CommandArg("commit"), new CommandArg("-m", "\"" + message + "\"", " "));
        new CommandLine(commitBuilder).call();

        // get info about the commit created
        return customLog(new CommandArg("--limit", "1")).get(0);
    }
}
