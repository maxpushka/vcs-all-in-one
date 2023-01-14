package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoryType;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.git.Git;
import com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg;
import com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "clone", description = "Clone a repository into a new directory")
class Clone implements Callable<Integer> {
    @Parameters(index = "0", description = "type of repository. Valid values: ${COMPLETION-CANDIDATES}")
    RepositoryType type;

    @Parameters(index = "1", description = "repository URL/path")
    String repository;

    @Parameters(index = "2", arity = "0..1", description = "directory to clone to (optional)")
    String directory;

    @Override
    public Integer call() throws Exception {
        VCSFacade vcs = switch (type) {
            case GIT -> new Git();
            case HG -> new Hg();
            case SVN -> new Svn();
        };

        ArrayList<String> output = vcs.clone_repo(repository, directory);
        for (var line : output) {
            Out.log(line);
        }

        return 0;
    }
}
