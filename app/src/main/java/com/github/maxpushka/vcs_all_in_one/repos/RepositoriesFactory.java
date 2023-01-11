package com.github.maxpushka.vcs_all_in_one.repos;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class RepositoriesFactory implements Callable<RepositoriesAdapter> {
    @Override
    public RepositoriesAdapter call() throws SQLException, URISyntaxException {
        return new RepositoriesAdapter();
    }
}
