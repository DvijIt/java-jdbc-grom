package hibernate.lesson4.service;

import hibernate.lesson4.DAO.UserDAO;
import hibernate.lesson4.entity.User;
import hibernate.lesson4.utils.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import static hibernate.lesson4.utils.SessionFactoryCreator.createSessionFactory;

public class UserService {
    UserDAO userDAO = new UserDAO();

    public void registerUser(User user) {
        userDAO.registerUser(user);
    }

    public void login(String userName, String userPassword) {
        userDAO.login(userName, userPassword);
    }

    public void logout() {
        userDAO.logout();
    }
}
