package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "clean", description = "Deregister ALL existing repositories")
public class Clean implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
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
        System.out.println("Successfully deregistered ALL repositories");
        return 0;
    }
}
