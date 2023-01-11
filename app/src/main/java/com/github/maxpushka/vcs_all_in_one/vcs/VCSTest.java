package com.github.maxpushka.vcs_all_in_one.vcs;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoryType;
import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;

class NoVCSFoundException extends Exception {
    public NoVCSFoundException() {
        super("fatal: not a repository (or any parent up to mount point /)");
    }
}

public class VCSTest {
    public static RepositoryType detectRepositoryType() throws NoVCSFoundException {
        final String path = System.getProperty("user.dir");
        return detectRepositoryType(path);
    }

    public static RepositoryType detectRepositoryType(String path) throws NoVCSFoundException {
        if (VCSTest.isGit(path)) {
            System.out.println("Git repository detected.");
            return RepositoryType.GIT;
        } else if (VCSTest.isMercurial(path)) {
            System.out.println("Mercurial repository detected.");
            return RepositoryType.HG;
        } else if (VCSTest.isSVN(path)) {
            System.out.println("SVN repository detected.");
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
                new CommandArg("svnadmin"),
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
