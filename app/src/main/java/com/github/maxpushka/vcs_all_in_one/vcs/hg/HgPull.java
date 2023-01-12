package com.github.maxpushka.vcs_all_in_one.vcs.hg;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg.hgBuilder;

public class HgPull implements Callable<ArrayList<String>> {
    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = hgBuilder().addArguments(
                new CommandArg("pull"),
                new CommandArg("-u"));
        return new CommandLine(builder).call();
    }
}
