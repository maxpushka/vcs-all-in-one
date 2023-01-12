package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "clone", description = "Clone a repository into a new directory")
class Clone implements Callable<Integer> {
    @Parameters(index = "0", description = "repository URL/path")
    String repository;

    @Parameters(arity = "0..1", description = "directory to clone to (optional)")
    String directory;

    @Override
    public Integer call() throws Exception {
        VCSFacade vcs;
        try {
            vcs = new VCSFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        ArrayList<String> output = vcs.clone_repo(repository, directory);
        for (var line : output) {
            Out.log(line);
        }

        return 0;
    }
}
