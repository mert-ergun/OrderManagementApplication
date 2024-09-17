package dao;

import core.Database;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        this.connection = Database.getInstance();
    }

    public User match(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

    public User getUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        User user = null;
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = match(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public ArrayList<User> getAllUsers() {
        String query = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = match(rs);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
