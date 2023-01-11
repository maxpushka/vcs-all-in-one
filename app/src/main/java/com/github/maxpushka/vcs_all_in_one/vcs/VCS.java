package com.github.maxpushka.vcs_all_in_one.vcs;

import java.util.ArrayList;

public abstract class VCS {
    public abstract VCSCommit commit(String msg) throws Exception;

    public abstract ArrayList<String> update() throws Exception;

    public abstract ArrayList<String> push() throws Exception;

    public abstract ArrayList<String> pull() throws Exception;

    public abstract ArrayList<String> fetch() throws Exception;

    public abstract ArrayList<String> list() throws Exception;

    public abstract ArrayList<VCSCommit> log() throws Exception;

    public abstract ArrayList<String> patch() throws Exception;

    public abstract ArrayList<String> branch() throws Exception;

    public abstract ArrayList<String> merge() throws Exception;

    public abstract ArrayList<String> tag() throws Exception;
}