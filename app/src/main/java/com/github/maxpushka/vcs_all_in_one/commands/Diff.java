package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "diff", description = "Show changes between commits, commit and working tree, etc")
class Diff implements Callable<Integer> {
    @Parameters(index = "0", description = "blob hash")
    String blob1;

    @Parameters(index = "1", arity = "0..1", description = "blob hash (optional)")
    String blob2;

    @Override
    public Integer call() throws Exception {
        VCSFacade vcs;
        try {
            vcs = new VCSFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        ArrayList<String> output;
        if (blob2 != null) {
            output = vcs.diff(blob1, blob2);
        } else {
            output = vcs.diff(blob1);
        }

        for (var line : output) {
            Out.log(line);
        }
        return 0;
    }
}
