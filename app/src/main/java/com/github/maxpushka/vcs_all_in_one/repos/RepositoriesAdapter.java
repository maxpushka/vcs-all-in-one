package com.github.maxpushka.vcs_all_in_one.repos;

import com.github.maxpushka.vcs_all_in_one.vcs.VCSDetector;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class RepositoriesAdapter {
    private final DBAdapter dbAdapter;

    public RepositoriesAdapter() throws SQLException, URISyntaxException {
        this.dbAdapter = new DBAdapter();
    }

    public void addRepository(String repoPath) throws Exception {
        // normalize path
        Path normalizedPath = Paths.get(repoPath).normalize();
        String path = normalizedPath.toAbsolutePath().toString();

        // check if repo is already registered
        if (!listByPath(path).isEmpty()) {
            throw new RepositoryAlreadyRegisteredException();
        }

        // detect repo type by path
        RepositoryType type = VCSDetector.detectType(path);

        Connection conn = this.dbAdapter.getConnection();

        final String query = "INSERT INTO repositories(path, type) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, path);
        pstmt.setString(2, type.toString());

        pstmt.execute();
    }

    public void removeRepository(String repoPath) throws Exception {
        // normalize path
        Path normalizedPath = Paths.get(repoPath).normalize();
        String path = normalizedPath.toAbsolutePath().toString();

        Connection conn = this.dbAdapter.getConnection();

        final String sql = "DELETE FROM repositories WHERE path = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, path);

        pstmt.execute();
        pstmt.close();
    }

    public void cleanAllRepositories() throws Exception {
        Connection conn = this.dbAdapter.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM repositories");
    }

    public Boolean moveRepository(String from, String to) throws Exception {
        // normalize paths
        Path normalizedFrom = Paths.get(from).normalize();
        from = normalizedFrom.toAbsolutePath().toString();

        Path normalizedTo = Paths.get(from).normalize();
        to = normalizedFrom.toAbsolutePath().toString();

        // check if repo exists in DB
        if (listByPath(from).isEmpty()) {
            throw new RepositoryNotRegisteredException();
        }

        // verify that repo exists on file system
        if (new File(to).exists()) {
            throw new RepositoryDoesNotExistException();
        }

        // verify that new location does not already exist
        if (new File(to).isFile()) {
            throw new NewPathAlreadyExistsException();
        }

        // update repository info in database
        Connection conn = this.dbAdapter.getConnection();

        final String query = "UPDATE repositories SET path = ? WHERE path = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, to);
        pstmt.setString(2, from);

        pstmt.execute();

        // move repo to new location
        return new File(from).renameTo(new File(to));
    }

    public ArrayList<Repository> listByPath(String path) throws SQLException {
        Connection conn = this.dbAdapter.getConnection();

        final String query = "SELECT * FROM repositories WHERE path = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, path);

        ResultSet rs = pstmt.executeQuery();
        return parseResults(rs);
    }

    @SuppressWarnings("unused")
    public ArrayList<Repository> listByType(RepositoryType type) throws SQLException {
        Connection conn = this.dbAdapter.getConnection();

        final String query = "SELECT * FROM repositories WHERE type = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, type.toString());

        ResultSet rs = pstmt.executeQuery();
        return parseResults(rs);
    }

    public ArrayList<Repository> listAll() throws SQLException {
        Connection conn = this.dbAdapter.getConnection();
        Statement stmt = conn.createStatement();

        final String query = "SELECT * FROM repositories";
        ResultSet rs = stmt.executeQuery(query);

        return parseResults(rs);
    }

    private ArrayList<Repository> parseResults(ResultSet rs) throws SQLException {
        // loop through the result set
        ArrayList<Repository> repos = new ArrayList<>();
        while (rs.next()) {
            final RepositoryType type = RepositoryType.toType(rs.getString("type"));
            final var repo = new Repository(
                    rs.getInt("id"),
                    rs.getString("path"),
                    type);

            repos.add(repo);
        }

        return repos;
    }
}

class RepositoryNotRegisteredException extends Exception {
    public RepositoryNotRegisteredException() {
        super("Repository is not registered");
    }
}

class RepositoryAlreadyRegisteredException extends Exception {
    public RepositoryAlreadyRegisteredException() {
        super("Repository is already registered");
    }
}

class RepositoryDoesNotExistException extends Exception {
    public RepositoryDoesNotExistException() {
        super("Cannot move repository: new path already exists");
    }
}

class NewPathAlreadyExistsException extends Exception {
    public NewPathAlreadyExistsException() {
        super("Cannot move repository: new path already exists");
    }
}