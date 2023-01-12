package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.svnBuilder;

public class SvnLog implements Callable<ArrayList<VCSCommit>> {
    static ArrayList<VCSCommit> customLog(CommandArg... customOpts) throws Exception {
        CommandBuilder builder = svnBuilder()
                .addArguments(
                        new CommandArg("log"),
                        new CommandArg("--xml"))
                .addArguments(customOpts);
        var rawLog = new CommandLine(builder).call();
        return Svn.logParser.parse(rawLog);
    }

    @Override
    public ArrayList<VCSCommit> call() throws Exception {
        return customLog();
    }
}
