package business;

import dao.CustomerDAO;
import entity.Customer;

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

    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerDAO.deleteCustomer(customer);
    }
}
