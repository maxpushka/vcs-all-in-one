package com.github.maxpushka.vcs_all_in_one.repos;

import com.github.maxpushka.vcs_all_in_one.App;
import com.github.maxpushka.vcs_all_in_one.shell.Out;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAdapter {
    private Connection conn;

    public DBAdapter() throws SQLException, URISyntaxException {
        this.conn = null;

        // db parameters
        String url = "jdbc:sqlite:" + getDBPath();
        // create a connection to the database
        this.conn = DriverManager.getConnection(url);
        Out.debug("Connection to SQLite has been established.");

        // create table
        createTable();
        Out.debug("Table has been created.");
    }

    private String getDBPath() throws URISyntaxException {
        File jar = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        String parentPath = jar.getParentFile().toURI().getPath();

        String dbName = jar.getName().replaceAll("\\.jar$", ".db");
        return parentPath + dbName;
    }

    private void createTable() throws SQLException {
        final String sql = """
                CREATE TABLE IF NOT EXISTS repositories (
                    id         INTEGER                                PRIMARY KEY AUTOINCREMENT,
                    path       TEXT                                   NOT NULL UNIQUE,
                    type       TEXT CHECK(type IN ('GIT','HG','SVN')) NOT NULL
                );""";

        Statement stmt = this.conn.createStatement();
        stmt.execute(sql);
    }

    public Connection getConnection() {
        return this.conn;
    }
}
