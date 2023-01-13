package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoryType;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSDetector;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn.copySeparator;

@Command(name = "tag", description = "Create a tag object")
class Tag implements Callable<Integer> {
    @Parameters(description = "tag name")
    String tagname;

    @Option(names = "--trunk", description = "(SVN only) Trunk of the branch; will be ignored for other VCS types")
    String trunk;

    @Override
    public Integer call() throws Exception {
        VCSFacade vcs;
        try {
            vcs = new VCSFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        // format data for SVN
        String args = tagname;

        RepositoryType type = VCSDetector.detectType(System.getProperty("user.dir"));
        if (type == RepositoryType.SVN) {
            if (trunk == null || trunk.isEmpty()) {
                Out.error("Trunk must be specified for SVN repository");
                return 1;
            }
            args = trunk + copySeparator + tagname;
        }

        ArrayList<String> output = vcs.tag(args);
        for (var line : output) {
            Out.log(line);
        }
        return 0;
    }
}
