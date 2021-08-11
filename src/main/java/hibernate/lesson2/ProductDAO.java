package hibernate.lesson2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        Product product = new Product();
        product.setName("New test");
        product.setPrice(555);
        product.setDescription("new descr");

        save(product);

        Product product1 = new Product();
        product1.setName("1231New");
        product1.setPrice(423);
        product1.setDescription("new 2323descr");

        Product product2 = new Product();
        product2.setName("New test43");
        product2.setPrice(100);
        product2.setDescription("new descrsfdf sdddf");

        Product product3 = new Product();
        product3.setName("New testwewe");
        product3.setPrice(90);
        product3.setDescription("new eesse descr");

        List<Product> products = Arrays.asList(product1, product2, product3);
        saveProducts(products);
    }

    public static void saveProducts(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            for (Product product : products) {
                session.save(product);
            }

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Save is done");
    }

    public static Product save(Product product) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(product);

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Save is done");

        return product;
    }

    public static void updateProducts(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            for (Product product : products) {
                session.update(product);
            }

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Update is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Update is done");
    }

    public static Product update(Product product) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.update(product);

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Update is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Update is done");

        return product;
    }

    public static void delete(long id) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            Product product = new Product();
            product.setId(id);
            session.delete(product);

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Delete is done");

    }

    public static void deleteAll(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            for (Product product : products) {
                session.delete(product);
            }

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        System.out.println("Delete is done");

    }

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

}
