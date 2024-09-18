package dao;

import core.Database;
import entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO() {
        this.connection = Database.getInstance();
    }

    public Customer match(ResultSet rs) throws SQLException {
        return new Customer(
            rs.getInt("id"),
            rs.getString("name"),
            Customer.CustomerType.valueOf(rs.getString("type")),
            rs.getString("phone"),
            rs.getString("email"),
            rs.getString("address")
        );
    }

    public Customer getCustomer(int id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        try {
            var ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Customer getCustomer(String name) {
        String query = "SELECT * FROM customers WHERE name = ?";
        try {
            var ps = connection.prepareStatement(query);
            ps.setString(1, name);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Customer> getCustomers() {
        String query = "SELECT * FROM customers";
        var customers = new ArrayList<Customer>();
        try {
            var ps = connection.prepareStatement(query);
            var rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public ArrayList<Customer> filterCustomers(String name, Customer.CustomerType type) {
        if (type == null) {
            String query = "SELECT * FROM customers WHERE name ILIKE ?";
            var customers = new ArrayList<Customer>();
            try {
                var ps = connection.prepareStatement(query);
                ps.setString(1, "%" + name + "%");
                var rs = ps.executeQuery();
                while (rs.next()) {
                    customers.add(match(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return customers;
        } else if (name.isEmpty()) {
            String query = "SELECT * FROM customers WHERE type = ?::\"CustomerType\"";
            var customers = new ArrayList<Customer>();
            try {
                var ps = connection.prepareStatement(query);
                ps.setString(1, type.name());
                var rs = ps.executeQuery();
                while (rs.next()) {
                    customers.add(match(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return customers;
        }

        String query = "SELECT * FROM customers WHERE name ILIKE ? AND type = ?::\"CustomerType\"";
        var customers = new ArrayList<Customer>();
        try {
            var ps = connection.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            ps.setString(2, type.name());
            var rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public void addCustomer(String name, Customer.CustomerType type, String email, String phone, String address) {
        String query = "INSERT INTO customers (name, type, phone, email, address) VALUES (?, ?::\"CustomerType\", ?, ?, ?)";
        try {
            var ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, type.name());
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET name = ?, type = ?::\"CustomerType\", phone = ?, email = ?, address = ? WHERE id = ?";
        try {
            var ps = connection.prepareStatement(query);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getType().name());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getAddress());
            ps.setInt(6, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(Customer customer) {
        String query = "DELETE FROM customers WHERE id = ?";
        try {
            var ps = connection.prepareStatement(query);
            ps.setInt(1, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(int id) {
        String query = "DELETE FROM customers WHERE id = ?";
        try {
            var ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}