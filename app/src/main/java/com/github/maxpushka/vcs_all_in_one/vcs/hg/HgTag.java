package com.github.maxpushka.vcs_all_in_one.vcs.hg;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg.hgBuilder;

public class HgTag implements Callable<ArrayList<String>> {
    private final String tagName;

    public HgTag(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        CommandBuilder builder = hgBuilder().addArguments(
                new CommandArg("tag"),
                new CommandArg(tagName));
        return new CommandLine(builder).call();
    }
}
