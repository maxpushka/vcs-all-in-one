package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "remove", description = "Deregister an existing repository")
class Remove implements Callable<Integer> {
    @Parameters(arity = "1..*", description = "path(s) of repository(ies)")
    String[] repo_paths;

    @Override
    public Integer call() {
        RepositoriesAdapter reposAdapter;
        try {
            reposAdapter = new RepositoriesFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        for (var repo : repo_paths) {
            try {
                reposAdapter.removeRepository(repo);
            } catch (Exception e) {
                Out.error("Failed to deregister repository " + repo);
                Out.debug(e.getMessage());
                return 1;
            }
            Out.log("Successfully removed " + repo);
        }
        return 0;
    }
}
