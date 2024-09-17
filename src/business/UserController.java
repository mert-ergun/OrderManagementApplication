package business;

import dao.UserDAO;
import entity.User;

public class UserController {
    private final UserDAO userDAO = new UserDAO();

    public User login(String email, String password) {
        return userDAO.getUser(email, password);
    }
}
