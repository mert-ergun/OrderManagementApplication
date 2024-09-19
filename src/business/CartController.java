package business;

import dao.CartDAO;
import entity.Cart;

public class CartController {
    private final CartDAO cartDAO;

    public CartController() {
        this.cartDAO = new CartDAO();
    }

    public void saveCart(int productId) {
        cartDAO.saveCart(productId);
    }

    public void saveCart(Cart cart) {
        cartDAO.saveCart(cart);
    }

    public Cart getCart(int id) {
        return cartDAO.getCart(id);
    }
}
