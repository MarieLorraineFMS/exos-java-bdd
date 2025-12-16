package fr.fms.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class AppLogger {
    private AppLogger() {
    }

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // INFO LOG
    public static void info(String msg) {
        System.out.println("[" + LocalTime.now().format(FMT) + "] INFO  : " + msg);
    }

    // ERROR LOG
    public static void error(String msg) {
        System.err.println("[" + LocalTime.now().format(FMT) + "] ERROR : " + msg);
    }

    // CONNECTION LOG
    public static void rocket(String msg) {
        info("🚀 " + msg);
    }
}
