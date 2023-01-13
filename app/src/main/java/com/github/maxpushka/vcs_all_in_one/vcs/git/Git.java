package com.github.maxpushka.vcs_all_in_one.vcs.git;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;

import java.util.ArrayList;

public final class Git implements VCSFacade {
    static CommandBuilder gitBuilder() {
        return new CommandBuilder().addArguments(new CommandArg("git"));
    }

    @Override
    public ArrayList<String> clone_repo(String url, String dir) throws Exception {
        return new GitClone(url, dir).call();
    }

    @Override
    public ArrayList<String> status() throws Exception {
        return new GitStatus().call();
    }

    @Override
    public ArrayList<String> stage(ArrayList<String> files) throws Exception {
        return new GitAdd(files).call();
    }

    @Override
    public VCSCommit commit(String message) throws Exception {
        return new GitCommit(message).call();
    }

    public ArrayList<String> diff(String commit) throws Exception {
        return new GitDiff(commit).call();
    }

    @Override
    public ArrayList<String> diff(String commit1, String commit2) throws Exception {
        return new GitDiff(commit1, commit2).call();
    }

    @Override
    public ArrayList<String> fetch_remote() throws Exception {
        return new GitPull().call();
    }

    @Override
    public ArrayList<String> push() throws Exception {
        return new GitPush().call();
    }

    @Override
    public ArrayList<VCSCommit> log() throws Exception {
        return new GitLog().call();
    }

    @Override
    public ArrayList<String> switch_branch(String branch) throws Exception {
        return new GitSwitch(branch).call();
    }

    @Override
    public ArrayList<String> create_branch(String branch) throws Exception {
        return new GitBranch(branch).call();
    }

    @Override
    public ArrayList<String> merge_branch(String branch) throws Exception {
        return new GitMerge(branch).call();
    }

    @Override
    public ArrayList<String> tag(String tag) throws Exception {
        return new GitTag(tag).call();
    }
}
