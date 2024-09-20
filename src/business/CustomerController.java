package business;

import dao.CustomerDAO;
import entity.Customer;
import entity.Order;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDAO customerDAO = new CustomerDAO();

    public CustomerController() {
    }

    public Customer getCustomer(int id) {
        return customerDAO.getCustomer(id);
    }

    public Customer getCustomer(String name) {
        return customerDAO.getCustomer(name);
    }

    public ArrayList<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    public ArrayList<Customer> filterCustomers(String name, Customer.CustomerType type) {
        return customerDAO.filterCustomers(name, type);
    }

    public void addCustomer(String name, Customer.CustomerType type, String email, String phone, String address) {
        customerDAO.addCustomer(name, type, email, phone, address);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerDAO.deleteCustomer(customer);
    }

    public void deleteCustomer(int id) {
        OrderController orderController = new OrderController();
        orderController.deleteOrdersByCustomer(id);
        customerDAO.deleteCustomer(id);
    }
}
