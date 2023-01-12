package com.github.maxpushka.vcs_all_in_one.vcs.hg;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg.hgBuilder;

public class HgDiff implements Callable<ArrayList<String>> {
    private final String commit1;
    private final String commit2;
    private final boolean isSingleCommit;

    public HgDiff(String commit1, String commit2) {
        this.commit1 = commit1;
        this.commit2 = commit2;

        this.isSingleCommit = false;
    }

    public HgDiff(String commit) {
        this.commit1 = commit;
        this.commit2 = null;

        this.isSingleCommit = true;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = hgBuilder().addArguments(
                new CommandArg("diff"),
                new CommandArg("-r", commit1));
        if (!isSingleCommit) {
            builder.addArguments(new CommandArg("-r", commit2));
        }

        return new CommandLine(builder).call();
    }
}
