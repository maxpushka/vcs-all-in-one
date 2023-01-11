package com.github.maxpushka.vcs_all_in_one.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "vcs",
        mixinStandardHelpOptions = true,
        version = "VCS all-in-one 0.1.0",
        subcommands = {
                Commit.class
        })
public class RootCommand {
}