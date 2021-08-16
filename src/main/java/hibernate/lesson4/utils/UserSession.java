package hibernate.lesson4.utils;

import hibernate.lesson4.entity.User;

import java.util.Optional;

public class UserSession {
    private static User authorizedUser;

    public static Optional<User> getAuthorizedUser() {
        return Optional.ofNullable(authorizedUser);
    }

    public static void login(User user) {
        if (authorizedUser == null) {
            authorizedUser = user;
        }
    }

    public static void logout() {
        authorizedUser = null;
    }

    public static boolean isAdmin() {
        return Optional.ofNullable(authorizedUser)
                .filter(user -> user.getUserType() == UserType.ADMIN)
                .isPresent();
    }

}
