package com.github.maxpushka.vcs_all_in_one.vcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public interface VCSLogParser {
    // Default implementation parses strings in format "e29d781 | Initial commit"
    default ArrayList<VCSCommit> parse(ArrayList<String> rawLog) throws Exception {
        var ch = new StringBuilder(rawLog.get(0));

        // delete leading and trailing quotes
        ch.deleteCharAt(0);
        ch.deleteCharAt(ch.length() - 1);

        var t = Arrays.stream(ch.toString().split("\\|"))
                .map(String::strip)
                .toList();
        return new ArrayList<>(Collections.singleton(new VCSCommit(t.get(0), t.get(1))));
    }
}