package com.github.maxpushka.vcs_all_in_one.vcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

interface VCSLogParser {
    default ArrayList<VCSCommit> parse(ArrayList<String> rawLog) {
        // here you will get a message like "e29d781 | Initial commit"
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