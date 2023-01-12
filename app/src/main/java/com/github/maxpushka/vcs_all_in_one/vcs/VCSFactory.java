package com.github.maxpushka.vcs_all_in_one.vcs;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoryType;
import com.github.maxpushka.vcs_all_in_one.vcs.git.Git;
import com.github.maxpushka.vcs_all_in_one.vcs.hg.Hg;
import com.github.maxpushka.vcs_all_in_one.vcs.svn.Svn;

import java.util.concurrent.Callable;

/* VCSFactory detects the type of VCS for repo you're currently in and returns an adapter that works for that VCS */
public class VCSFactory implements Callable<VCSFacade> {

    @Override
    public VCSFacade call() throws NoVCSFoundException {
        RepositoryType type = VCSDetector.detectType();

        if (type == RepositoryType.GIT) {
            return new Git();
        } else if (type == RepositoryType.HG) {
            return new Hg();
        } else { // type == RepositoryType.SVN
            return new Svn();
        }
    }
}

