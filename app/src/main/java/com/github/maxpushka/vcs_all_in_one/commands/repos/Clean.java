package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "clean", description = "Deregister ALL existing repositories")
class Clean implements Callable<Integer> {
    @Override
    public Integer call() {
        RepositoriesAdapter reposAdapter;
        try {
            reposAdapter = new RepositoriesFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        try {
            reposAdapter.cleanAllRepositories();
        } catch (Exception e) {
            Out.error("Failed to deregister ALL repositories");
            Out.debug(e.getMessage());
            return 1;
        }
        Out.log("Successfully deregistered ALL repositories");
        return 0;
    }
}
