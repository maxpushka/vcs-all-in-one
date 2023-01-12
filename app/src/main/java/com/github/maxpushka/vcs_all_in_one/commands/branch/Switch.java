package com.github.maxpushka.vcs_all_in_one.commands.branch;

import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "switch", description = "Switch branches")
class Switch implements Callable<Integer> {
    @Parameters(description = "branch to switch to")
    String branch;

    @Override
    public Integer call() throws Exception {
        VCSFacade vcs;
        try {
            vcs = new VCSFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        ArrayList<String> output = vcs.switch_branch(branch);
        for (var line : output) {
            Out.log(line);
        }
        return 0;
    }
}
