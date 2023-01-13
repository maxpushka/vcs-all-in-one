package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.svnBuilder;

public class SvnCopy implements Callable<ArrayList<String>> {
    private final String trunk;
    private final String new_branch;
    private final String tag;

    public SvnCopy(String trunk, String new_branch) {
        this.trunk = trunk;
        this.new_branch = new_branch;
        this.tag = "";
    }

    public SvnCopy(String trunk, String new_branch, String tag) {
        this.trunk = trunk;
        this.new_branch = new_branch;
        this.tag = tag;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = svnBuilder().addArguments(
                new CommandArg("copy"),
                new CommandArg(trunk),
                new CommandArg(new_branch));
        if (!tag.isEmpty()) {
            builder.addArguments(new CommandArg(tag));
        }

        return new CommandLine(builder).call();
    }
}
