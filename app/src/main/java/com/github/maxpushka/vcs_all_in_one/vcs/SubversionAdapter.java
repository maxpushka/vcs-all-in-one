package com.github.maxpushka.vcs_all_in_one.vcs;

import java.util.ArrayList;

public final class SubversionAdapter extends VCS {
    @Override
    public VCSCommit commit(String msg) throws Exception {
        return null;
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

    @Override
    public ArrayList<String> patch() throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> branch(String targetBranch) throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> merge(String targetBranch) throws Exception {
        return null;
    }

    @Override
    public ArrayList<String> tag(String newTag) throws Exception {
        return null;
    }
}
