package com.github.maxpushka.vcs_all_in_one.vcs;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoryType;
import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;
import com.github.maxpushka.vcs_all_in_one.shell.Out;

import java.util.ArrayList;

class NoVCSFoundException extends Exception {
    public NoVCSFoundException() {
        super("fatal: not a repository (or any parent up to mount point /)");
    }
}

public class VCSDetector {
    public static RepositoryType detectType() throws NoVCSFoundException {
        final String path = System.getProperty("user.dir");
        return detectType(path);
    }

    public static RepositoryType detectType(String path) throws NoVCSFoundException {
        if (VCSDetector.isGit(path)) {
            Out.log("Git repository detected.");
            return RepositoryType.GIT;
        } else if (VCSDetector.isMercurial(path)) {
            Out.log("Mercurial repository detected.");
            return RepositoryType.HG;
        } else if (VCSDetector.isSVN(path)) {
            Out.log("SVN repository detected.");
            return RepositoryType.SVN;
        }

        throw new NoVCSFoundException();
    }

    private static Boolean runCommand(CommandBuilder cmd) {
        ArrayList<String> out;
        try {
            out = new CommandLine(cmd).call();
        } catch (Exception e) {
            return false;
        }

        return !out.isEmpty();
    }

    private static Boolean isGit(String path) {
        var pathBuilder = new StringBuilder(path);
        if (!path.endsWith(".git")) {
            if (!path.endsWith("/")) {
                pathBuilder.append("/");
            }
            pathBuilder.append(".git");
        }

        CommandBuilder cmd = new CommandBuilder().addArguments(
                new CommandArg("git"),
                new CommandArg("--git-dir", pathBuilder.toString()),
                new CommandArg("rev-parse"),
                new CommandArg("--show-toplevel"));
        return runCommand(cmd);
    }

    private static Boolean isSVN(String path) {
        CommandBuilder cmd = new CommandBuilder().addArguments(
                new CommandArg("svn"),
                new CommandArg("info"),
                new CommandArg(path));
        return runCommand(cmd);
    }

    private static Boolean isMercurial(String path) {
        CommandBuilder cmd = new CommandBuilder().addArguments(
                new CommandArg("hg"),
                new CommandArg("root"),
                new CommandArg("--cwd", path));
        return runCommand(cmd);
    }
}
