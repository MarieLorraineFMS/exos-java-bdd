package fr.fms.utils;

public class Helpers {

    // CLI
    public static void spacer() {
        System.out.println();
    }

    public static void title(String text) {
        System.out.println("//////////// " + text + " ///////////");
        spacer();
    }

    // STRINGS
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isBlank();
    }
}
