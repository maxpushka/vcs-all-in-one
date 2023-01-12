package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.svnBuilder;

public class SvnClone implements Callable<ArrayList<String>> {
    private final String url;
    private final String dir;

    public SvnClone(String url, String dir) {
        this.url = url;
        this.dir = dir;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = svnBuilder().addArguments(
                new CommandArg("checkout"),
                new CommandArg(url));
        if (dir != null) {
            builder.addArguments(new CommandArg(dir));
        }

        return new CommandLine(builder).call();
    }
}
