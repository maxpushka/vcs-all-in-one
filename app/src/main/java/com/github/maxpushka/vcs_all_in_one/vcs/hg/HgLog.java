package com.github.maxpushka.vcs_all_in_one.vcs.hg;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg.hgBuilder;

class HgLog implements Callable<ArrayList<VCSCommit>> {
    static ArrayList<VCSCommit> customLog(CommandArg... customOpts) throws Exception {
        CommandBuilder builder = hgBuilder()
                .addArguments(new CommandArg("log"))
                .addArguments(customOpts);
        var rawLog = new CommandLine(builder).call();
        return Hg.logParser.parse(rawLog);
    }

    @Override
    public ArrayList<VCSCommit> call() throws Exception {
        return customLog(new CommandArg("--template", "'{node|short} | {desc}\\n'"));
    }
}
