package hibernate.lesson2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ProductDAOHQL {
    private static final String SELECT_BY_ID = "FROM Product p WHERE p.id=:id";
    private static final String SELECT_PRODUCTS_BY_NAME = "FROM Product p WHERE p.name=:name";
    private static final String SELECT_PRODUCTS_BY_NAME_LIKE = "FROM Product p WHERE p.name like :name";
    private static final String SELECT_PRODUCTS_BY_NAME_ASC_ORDER = SELECT_PRODUCTS_BY_NAME_LIKE + " order by p.name asc";
    private static final String SELECT_PRODUCTS_BY_NAME_DESC_ORDER = SELECT_PRODUCTS_BY_NAME_LIKE +
            " order by p.name desc";
    private static final String SELECT_PRODUCTS_BY_PRICE = "FROM Product p WHERE p.price between :from and :to";
    private static final String SELECT_PRODUCTS_BY_PRICE_DESC_ORDER = SELECT_PRODUCTS_BY_PRICE + " order by p.price desc";

    private static SessionFactory sessionFactory;

    Product findById(long id) {
        AtomicReference<Product> product = new AtomicReference<>();

        Consumer<Session> action = session -> {
            Query<Product> query = session.createQuery(SELECT_BY_ID, Product.class)
                    .setParameter("id", id);
            product.set(query.getSingleResult());
        };

        executeInsideTransaction(action);
        return product.get();
    }

    List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();

        Consumer<Session> action = session -> {
            Query<Product> query = session.createQuery(SELECT_PRODUCTS_BY_NAME, Product.class)
                    .setParameter("name", name);
            products.addAll(query.list());
        };

        executeInsideTransaction(action);
        return products;
    }

    List<Product> findByContainedName(String name) {
        List<Product> products = new ArrayList<>();

        Consumer<Session> action = session -> {
            Query<Product> query = session.createQuery(SELECT_PRODUCTS_BY_NAME_LIKE, Product.class)
                    .setParameter("name", "%" + name + "%");
            products.addAll(query.list());
        };
        executeInsideTransaction(action);
        return products;
    }

    List<Product> findByPrice(int price, int delta) {
        List<Product> products = new ArrayList<>();

        Consumer<Session> action = session -> {
            Query<Product> query = session.createQuery(SELECT_PRODUCTS_BY_PRICE, Product.class)
                    .setParameter("from", price - delta)
                    .setParameter("to", price + delta);
            products.addAll(query.list());
        };
        executeInsideTransaction(action);
        return products;
    }

    List<Product> findByNameSortedAsc(String name) {
        List<Product> products = new ArrayList<>();

        Consumer<Session> action = session -> {
            Query<Product> query = session.createQuery(SELECT_PRODUCTS_BY_NAME_ASC_ORDER, Product.class)
                    .setParameter("name", "%" + name + "%");
            products.addAll(query.list());
        };
        executeInsideTransaction(action);
        return products;
    }

    List<Product> findByNameSortedDesc(String name) {
        List<Product> products = new ArrayList<>();

        Consumer<Session> action = session -> {
            Query<Product> query = session.createQuery(SELECT_PRODUCTS_BY_NAME_DESC_ORDER, Product.class)
                    .setParameter("name", "%" + name + "%");
            products.addAll(query.list());
        };
        executeInsideTransaction(action);
        return products;
    }

    List<Product> findByPriceSortedDesc(int price, int delta) {
        List<Product> products = new ArrayList<>();

        Consumer<Session> action = session -> {
            Query<Product> query = session.createQuery(SELECT_PRODUCTS_BY_PRICE_DESC_ORDER, Product.class)
                    .setParameter("from", price - delta)
                    .setParameter("to", price + delta);
            products.addAll(query.list());
        };
        executeInsideTransaction(action);
        return products;
    }

    private static void executeInsideTransaction(Consumer<Session> action) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            action.accept(session);

            //close session/transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }
    }

    private static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
