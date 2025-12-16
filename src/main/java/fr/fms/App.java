package fr.fms;

import fr.fms.dao.ArticleDao;
import fr.fms.entity.Article;

public class App {
    public static void main(String[] args) throws Exception {
        ArticleDao dao = new ArticleDao();

        // INSERT
        int id = dao.create(new Article("Micro soudeur", "ECorp", 199.34));
        System.out.println("Inserted id = " + id);

        // READ BY ID
        Article a = dao.readById(id);
        System.out.println("ReadById = " + a);

        // UPDATE
        a.description = "Micro soudeur (v2)";
        a.unitaryPrice = 9.99;
        System.out.println("Updated : " + dao.update(a));

        // DELETE
        System.out.println("Deleted : " + dao.delete(id));

        // READ ALL
        System.out.println("=== ALL ===");
        dao.readAll().forEach(System.out::println);

    }

};
