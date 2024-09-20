package dao;

import core.Database;
import entity.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDAO {
    private Connection connection;
    private ProductDAO productDAO;

    public CartDAO() {
        connection = Database.getInstance();
        productDAO = new ProductDAO();
    }

    private Cart match(ResultSet rs) throws SQLException {
        return new Cart(rs.getInt("id"),
                rs.getInt("product_id"),
                productDAO.getProduct(rs.getInt("product_id"))
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

    public ArrayList<Cart> getCarts() {
        String query = "SELECT * FROM carts";
        ArrayList<Cart> carts = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                carts.add(match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carts;
    }

    public void saveCart(Cart cart) {
        String query = "INSERT INTO carts (product_id) VALUES (?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cart.getProductId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCart(int productId) {
        String query = "INSERT INTO carts (product_id) VALUES (?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearCart() {
        String query = "DELETE FROM carts";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCart(int id) {
        String query = "DELETE FROM carts WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCartsByProduct(int productId) {
        String query = "DELETE FROM carts WHERE product_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
