package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.vcs.VCS;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "commit", description = "Commits the changes to the repository")
public class Commit implements Callable<Integer> {
    @CommandLine.Parameters(arity = "1..*", description = "commit message")
    String commitMsg;

    @Override
    public Integer call() throws Exception {
        VCS vcs;
        try {
            vcs = new VCSFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        var commitMsg = vcs.commit(this.commitMsg);
        if (commitMsg != null) {
            System.out.println(commitMsg);
        }

        return 0;
    }
}
