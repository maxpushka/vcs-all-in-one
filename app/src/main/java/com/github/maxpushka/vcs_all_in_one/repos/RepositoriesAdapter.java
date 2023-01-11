package com.github.maxpushka.vcs_all_in_one.repos;

import com.github.maxpushka.vcs_all_in_one.vcs.VCSTest;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;

public class RepositoriesAdapter {
    private final DBAdapter dbAdapter;

    public RepositoriesAdapter() throws SQLException, URISyntaxException {
        this.dbAdapter = new DBAdapter();
    }

    public void addRepository(String repoPath) throws Exception {
        RepositoryType type = VCSTest.detectRepositoryType(repoPath);

        Connection conn = this.dbAdapter.getConnection();

        final String query = "INSERT INTO repositories(path, type) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, repoPath);
        pstmt.setString(2, type.toString());

        pstmt.execute();
    }

    public void removeRepository(String path) throws Exception {
        Connection conn = this.dbAdapter.getConnection();

        final String sql = "DELETE FROM repositories WHERE path = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, path);

        pstmt.execute();
        pstmt.close();
    }

    public Boolean moveRepository(String from, String to) throws Exception {
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
            throw new NewPathAlreadyExistsExcetpion();
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
            RepositoryType type;
            try {
                type = RepositoryType.toType(rs.getString("type"));
            } catch (Exception e) {
                type = RepositoryType.GIT; // TODO: find a better way to handle this exception
            }

            var repo = new Repository(
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

class RepositoryDoesNotExistException extends Exception {
    public RepositoryDoesNotExistException() {
        super("Cannot move repository: new path already exists");
    }
}

class NewPathAlreadyExistsExcetpion extends Exception {
    public NewPathAlreadyExistsExcetpion() {
        super("Cannot move repository: new path already exists");
    }
}