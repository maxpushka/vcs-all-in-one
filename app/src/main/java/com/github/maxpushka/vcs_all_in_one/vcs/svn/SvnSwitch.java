package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.svnBuilder;

public class SvnSwitch implements Callable<ArrayList<String>> {
    private final String branch;

    public SvnSwitch(String branch) {
        this.branch = branch;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = svnBuilder().addArguments(
                new CommandArg("switch"),
                new CommandArg(branch));
        return new CommandLine(builder).call();
    }
}
