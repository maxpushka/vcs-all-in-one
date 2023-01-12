package com.github.maxpushka.vcs_all_in_one.vcs.hg;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg.hgBuilder;

public class HgUpdate implements Callable<ArrayList<String>> {
    private final String branch;

    public HgUpdate() {
        this.branch = "";
    }

    public HgUpdate(String branch) {
        this.branch = branch;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = hgBuilder().addArguments(new CommandArg("update"));
        if (branch != null) {
            builder.addArguments(new CommandArg(branch));
        }

        return new CommandLine(builder).call();
    }
}
