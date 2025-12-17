package fr.fms;

import fr.fms.dao.ArticleDao;
import fr.fms.entity.Article;
import fr.fms.exceptions.DaoException;
import fr.fms.exceptions.Ex2Exceptions;

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
            title("/////////////////////// MENU ///////////////////////////////");
            System.out.println("1) Ex1 - JDBC CRUD Articles");
            System.out.println("2) Ex2 - Exceptions");
            System.out.print("Choix : ");

            String choice = sc.nextLine().trim();
            spacer();

            switch (choice) {
                case "1" -> runEx1();
                case "2" -> Ex2Exceptions.main(new String[0]);
                default -> System.out.println("Choix invalide.");
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

};
