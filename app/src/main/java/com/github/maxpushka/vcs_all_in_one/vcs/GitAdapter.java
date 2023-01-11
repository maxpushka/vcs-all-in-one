package com.github.maxpushka.vcs_all_in_one.vcs;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

interface GitLogParser {
    ArrayList<VCSCommit> parse(ArrayList<String> rawLog);
}

public final class GitAdapter extends VCS {
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
        var customArg = new CommandArg("--pretty", "format:\"%h | %s\"");
        GitLogParser parser = (rawLog) -> {
            // here you will get a message like "e29d781 | Initial commit"
            var ch = new StringBuilder(rawLog.get(0));

            // delete leading and trailing quotes
            ch.deleteCharAt(0);
            ch.deleteCharAt(ch.length() - 1);

            var t = Arrays.stream(ch.toString().split("\\|"))
                    .map(String::strip)
                    .toList();
            return new ArrayList<>(Collections.singleton(new VCSCommit(t.get(0), t.get(1))));
        };

        return customLog(parser, customArg).get(0);
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
        return null;
    }

    @Override
    public ArrayList<String> list() throws Exception {
        return null;
    }

    @Override
    public ArrayList<VCSCommit> log() throws Exception {
        return null;
    }

    private ArrayList<VCSCommit> customLog(GitLogParser parser, CommandArg... customOpts) throws Exception {
        CommandBuilder logBuilder = gitBuilder()
                .addArguments(new CommandArg("log"))
                .addArguments(customOpts);
        var rawLog = new CommandLine(logBuilder).call();
        return parser.parse(rawLog);
    }

    @Override
    public ArrayList<String> patch() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> branch() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> merge() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> tag() throws Exception {
        return null;
    }
}
