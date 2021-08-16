
package hibernate.lesson4.DAO;

import hibernate.lesson4.entity.Hotel;
import hibernate.lesson4.entity.User;
import hibernate.lesson4.utils.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.Optional;

import static hibernate.lesson4.utils.SessionFactoryCreator.createSessionFactory;

public class UserDAO extends DAO<User> {
    private static final String SELECT_USER_BY_NAME_PASSWORD = "SELECT * FROM USERS p WHERE p.name=:name AND p.password=:password";

    public User save(User user) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(user);

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

        return user;
    }

    @Override
    public void delete(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            User user = session.load(User.class, id);
            session.delete(user);

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
    public User update(User user) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            //create session/tr
            tr = session.getTransaction();
            tr.begin();

            //action
            session.update(session.load(User.class, user.getId()));

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

        return user;
    }

    @Override
    public User findById(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            User user = session.find(User.class, id);

            //close session/tr
            tr.commit();

            return user;
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

    public void registerUser(User user) {
        this.save(user);
    }

    public void login(String userName, String userPassword) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            //action
            NativeQuery<User> query = session.createNativeQuery(SELECT_USER_BY_NAME_PASSWORD, User.class)
                    .setParameter("name", userName)
                    .setParameter("password", userPassword);

            User user = new User();
            user = query.getSingleResult();

            UserSession.login(user);

            //close session/transaction
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("User with name: " + userName + " not found");
            System.err.println(e.getMessage());

            if (tr != null) {
                tr.rollback();
            }
        }

    }

    public void logout() {
        UserSession.logout();
    }

    private static boolean isAdmin() {
        return UserSession.isAdmin();
    }

    private static Optional<User> getAuthorizedUser() {
        return UserSession.getAuthorizedUser();
    }


}
