package hibernate.lesson2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ProductNativeSQLDAO {
    private static final String SELECT_BY_ID = "SELECT * FROM Products p WHERE p.id=:id";
    private static final String SELECT_PRODUCTS_BY_NAME = "SELECT * FROM Products p WHERE p.name=:name";
    private static final String SELECT_PRODUCTS_BY_NAME_LIKE = "SELECT * FROM Products p WHERE p.name like :name";
    private static final String SELECT_PRODUCTS_BY_NAME_ASC_ORDER = SELECT_PRODUCTS_BY_NAME_LIKE + " order by p.name asc";
    private static final String SELECT_PRODUCTS_BY_NAME_DESC_ORDER = SELECT_PRODUCTS_BY_NAME_LIKE +
            " order by p.name desc";
    private static final String SELECT_PRODUCTS_BY_PRICE = "FROM Products p WHERE p.price between :from and :to";
    private static final String SELECT_PRODUCTS_BY_PRICE_DESC_ORDER = SELECT_PRODUCTS_BY_PRICE + " order by p.price desc";

    private static SessionFactory sessionFactory;

    public Product findById(long id) {
        AtomicReference<Product> product = new AtomicReference<>();

        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<Product> query = session.createNativeQuery(SELECT_BY_ID, Product.class)
                    .setParameter("id", id);
            product.set(query.getSingleResult());

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return product.get();
    }

    List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<Product> query = session.createNativeQuery(SELECT_PRODUCTS_BY_NAME, Product.class)
                    .setParameter("name", name);
            products.addAll(query.list());

            //close session/transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Product with name: " + name + " not found");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }
        return products;
    }

    List<Product> findByContainedName(String name) {
        List<Product> products = new ArrayList<>();

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<Product> query = session.createNativeQuery(SELECT_PRODUCTS_BY_NAME_LIKE, Product.class)
                    .setParameter("name", "%" + name + "%");
            products.addAll(query.list());

            //close session/transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }
        return products;
    }

    List<Product> findByPrice(int price, int delta) {
        List<Product> products = new ArrayList<>();

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<Product> query = session
                    .createNativeQuery(SELECT_PRODUCTS_BY_PRICE, Product.class)
                    .setParameter("from", price - delta)
                    .setParameter("to", price + delta);
            products.addAll(query.list());

            //close session/transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }
        return products;
    }

    List<Product> findByNameSortedAsc(String name) {
        List<Product> products = new ArrayList<>();

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<Product> query = session
                    .createNativeQuery(SELECT_PRODUCTS_BY_NAME_ASC_ORDER, Product.class)
                    .setParameter("name", "%" + name + "%");
            products.addAll(query.list());

            //close session/transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        return products;
    }

    List<Product> findByNameSortedDesc(String name) {
        List<Product> products = new ArrayList<>();

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<Product> query = session.createNativeQuery(SELECT_PRODUCTS_BY_NAME_DESC_ORDER, Product.class)
                    .setParameter("name", "%" + name + "%");
            products.addAll(query.list());

            //close session/transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }
        return products;
    }

    List<Product> findByPriceSortedDesc(int price, int delta) {
        List<Product> products = new ArrayList<>();

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<Product> query = session.createNativeQuery(SELECT_PRODUCTS_BY_PRICE_DESC_ORDER, Product.class)
                    .setParameter("from", price - delta)
                    .setParameter("to", price + delta);
            products.addAll(query.list());

            //close session/transaction
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        return products;
    }


    private static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
