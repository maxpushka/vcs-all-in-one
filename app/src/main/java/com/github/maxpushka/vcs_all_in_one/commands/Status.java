package com.github.maxpushka.vcs_all_in_one.commands;

import com.github.maxpushka.vcs_all_in_one.shell.Out;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFacade;
import com.github.maxpushka.vcs_all_in_one.vcs.VCSFactory;
import picocli.CommandLine.Command;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "status", description = "Show the working tree status")
class Status implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        VCSFacade vcs;
        try {
            vcs = new VCSFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        ArrayList<String> output = vcs.status();
        for (var line : output) {
            Out.log(line);
        }

        return 0;
    }
}