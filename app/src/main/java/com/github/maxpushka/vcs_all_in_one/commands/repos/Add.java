package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "add", description = "Register a repository")
class Add implements Callable<Integer> {
    @Parameters(arity = "1..*", description = "path(s) to the repository(ies)")
    String[] paths;

    @Override
    public Integer call() {
        RepositoriesAdapter reposAdapter;
        try {
            reposAdapter = new RepositoriesFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        for (var repo : paths) {
            try {
                reposAdapter.addRepository(repo);
            } catch (Exception e) {
                Out.error("Failed to add repository " + repo);
                Out.error(e.getMessage());
                return 1;
            }
            Out.log("Repository is registered successfully " + repo);
        }
        return 0;
    }
}
