package fr.fms;

import fr.fms.dao.ArticleDao;
import fr.fms.entity.Article;

import static fr.fms.utils.Helpers.spacer;
import static fr.fms.utils.Helpers.title;
import fr.fms.utils.AppLogger;

public class App {
    public static void main(String[] args) throws Exception {
        ArticleDao dao = new ArticleDao();

        // INSERT
        int id = dao.create(new Article("Micro soudeur", "ECorp", 199.34));
        AppLogger.info("Inserted id = " + id);
        spacer();

        // READ BY ID
        Article a = dao.readById(id);
        AppLogger.info("ReadById = " + a);
        spacer();

        // UPDATE
        a.description = "Micro soudeur (v2)";
        a.unitaryPrice = 9.99;
        AppLogger.info("Updated : " + dao.update(a));
        spacer();

        // DELETE
        AppLogger.info("Deleted : " + dao.delete(id));
        spacer();

        // READ ALL
        title("ALL");
        spacer();
        dao.readAll().forEach(System.out::println);
        spacer();

    }

};
