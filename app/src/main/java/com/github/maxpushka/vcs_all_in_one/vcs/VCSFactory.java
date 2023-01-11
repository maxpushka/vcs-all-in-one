package com.github.maxpushka.vcs_all_in_one.vcs;

import com.github.maxpushka.vcs_all_in_one.shell.CommandArg;
import com.github.maxpushka.vcs_all_in_one.shell.CommandBuilder;
import com.github.maxpushka.vcs_all_in_one.shell.CommandLine;

import java.util.ArrayList;
import java.util.concurrent.Callable;

class NoVCSFound extends Exception {
    public NoVCSFound() {
        super("fatal: not a repository (or any parent up to mount point /)");
    }
}

/* VCSFactory detects the type of VCS for repo you're currently in and returns an adapter that works for that VCS */
public class VCSFactory implements Callable<VCS> {

    @Override
    public VCS call() throws NoVCSFound {
        if (VCSTest.isGit()) {
            System.out.println("Git repository detected.");
            return new Git();
        } else if (VCSTest.isMercurial()) {
            System.out.println("Mercurial repository detected.");
            return new Mercurial();
        } else if (VCSTest.isSVN()) {
            System.out.println("SVN repository detected.");
            return new Subversion();
        }

        throw new NoVCSFound();
    }
}

final class VCSTest {
    private static Boolean runCommand(CommandBuilder cmd) {
        ArrayList<String> out;
        try {
            out = new CommandLine(cmd).call();
        } catch (Exception e) {
            return false;
        }

        return !out.isEmpty();
    }

    public static Boolean isGit() {
        CommandBuilder cmd = new CommandBuilder().addArguments(
                new CommandArg("git"),
                new CommandArg("rev-parse"),
                new CommandArg("--show-toplevel"));
        return runCommand(cmd);
    }

    public static Boolean isSVN() {
        CommandBuilder cmd = new CommandBuilder().addArguments(
                new CommandArg("svnadmin"),
                new CommandArg("info"),
                new CommandArg("."));
        return runCommand(cmd);
    }

    public static Boolean isMercurial() {
        CommandBuilder cmd = new CommandBuilder().addArguments(
                new CommandArg("hg"),
                new CommandArg("root"));
        return runCommand(cmd);
    }
}