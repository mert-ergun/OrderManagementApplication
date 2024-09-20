package business;

import dao.OrderDAO;
import entity.Order;

import java.util.ArrayList;

public class OrderController {
    private final OrderDAO orderDAO;

    public OrderController() {
        this.orderDAO = new OrderDAO();
    }

    public Order getOrder(int id) {
        return orderDAO.getOrder(id);
    }

    public ArrayList<Order> getOrders() {
        return orderDAO.getOrders();
    }

    public void saveOrder(int customerId, int productId, int price, String note) {
        orderDAO.saveOrder(customerId, productId, price, note);
    }

    public void deleteOrder(int id) {
        orderDAO.deleteOrder(id);
    }
}
