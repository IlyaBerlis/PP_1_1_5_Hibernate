package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.setProperties;

public class Util {
    // реализуйте настройку соединения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kata";
    private static final String DB_USER = "BerlIS";
    private static final String DB_PASSWORD = "<thkBC1990";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try{
                Configuration configuration = new Configuration();

                configuration.setProperty("hibernate.connection.driver_class", DB_DRIVER);
                configuration.setProperty("hibernate.connection.url", DB_URL);
                configuration.setProperty("hibernate.connection.username", DB_USER);
                configuration.setProperty("hibernate.connection.password", DB_PASSWORD);

                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

    public static void shutdown(){
        if (sessionFactory != null){
            sessionFactory.close();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }

        return connection;
    }
}
