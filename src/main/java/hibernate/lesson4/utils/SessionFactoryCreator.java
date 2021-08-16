package hibernate.lesson4.utils;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class SessionFactoryCreator {
    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
