package com.github.maxpushka.vcs_all_in_one.repos;

public record Repository(
        Integer id,
        String path,
        RepositoryType type
) {
    @Override
    public String toString() {
        return this.id + " | " + this.path + " | " + this.type;
    }
}
