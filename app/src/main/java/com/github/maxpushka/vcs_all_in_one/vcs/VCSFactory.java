package com.github.maxpushka.vcs_all_in_one.vcs;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoryType;

import java.util.concurrent.Callable;

/* VCSFactory detects the type of VCS for repo you're currently in and returns an adapter that works for that VCS */
public class VCSFactory implements Callable<VCS> {

    @Override
    public VCS call() throws NoVCSFoundException {
        RepositoryType type = VCSTest.detectRepositoryType();

        if (type == RepositoryType.GIT) {
            return new GitAdapter();
        } else if (type == RepositoryType.HG) {
            return new MercurialAdapter();
        } else { // type == RepositoryType.SVN
            return new SubversionAdapter();
        }
    }
}

