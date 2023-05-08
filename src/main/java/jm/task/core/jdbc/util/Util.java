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
    private static final String URL = "jdbc:mysql://localhost:3306/peoples";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "qasfg729Sd";

    private static SessionFactory sessionFactory;

    public static final String QUERY_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "  id bigint NOT NULL AUTO_INCREMENT," +
                    "  age tinyint DEFAULT NULL," +
                    "  lastName varchar(255) DEFAULT NULL," +
                    "  name varchar(255) DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ")";

    public static final String QUERY_DROP_TABLE =
            "DROP TABLE IF EXISTS users";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");

                settings.put(Environment.SHOW_SQL, "true");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
