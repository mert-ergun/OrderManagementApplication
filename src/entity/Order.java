package entity;

import java.time.LocalDate;

public class Order {
    private int id;
    private int customerId;
    private int productId;
    private int price;
    private LocalDate date;
    private String note;

    private Customer customer;
    private Product product;

    public Order() {
    }

    public Order(int id, int customerId, int productId, int price, LocalDate date, String note) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.price = price;
        this.date = date;
        this.note = note;
    }

    public Order(int id, int customerId, int productId, int price, LocalDate date, String note, Customer customer, Product product) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.price = price;
        this.date = date;
        this.note = note;
        this.customer = customer;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
