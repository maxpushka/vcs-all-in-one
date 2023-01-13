package com.github.maxpushka.vcs_all_in_one.vcs.git;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSLogParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.git.Git.gitBuilder;

class GitLog implements Callable<ArrayList<VCSCommit>> {
    private static final VCSLogParser logParser = rawLog -> {
        var list = rawLog.stream().map(rawEntry -> {
            var ch = new StringBuilder(rawEntry);

            // delete leading and trailing quotes, if any
            if (ch.charAt(0) == '"') {
                ch.deleteCharAt(0);
            }
            if (ch.charAt(ch.length() - 1) == '"') {
                ch.deleteCharAt(ch.length() - 1);
            }

            // build array of [commit hash, commit msg] pairs
            var t = Arrays.stream(ch.toString().split("\\|"))
                    .map(String::strip)
                    .toList();
            return new VCSCommit(t.get(0), t.get(1));
        }).toList();

        return new ArrayList<>(list);
    };

    static ArrayList<VCSCommit> customLog(CommandArg... customOpts) throws Exception {
        CommandBuilder builder = gitBuilder()
                .addArguments(new CommandArg("log"))
                .addArguments(customOpts);
        var rawLog = new CommandLine(builder).call();
        return logParser.parse(rawLog);
    }

    @Override
    public ArrayList<VCSCommit> call() throws Exception {
        return customLog(new CommandArg("--pretty", "format:\"%h | %s\""));
    }
}
