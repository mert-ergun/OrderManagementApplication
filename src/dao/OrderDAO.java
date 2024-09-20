package dao;

import core.Database;
import entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void saveOrder(int customerId, int productId, int price, String note) {
        String query = "INSERT INTO orders (customer_id, product_id, price, note) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            ps.setInt(3, price);
            ps.setString(4, note);
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
}
