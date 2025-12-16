package fr.fms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    private static final String URL = "jdbc:mariadb://127.0.0.1:3306/Shop?useUnicode=true&characterEncoding=utf8mb4&characterSetResults=utf8mb4";
    private static final String USER = "root";
    private static final String PWD = "pwd";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }
}
