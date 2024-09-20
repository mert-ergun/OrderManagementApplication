package entity;

public class Cart {
    private int id;
    private int productId;
    private Product product;

    public Cart() {
    }

    public Cart(int id, int productId, Product product) {
        this.id = id;
        this.productId = productId;
        this.product = product;
    }

    public Cart(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
