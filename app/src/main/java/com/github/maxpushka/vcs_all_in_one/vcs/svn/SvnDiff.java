package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.svnBuilder;

public class SvnDiff implements Callable<ArrayList<String>> {
    private final String revision1;
    private final String revision2;
    private final boolean isSingleRev;

    public SvnDiff(String commit1, String commit2) {
        this.revision1 = commit1;
        this.revision2 = commit2;

        this.isSingleRev = false;
    }

    public SvnDiff(String commit) {
        this.revision1 = commit;
        this.revision2 = null;

        this.isSingleRev = true;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = svnBuilder().addArguments(
                new CommandArg("diff"),
                new CommandArg(revision1));
        if (!isSingleRev) {
            builder.addArguments(new CommandArg(revision2));
        }

        return new CommandLine(builder).call();
    }
}
