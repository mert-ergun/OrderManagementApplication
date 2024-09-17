package business;

import core.Utils;
import dao.UserDAO;
import entity.User;

public class UserController {
    private final UserDAO userDAO = new UserDAO();

    public User login(String email, String password) {
        if (Utils.isEmailValid(email) && Utils.isPasswordValid(password.toCharArray())) {
            return userDAO.getUser(email, password);
        } else {
            return null;
        }
    }
}
