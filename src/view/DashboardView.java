package view;

import business.CustomerController;
import core.Utils;
import entity.Customer;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DashboardView extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "Müşteri Yönetim Ekranı";
    private final User user;
    private final CustomerController customerController = new CustomerController();

    private JPanel container;
    private JLabel txt_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JPanel pnl_customer_filter;
    private JTextField txt_f_customer_name;
    private JComboBox cmb_f_customer_type;
    private JButton btn_customer_filter;
    private JButton btn_f_clear;
    private JButton btn_f_customer_new;
    private JLabel lbl_f_customer_name;
    private JLabel lbl_f_customer_type;
    private JTable tbl_customer;

    private JPopupMenu customerMenu = new JPopupMenu();

    public DashboardView(User user) {
        this.user = user;
        configureFrame();
        setComboBox();
        setListeners();
        loadCustomerTable(null);
        loadCustomerMenu();
    }

    private void configureFrame() {
        this.add(container);
        this.setTitle(FRAME_TITLE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        Utils.centerFrame(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.txt_welcome.setText("Hoşgeldin, " + user.getName());
    }

    private void setListeners() {
        this.btn_logout.addActionListener(e -> {
            handleLogout();
        });

        this.btn_f_clear.addActionListener(e -> {
            clearFilters();
        });

        this.btn_customer_filter.addActionListener(e -> {
            filterCustomers();
        });

        this.btn_f_customer_new.addActionListener(e -> {
            // TODO: new CustomerView();
        });
    }

    private void setComboBox() {
        DefaultComboBoxModel<String> customerTypes = new DefaultComboBoxModel<>();
        customerTypes.addElement("Hepsi");
        customerTypes.addElement("Bireysel");
        customerTypes.addElement("Kurumsal");
        this.cmb_f_customer_type.setModel(customerTypes);
    }

    private void handleLogout() {
        this.dispose();
        new LoginView();
    }

    private void clearFilters() {
        this.txt_f_customer_name.setText("");
        this.cmb_f_customer_type.setSelectedIndex(0);

        loadCustomerTable(null);
    }

    private void filterCustomers() {
        String name = this.txt_f_customer_name.getText();
        Customer.CustomerType type = switch (this.cmb_f_customer_type.getSelectedIndex()) {
            case 1 -> Customer.CustomerType.INDIVIDUAL;
            case 2 -> Customer.CustomerType.CORPORATE;
            default -> null;
        };
        if (type == null && name.isEmpty()) {
            loadCustomerTable(null);
            return;
        }

        ArrayList<Customer> customers = customerController.filterCustomers(name, type);
        loadCustomerTable(customers);
    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        if (customers == null) {
            customers = customerController.getCustomers();
        }

        String[] columnNames = {"ID", "Ad", "Müşteri Tipi", "Telefon", "E-Mail", "Adres"};

        Object[][] data = new Object[customers.size()][6];

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = Utils.translateCustomerType(customer.getType().name());
            data[i][3] = customer.getPhone();
            data[i][4] = customer.getEmail();
            data[i][5] = customer.getAddress();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        this.tbl_customer.setModel(model);

        this.tbl_customer.getColumnModel().getColumn(0).setMinWidth(50);
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_customer.getColumnModel().getColumn(2).setMinWidth(100);
        this.tbl_customer.getColumnModel().getColumn(2).setMaxWidth(100);

        this.tbl_customer.getTableHeader().setReorderingAllowed(false);

        this.tbl_customer.setDefaultEditor(Object.class, null);
    }

    private void loadCustomerMenu() {
        JMenuItem itemUpdate = new JMenuItem("Güncelle");
        JMenuItem itemDelete = new JMenuItem("Sil");

        customerMenu.add(itemUpdate);
        customerMenu.add(itemDelete);

        tbl_customer.setComponentPopupMenu(customerMenu);

        itemUpdate.addActionListener(e -> {
            int selectedRow = this.tbl_customer.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int customerId = (int) this.tbl_customer.getValueAt(selectedRow, 0);
            Customer customer = customerController.getCustomer(customerId);
            // TODO: new CustomerView(customer);
        });

        itemDelete.addActionListener(e -> {
            int selectedRow = this.tbl_customer.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int customerId = (int) this.tbl_customer.getValueAt(selectedRow, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Müşteriyi silmek istediğinize emin misiniz?", "Müşteri Sil", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
            customerController.deleteCustomer(customerId);
            filterCustomers();
        });
    }

}
