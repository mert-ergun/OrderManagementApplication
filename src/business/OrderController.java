package business;

import dao.OrderDAO;
import entity.Order;

public class OrderController {
    private final OrderDAO orderDAO;

    public OrderController() {
        this.orderDAO = new OrderDAO();
    }

    public Order getOrder(int id) {
        return orderDAO.getOrder(id);
    }

    public void saveOrder(int customerId, int productId, int price, String note) {
        orderDAO.saveOrder(customerId, productId, price, note);
    }

    public void deleteOrder(int id) {
        orderDAO.deleteOrder(id);
    }
}
