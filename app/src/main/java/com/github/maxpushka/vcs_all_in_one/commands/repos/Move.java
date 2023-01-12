package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "move", description = "Move repository from old to new place")
class Move implements Callable<Integer> {
    @Parameters(index = "0", description = "existing repository path")
    String old_path;

    @Parameters(index = "1", description = "new path")
    String new_path;

    @Override
    public Integer call() throws Exception {
        RepositoriesAdapter reposAdapter;
        try {
            reposAdapter = new RepositoriesFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        Boolean isMoved;
        try {
            isMoved = reposAdapter.moveRepository(old_path, new_path);
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }
        if (!isMoved) {
            Out.error("Failed to move to new repository");
            return 1;
        }
        Out.log("Successfully moved");
        return 0;
    }
}
