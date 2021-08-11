package hibernate.lesson3.DAO;

import hibernate.lesson3.entity.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HotelDAO extends DAO<Hotel> {

    @Override
    public Hotel save(Hotel hotel) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(hotel);

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

        return hotel;
    }

    @Override
    public void delete(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            Hotel hotel = session.load(Hotel.class, id);
            session.delete(hotel);

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
    public Hotel update(Hotel hotel) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            if (session.contains(hotel)) {
                session.refresh(hotel);
                System.out.println("refresh");
            }
            session.load(Hotel.class, hotel.getId());
            session.update(hotel);

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

        return hotel;
    }

    @Override
    public Hotel findById(long id) {
        Session session = null;
        Transaction tr = null;
        try {
            //create session/tr
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            Hotel hotel = session.load(Hotel.class, id);
            if (session.contains(hotel)) {
                session.refresh(hotel);
            }
            session.find(Hotel.class, id);

            //close session/tr
            tr.commit();

            return hotel;
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

        return null;
    }

}
