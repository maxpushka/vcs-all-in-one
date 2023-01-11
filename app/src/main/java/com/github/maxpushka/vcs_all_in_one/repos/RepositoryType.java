package com.github.maxpushka.vcs_all_in_one.repos;

public enum RepositoryType {
    GIT,
    HG,
    SVN;

    public static RepositoryType toType(String type) throws Exception {
        return switch (type) {
            case "GIT" -> GIT;
            case "HG" -> HG;
            case "SVN" -> SVN;
            default -> throw new RepositoryTypeNotFoundException();
        };
    }
}

class RepositoryTypeNotFoundException extends Exception {
    public RepositoryTypeNotFoundException() {
        super("fatal: repository type not found");
    }
}
