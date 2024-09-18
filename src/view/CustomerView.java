package view;

import business.CustomerController;
import core.Utils;
import entity.Customer;

import javax.swing.*;

public class CustomerView extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "Müşteri Düzenleme Ekranı";
    private final Customer customer;
    private final DashboardView dashboardView;
    private final CustomerController customerController = new CustomerController();

    private JPanel container;
    private JLabel lbl_customer;
    private JLabel lbl_name;
    private JTextField txt_name;
    private JLabel lbl_type;
    private JComboBox cmb_type;
    private JLabel lbl_email;
    private JTextField txt_email;
    private JLabel lbl_phone;
    private JTextField txt_phone;
    private JLabel lbl_address;
    private JTextField txt_address;
    private JPanel pnl_bottom;
    private JPanel pnl_top;
    private JButton btn_save;

    public CustomerView(DashboardView dashboardView) {
        this.customer = null;
        this.dashboardView = dashboardView;
        configureFrame();
        setFields();
    }

    public CustomerView(DashboardView dashboardView, Customer customer) {
        this.customer = customer;
        this.dashboardView = dashboardView;
        configureFrame();
        setFields();
    }

    private void configureFrame() {
        this.add(container);
        this.setTitle(FRAME_TITLE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        Utils.centerFrame(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void setFields() {
        cmb_type.addItem("Bireysel");
        cmb_type.addItem("Kurumsal");

        if (customer != null) {
            txt_name.setText(customer.getName());
            cmb_type.setSelectedItem(Utils.translateCustomerType(customer.getType().toString()));
            txt_email.setText(customer.getEmail());
            txt_phone.setText(customer.getPhone());
            txt_address.setText(customer.getAddress());
        }

        btn_save.addActionListener(e -> {
            saveCustomer();
        });
    }

    private void saveCustomer() {
        String name = txt_name.getText();
        String type = cmb_type.getSelectedItem().toString();
        String email = txt_email.getText();
        String phone = txt_phone.getText();
        String address = txt_address.getText();

        if (name.isEmpty() || email.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz.");
            return;
        }

        Customer.CustomerType customerType = type.equals("Bireysel") ? Customer.CustomerType.INDIVIDUAL : Customer.CustomerType.CORPORATE;

        if (customer != null) {
            customerController.updateCustomer(new Customer(customer.getId(), name, customerType, email, phone, address));
            JOptionPane.showMessageDialog(this, "Müşteri başarıyla güncellendi.");
            this.dispose();
            dashboardView.filterCustomers();
            return;
        }
        customerController.addCustomer(name, customerType, email, phone, address);
        JOptionPane.showMessageDialog(this, "Müşteri başarıyla kaydedildi.");
        dashboardView.filterCustomers();
        this.dispose();
    }
}
