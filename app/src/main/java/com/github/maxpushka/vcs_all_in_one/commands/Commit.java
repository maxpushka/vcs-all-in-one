package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "commit", description = "Record changes to the repository")
class Commit implements Callable<Integer> {
    @Parameters(arity = "1..*", description = "commit message")
    String msg;

    @Override
    public Integer call() throws Exception {
        VCSFacade vcs;
        try {
            vcs = new VCSFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        VCSCommit commitMsg = vcs.commit(this.msg);
        if (commitMsg != null) {
            Out.log(commitMsg);
        }
        return 0;
    }
}
