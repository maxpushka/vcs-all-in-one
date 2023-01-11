package com.github.maxpushka.vcs_all_in_one.shell;

import static com.github.maxpushka.vcs_all_in_one.shell.Colors.*;

public class Out {
    public static void debug(String x) {
        System.out.println(ANSI_YELLOW + x + ANSI_RESET);
    }

    public static void error(String x) {
        System.out.println(ANSI_RED + x + ANSI_RESET);
    }
}
