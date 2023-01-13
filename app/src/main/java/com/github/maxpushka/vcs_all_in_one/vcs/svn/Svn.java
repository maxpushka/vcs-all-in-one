package com.github.maxpushka.vcs_all_in_one.vcs.svn;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSCommit;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;

import java.util.ArrayList;

public final class Svn implements VCSFacade {
    public static final String copySeparator = "<br/>";

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
    public ArrayList<String> diff(String revision) throws Exception {
        return new SvnDiff(revision).call();
    }

    @Override
    public ArrayList<String> diff(String revision1, String revision2) throws Exception {
        return new SvnDiff(revision1, revision2).call();
    }

    @Override
    public ArrayList<String> fetch_remote() throws Exception {
        return new SvnUpdate().call();
    }

    @Override
    public ArrayList<String> push() {
        ArrayList<String> text = new ArrayList<>();
        text.add("""
                SVN is not a distributed VCS and cannot push changes.
                                
                The very point of distributed version control is this feature of local commits
                that can at a later point be merged with the upstream repository.
                  
                The central hindrance is SVN's linear revision numbering,
                which implies that every client must get a new identifying revision number for each changeset.
                 
                Since "allocating" a revision number and later using it would lead to all kinds of race conditions,
                the "commit" and "push" actions are atomic in SVN and every non-distributed version control system.
                """);

        return text;
    }

    @Override
    public ArrayList<VCSCommit> log() throws Exception {
        return new SvnLog().call();
    }

    @Override
    public ArrayList<String> switch_branch(String branch) throws Exception {
        return new SvnSwitch(branch).call();
    }

    @Override
    public ArrayList<String> create_branch(String branch) throws Exception {
        var args = branch.split(copySeparator);
        return new SvnCopy(args[0], args[1]).call();
    }

    @Override
    public ArrayList<String> merge_branch(String branch) throws Exception {
        return new SvcMerge(branch).call();
    }

    @Override
    public ArrayList<String> tag(String tag) throws Exception {
        // for SVN tag is essentially the same as the branch creation
        return create_branch(tag);
    }
}
