package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "remove", description = "Deregister an existing repository")
public class Remove implements Callable<Integer> {
    @Parameters(arity = "0..*", description = "name of repository")
    String repoName;

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
            reposAdapter.removeRepository(repoName);
        } catch (Exception e) {
            Out.error("Failed to deregister repository");
            Out.debug(e.getMessage());
            return 1;
        }
        System.out.println("Successfully removed");
        return 0;
    }
}
