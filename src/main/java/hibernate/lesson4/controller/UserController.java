package hibernate.lesson4.controller;

import hibernate.lesson4.entity.User;
import hibernate.lesson4.service.UserService;

public class UserController {
    private static UserService USER_SERVICE = new UserService();


    private void registerUser(User user) {
        USER_SERVICE.registerUser(user);
    }

    private void login(String userName, String userPassword) {
        USER_SERVICE.login(userName, userPassword);
    }

    private void logout() {
        USER_SERVICE.logout();
    }
}
