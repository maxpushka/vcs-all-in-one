package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCS;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "commit", description = "Commits the changes to the repository")
public class Commit implements Callable<Integer> {
    @Parameters(arity = "0..*", description = "commit message")
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
