
package hibernate.lesson4.DAO;

import hibernate.lesson4.entity.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static hibernate.lesson4.utils.SessionFactoryCreator.createSessionFactory;

public class OrderDAO extends DAO<Order> {

    @Override
    public Order save(Order order) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(order);

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Save is done");

        return order;
    }

    @Override
    public void delete(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            Order order = session.load(Order.class, id);
            session.delete(order);

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Delete is done");
    }

    @Override
    public Order update(Order order) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            session.update(session.load(Order.class, order.getId()));

            //close session/tr
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Update is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Update is done");

        return order;
    }

    @Override
    public Order findById(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            Order order = session.find(Order.class, id);

            //close session/tr
            tr.commit();

            return order;
        } catch (HibernateException e) {
            System.err.println("Delete is failed");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

        System.out.println("Delete is done");

        return null;
    }

}
