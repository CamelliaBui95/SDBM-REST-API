package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.security.User;
import lombok.Data;

public class TestUtils {
    private static String login = "Admin";
    private static String password = "admin";
    private static User user;

    private TestUtils() {

    }

    public static User getUser() {
        if(user == null) {
            user = User.builder()
                    .login(login)
                    .password(password)
                    .build();
        }

        return user;
    }
}
