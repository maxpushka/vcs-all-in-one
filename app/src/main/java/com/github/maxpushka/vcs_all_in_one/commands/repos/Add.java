package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "add", description = "Register a repository")
public class Add implements Callable<Integer> {
    @Parameters(arity = "0..*", description = "add message")
    String repoPath;

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
            reposAdapter.addRepository(repoPath);
        } catch (Exception e) {
            Out.error("Failed to add repository");
            Out.debug(e.getMessage());
            return 1;
        }
        System.out.println("Repository is registered successfully");
        return 0;
    }
}
