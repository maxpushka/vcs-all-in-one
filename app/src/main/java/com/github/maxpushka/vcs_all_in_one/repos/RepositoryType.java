package com.github.maxpushka.vcs_all_in_one.repos;

public enum RepositoryType {
    GIT,
    HG,
    SVN;

    public static RepositoryType toType(String type) {
        return switch (type) {
            case "HG" -> HG;
            case "SVN" -> SVN;
            default -> GIT;
        };
    }
}
