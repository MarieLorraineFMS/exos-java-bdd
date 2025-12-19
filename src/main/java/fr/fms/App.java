package fr.fms;

import fr.fms.dao.ArticleDao;
import fr.fms.entity.Article;
import fr.fms.exceptions.DaoException;
import fr.fms.exceptions.Ex2Exceptions;
import fr.fms.file.Ex4File;
import fr.fms.thread.Ex31Thread;
import fr.fms.thread.Ex32Thread;

import static fr.fms.utils.Helpers.spacer;
import static fr.fms.utils.Helpers.title;

import java.util.Scanner;

import fr.fms.utils.AppLogger;

public class App {

    public static void main(String[] args) {
        try {
            run();
        } catch (DaoException e) {
            AppLogger.error("❌ DB error: " + e.getMessage());
        } catch (Exception e) {
            AppLogger.error("❌ Unexpected error: " + e.getMessage());
        }
    }

    private static void run() throws Exception {
        try (Scanner sc = new Scanner(System.in)) {

            while (true) {
                spacer();
                title("/////////////////////// MENU ///////////////////////////////");
                System.out.println("1) Ex1 - JDBC CRUD Articles");
                System.out.println("2) Ex2 - Exceptions");
                System.out.println("3) Ex3.1 - Threads");
                System.out.println("4) Ex3.2 - Runnable");
                System.out.println("5) Ex3.3 - Horloge digitale");
                System.out.println("6) Ex4 - File");
                System.out.println("0) Quitter");
                System.out.print("Choix : ");

                String choice = sc.nextLine().trim();
                spacer();

                if (choice.equals("0")) {
                    System.out.println("Bye..!!!");
                    break;
                }

                switch (choice) {
                    case "1" -> runEx1();
                    case "2" -> Ex2Exceptions.main(new String[0]);
                    case "3" -> Ex31Thread.main(new String[0]);
                    case "4" -> Ex32Thread.main(new String[0]);
                    case "5" -> runInNewTerminal("fr.fms.thread.Ex33Thread");
                    case "6" -> Ex4File.main(new String[0]);
                    default -> System.out.println("Choix invalide.");
                }

                spacer();
            }
        }
    }

    private static void runEx1() throws Exception {
        ArticleDao dao = new ArticleDao();

        int id = dao.create(new Article("Micro soudeur", "ECorp", 199.34));
        AppLogger.info("Inserted id = " + id);
        spacer();

        Article a = dao.readById(id);
        AppLogger.info("ReadById = " + a);
        spacer();

        a.description = "Micro soudeur (v2)";
        a.unitaryPrice = 9.99;
        AppLogger.info("Updated : " + dao.update(a));
        spacer();

        AppLogger.info("Deleted : " + dao.delete(id));
        spacer();

        title("ALL");
        spacer();
        dao.readAll().forEach(System.out::println);
        spacer();
    }

    // New terminal for clock
    private static void runInNewTerminal(String mainClass) throws Exception {

        // Compile project (skip tests)
        // "-q" (quiet) = Print almost Maven error
        // "clean" = remove target/class folder
        int code = new ProcessBuilder("cmd", "/c", "mvn", "-q", "-DskipTests", "clean", "compile")
                // Maven output goes to first terminal menu
                .inheritIO()
                .start()
                // Wait for Maven to finish compilation
                .waitFor();

        // If compilation failed, don't open new terminal
        if (code != 0) {
            AppLogger.error("❌ Build failed, cannot start " + mainClass);
            return;
        }

        // PowerShell command :
        // - cd to project folder
        // - run "compiled.class" from target/classes :Ex33Thread.class
        String cmd = "cd \"" + System.getProperty("user.dir") + "\"; " +
                "java -cp \"target\\\\classes\" " + mainClass;

        // Open a NEW window (cmd "start") & run PowerShell inside
        // -'-NoExit' keeps window open
        // -'-Command' runs 'cmd' string
        new ProcessBuilder(
                "cmd", "/c", "start", "\"DigitalClock\"",
                "powershell", "-NoExit", "-Command", cmd).start();
    }

};
