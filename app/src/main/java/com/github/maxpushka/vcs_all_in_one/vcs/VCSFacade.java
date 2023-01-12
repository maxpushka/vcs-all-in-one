package com.github.maxpushka.vcs_all_in_one.vcs;

import java.util.ArrayList;

public interface VCSFacade {
    ArrayList<String> clone_repo(String url, String dir) throws Exception;

    ArrayList<String> status() throws Exception;

    ArrayList<String> stage(ArrayList<String> files) throws Exception;

    VCSCommit commit(String message) throws Exception;

    ArrayList<String> diff(String commit) throws Exception;

    ArrayList<String> diff(String commit1, String commit2) throws Exception;

    ArrayList<String> fetch_remote() throws Exception;

    ArrayList<String> push() throws Exception;

    ArrayList<VCSCommit> log() throws Exception;

    ArrayList<String> switch_branch(String branch) throws Exception;

    ArrayList<String> create_branch(String branch) throws Exception;

    ArrayList<String> merge_branch(String branch) throws Exception;

    ArrayList<String> tag(String tagName) throws Exception;
}