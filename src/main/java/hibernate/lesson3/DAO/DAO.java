package hibernate.lesson3.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class DAO<T> {
    private static SessionFactory sessionFactory;

    public abstract T save(T t);

    public abstract void delete(long id);

    public abstract T update(T t);

    public abstract T findById(long id);

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
