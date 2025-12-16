package fr.fms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.fms.utils.AppLogger;
import static fr.fms.utils.Helpers.spacer;

public class DbConfig {
    private static final java.util.Properties PROPS = new java.util.Properties();

    static {
        try (var in = DbConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new IllegalStateException("application.properties not found");
            }
            PROPS.load(in);
            spacer();
            AppLogger.rocket("DB config loaded. Url=" + PROPS.getProperty("db.url")
                    + ", User=" + PROPS.getProperty("db.user"));
        } catch (Exception e) {
            spacer();
            AppLogger.error("Error while connecting to DB : " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private static final String URL = PROPS.getProperty("db.url");
    private static final String USER = PROPS.getProperty("db.user");
    private static final String PWD = PROPS.getProperty("db.pwd");

    public static Connection getConnection() throws SQLException {
        Connection cnx = DriverManager.getConnection(URL, USER, PWD);
        AppLogger.rocket("Connected to DB: " + cnx.getMetaData().getURL()
                + " User=" + cnx.getMetaData().getUserName() + "");
        spacer();
        return cnx;
    }
}
