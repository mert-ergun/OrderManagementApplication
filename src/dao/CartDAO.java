package dao;

import core.Database;
import entity.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDAO {
    Connection connection;
    ProductDAO productDAO;

    public CartDAO() {
        connection = Database.getInstance();
        productDAO = new ProductDAO();
    }

    private Cart match(ResultSet rs) throws SQLException {
        return new Cart(rs.getInt("id"),
                rs.getInt("productId"),
                productDAO.getProduct(rs.getInt("productId"))
        );
    }

    public Cart getCart(int id) {
        String query = "SELECT * FROM carts WHERE id = ?";
        Cart cart = null;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cart = match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;
    }

    public void saveCart(Cart cart) {
        String query = "INSERT INTO carts (productId) VALUES (?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cart.getProductId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCart(int productId) {
        String query = "INSERT INTO carts (productId) VALUES (?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
