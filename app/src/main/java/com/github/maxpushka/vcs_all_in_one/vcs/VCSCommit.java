package com.github.maxpushka.vcs_all_in_one.vcs;

// VCSCommit holds generic commit information
public record VCSCommit(
        String hash,
        String message
) {
    @Override
    public String toString() {
        return hash.trim() + " | " + message.trim();
    }
}
