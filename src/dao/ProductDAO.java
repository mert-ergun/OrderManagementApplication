package dao;

import core.Database;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
    private Connection connection;

    public ProductDAO() {
        this.connection = Database.getInstance();
    }

    private Product match(ResultSet rs) throws SQLException {
        return new Product(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("code"),
                rs.getInt("price"),
                rs.getInt("stock")
        );
    }

    public Product getProduct(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        Product product = null;
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = match(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    public Product getProduct(String code) {
        String query = "SELECT * FROM products WHERE code = ?";
        Product product = null;
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = match(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    public ArrayList<Product> getProducts() {
        String query = "SELECT * FROM products";
        ArrayList<Product> products = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                products.add(match(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public ArrayList<Product> filterProducts(String name, String code, int stock) {
        ArrayList<Product> products = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM products WHERE 1=1");

        if (!name.isEmpty()) {
            query.append(" AND name ILIKE ?");
        }
        if (!code.isEmpty()) {
            query.append(" AND code ILIKE ?");
        }
        if (stock == 1) {
            query.append(" AND stock > 0");
        } else if (stock == 2) {
            query.append(" AND stock = 0");
        }

        try {
            PreparedStatement ps = this.connection.prepareStatement(query.toString());
            int paramIndex = 1;

            if (!name.isEmpty()) {
                ps.setString(paramIndex++, "%" + name + "%");
            }
            if (!code.isEmpty()) {
                ps.setString(paramIndex++, "%" + code + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public void addProduct(String name, String code, int price, int stock) {
        String query = "INSERT INTO products (name, code, price, stock) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, code);
            ps.setInt(3, price);
            ps.setInt(4, stock);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, code = ?, price = ?, stock = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCode());
            ps.setInt(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProduct(Product product) {
        deleteProduct(product.getId());
    }


}
