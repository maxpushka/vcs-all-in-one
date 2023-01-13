package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;

import java.util.ArrayList;

public final class Svn implements VCSFacade {
    static CommandBuilder svnBuilder() {
        return new CommandBuilder().addArguments(new CommandArg("svn"));
    }

    @Override
    public ArrayList<String> clone_repo(String url, String dir) throws Exception {
        return new SvnClone(url, dir).call();
    }

    @Override
    public ArrayList<String> status() throws Exception {
        return new SvnStatus().call();
    }

    @Override
    public ArrayList<String> stage(ArrayList<String> files) throws Exception {
        return new SvnAdd(files).call();
    }

    @Override
    public VCSCommit commit(String message) throws Exception {
        return new SvnCommit(message).call();
    }

    @Override
    public ArrayList<String> diff(String commit) throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> diff(String commit1, String commit2) throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> fetch_remote() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> push() throws Exception {
        return null;
    }

    @Override
    public ArrayList<VCSCommit> log() throws Exception {
        return new SvnLog().call();
    }

    @Override
    public ArrayList<String> switch_branch(String branch) throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> create_branch(String branch) throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> merge_branch(String branch) throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> tag(String tagName) throws Exception {
        return null;
    }
}
