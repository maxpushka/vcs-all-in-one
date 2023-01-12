package com.github.maxpushka.vcs_all_in_one.vcs;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;

public final class GitAdapter extends VCS {
    private final VCSLogParser parser;

    public GitAdapter() {
        this.parser = new VCSLogParser() {
            @Override
            public ArrayList<VCSCommit> parse(ArrayList<String> rawLog) {
                return VCSLogParser.super.parse(rawLog);
            }
        };
    }

    private CommandBuilder gitBuilder() {
        return new CommandBuilder().addArguments(new CommandArg("git"));
    }

    @Override
    public VCSCommit commit(String commitMsg) throws Exception {
        // check if anything is staged
        CommandBuilder statusBuilder = gitBuilder()
                .addArguments(new CommandArg("status"), new CommandArg("--porcelain", "v1"));
        var status = new CommandLine(statusBuilder).call();
        if (status.isEmpty()) {
            System.out.println("Nothing to commit.");
            return null;
        }

        // run commit command
        CommandBuilder commitBuilder = gitBuilder()
                .addArguments(new CommandArg("commit"), new CommandArg("-m", commitMsg, " "));
        new CommandLine(commitBuilder).call();

        // get info about the commit created
        return customLog(this.parser,
                new CommandArg("--pretty", "format:\"%h | %s\""),
                new CommandArg("-1")
        ).get(0);
    }

    @Override
    public ArrayList<String> update() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> push() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> pull() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> fetch() throws Exception {
        CommandBuilder builder = gitBuilder().addArguments(
                new CommandArg("fetch"),
                new CommandArg("--all"),
                new CommandArg("--prune"),
                new CommandArg("--jobs", "10"));
        return new CommandLine(builder).call();
    }

    @Override
    public ArrayList<String> list() throws Exception {
        return null;
    }

    @Override
    public ArrayList<VCSCommit> log() throws Exception {
        return customLog(this.parser, new CommandArg("--pretty", "format:\"%h | %s\""));
    }

    private ArrayList<VCSCommit> customLog(VCSLogParser parser, CommandArg... customOpts) throws Exception {
        CommandBuilder builder = gitBuilder()
                .addArguments(new CommandArg("log"))
                .addArguments(customOpts);
        var rawLog = new CommandLine(builder).call();
        return parser.parse(rawLog);
    }

    @Override
    public ArrayList<String> patch() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> branch(String targetBranch) throws Exception {
        CommandBuilder builder = gitBuilder().addArguments(
                new CommandArg("branch"),
                new CommandArg(targetBranch));
        return new CommandLine(builder).call();
    }

    @Override
    public ArrayList<String> merge(String targetBranch) throws Exception {
        CommandBuilder builder = gitBuilder().addArguments(
                new CommandArg("merge"),
                new CommandArg(targetBranch));
        return new CommandLine(builder).call();
    }

    @Override
    public ArrayList<String> tag(String newTag) throws Exception {
        CommandBuilder builder = gitBuilder().addArguments(
                new CommandArg("tag"),
                new CommandArg(newTag));
        return new CommandLine(builder).call();
    }
}
