package com.github.maxpushka.vcs_all_in_one.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class CommandLine implements Callable<ArrayList<String>> {
    private final CommandBuilder command;
    private final ArrayList<String> output;

    public CommandLine(CommandBuilder command) {
        this.command = command;
        this.output = new ArrayList<>();
    }

    @Override
    public ArrayList<String> call() throws Exception {
        final String[] cmd = this.command.compile();
        Out.debug("CMD IN :\t" + Arrays.toString(cmd));
        final Process p = Runtime.getRuntime().exec(cmd);

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;

        try {
            while ((line = input.readLine()) != null) {
                this.output.add(line);
            }
        } catch (IOException e) {
            Out.error(e.getMessage());
            e.printStackTrace();
        }

        p.waitFor();
        Out.debug("CMD OUT:\t" + this.output);
        return this.output;
    }
}
