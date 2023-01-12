package com.github.maxpushka.vcs_all_in_one.vcs.hg;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSLogParser;

import java.util.ArrayList;

public final class Hg implements VCSFacade {
    static final VCSLogParser logParser = new VCSLogParser() {
        @Override
        public ArrayList<VCSCommit> parse(ArrayList<String> rawLog) throws Exception {
            return VCSLogParser.super.parse(rawLog);
        }
    };

    static CommandBuilder hgBuilder() {
        return new CommandBuilder().addArguments(
                new CommandArg("hg"),
                new CommandArg("--pager", "never"));
    }

    @Override
    public ArrayList<String> clone_repo(String url, String dir) throws Exception {
        return new HgClone(url, dir).call();
    }

    @Override
    public ArrayList<String> status() throws Exception {
        return new HgStatus().call();
    }

    @Override
    public ArrayList<String> stage(ArrayList<String> files) throws Exception {
        return new HgAdd(files).call();
    }

    @Override
    public VCSCommit commit(String message) throws Exception {
        return new HgCommit(message).call();
    }

    @Override
    public ArrayList<String> diff(String commit) throws Exception {
        return new HgDiff(commit).call();
    }

    @Override
    public ArrayList<String> diff(String commit1, String commit2) throws Exception {
        return new HgDiff(commit1, commit2).call();
    }

    @Override
    public ArrayList<String> fetch_remote() throws Exception {
        return new HgPull().call();
    }

    @Override
    public ArrayList<String> push() throws Exception {
        return new HgPush().call();
    }

    @Override
    public ArrayList<VCSCommit> log() throws Exception {
        return new HgLog().call();
    }

    @Override
    public ArrayList<String> switch_branch(String branch) throws Exception {
        return new HgUpdate(branch).call();
    }

    @Override
    public ArrayList<String> create_branch(String branch) throws Exception {
        return new HgBranch(branch).call();
    }

    @Override
    public ArrayList<String> merge_branch(String branch) throws Exception {
        // TODO: backend should RETURN lines, not print them
        var output = new HgUpdate().call();
        for (var line : output) {
            Out.log(line);
        }

        return new HgMerge(branch).call();
    }

    @Override
    public ArrayList<String> tag(String tagName) throws Exception {
        return new HgTag(tagName).call();
    }
}
