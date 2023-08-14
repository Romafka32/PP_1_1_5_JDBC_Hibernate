package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "admin";
    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    public static final String URL_HIBERNATE = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";


    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory;
        try {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.URL, URL_HIBERNATE);
            settings.put(Environment.USER, USER_NAME);
            settings.put(Environment.PASS, PASSWORD);
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.FORMAT_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            throw new ExceptionInInitializerError();
        }
        return sessionFactory;
    }
}
