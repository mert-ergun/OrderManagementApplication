package dao;

import core.Database;
import entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAO {
    private Connection connection;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;

    public OrderDAO() {
        connection = Database.getInstance();
        productDAO = new ProductDAO();
        customerDAO = new CustomerDAO();
    }

    public Order match(ResultSet rs) throws SQLException {
        Order order = new Order(rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("product_id"),
                rs.getInt("price"),
                rs.getDate("date").toLocalDate(),
                rs.getString("note")
        );
        order.setCustomer(customerDAO.getCustomer(order.getCustomerId()));
        order.setProduct(productDAO.getProduct(order.getProductId()));

        return order;
    }

    public Order getOrder(int id) {
        String query = "SELECT * FROM orders WHERE id = ?";
        Order order = null;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public ArrayList<Order> getOrders() {
        String query = "SELECT * FROM orders";
        ArrayList<Order> orders = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    public void saveOrder(int customerId, int productId, int price, LocalDate date, String note) {
        String query = "INSERT INTO orders (customer_id, product_id, price, date, note) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            ps.setInt(3, price);
            ps.setDate(4, java.sql.Date.valueOf(date));
            ps.setString(5, note);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOrder(int id) {
        String query = "DELETE FROM orders WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOrdersByCustomer(int customerId) {
        String query = "DELETE FROM orders WHERE customer_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOrdersByProduct(int productId) {
        String query = "DELETE FROM orders WHERE product_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
