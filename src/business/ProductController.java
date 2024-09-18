package business;

import dao.ProductDAO;
import entity.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductDAO productDAO;

    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    public Product getProduct(int id) {
        return productDAO.getProduct(id);
    }

    public Product getProduct(String code) {
        return productDAO.getProduct(code);
    }

    public ArrayList<Product> getProducts() {
        return productDAO.getProducts();
    }

    public ArrayList<Product> filterProducts(String name, String code, int stock) {
        return productDAO.filterProducts(name, code, stock);
    }

    public void addProduct(String name, String code, int price, int stock) {
        productDAO.addProduct(name, code, price, stock);
    }

    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }

    public void deleteProduct(Product product) {
        productDAO.deleteProduct(product);
    }

}
