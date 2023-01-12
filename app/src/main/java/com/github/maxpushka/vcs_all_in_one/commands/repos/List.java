package com.github.maxpushka.vcs_all_in_one.commands.repos;

import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesAdapter;
import com.github.maxpushka.vcs_all_in_one.repos.RepositoriesFactory;
import com.github.maxpushka.vcs_all_in_one.repos.Repository;
import com.github.maxpushka.vcs_all_in_one.shell.Out;
import de.vandermeer.asciitable.AsciiTable;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "list", description = "List all registered repositories")
class List implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        RepositoriesAdapter reposAdapter;
        try {
            reposAdapter = new RepositoriesFactory().call();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        ArrayList<Repository> registeredRepos;
        try {
            registeredRepos = reposAdapter.listAll();
        } catch (Exception e) {
            Out.error(e.getMessage());
            return 1;
        }

        // build table of repositories
        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow("REPOSITORY NAME", "TYPE", "FULL PATH");
        at.addRule();
        for (var repo : registeredRepos) {
            String name = new File(repo.path()).getName();
            at.addRow(name, repo.type(), repo.path());
            at.addRule();
        }

        String renderedTable = at.render();
        Out.log(renderedTable);
        return 0;
    }
}
