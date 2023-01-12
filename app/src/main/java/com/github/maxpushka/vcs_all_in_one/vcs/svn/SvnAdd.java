package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.svnBuilder;

public class SvnAdd implements Callable<ArrayList<String>> {
    private final ArrayList<String> files;

    public SvnAdd(ArrayList<String> files) {
        this.files = files;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = svnBuilder().addArguments(new CommandArg("add"));
        this.files.stream().map(CommandArg::new).forEach(builder::addArguments);

        return new CommandLine(builder).call();
    }
}
