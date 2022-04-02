package com.toocol.ssh.common.utils;

import java.io.PrintStream;

import static com.toocol.ssh.core.configuration.SystemConfiguration.*;

/**
 * @author ZhaoZhe
 * @email joezane.cn@gmail.com
 * @date 2021/2/19 16:20
 */
public class Printer {

    private static final PrintStream PRINT = System.out;

    private static final int LOADING_COUNT = 3;

    public static void print(String msg) {
        PRINT.print(msg);
    }

    public static void println() {
        PRINT.println();
    }

    public static void println(String msg) {
        PRINT.println(msg);
    }

    public static void clear() {
        getExecuteMode().ifPresent(executeMode -> {
            getClearCmd().ifPresent(clearCmd -> {
                try {
                    new ProcessBuilder(BOOT_TYPE, executeMode, clearCmd)
                            .inheritIO()
                            .start()
                            .waitFor();
                } catch (Exception e) {
                    // do nothing
                }
            });
        });
    }
}
