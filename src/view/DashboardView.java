package view;

import core.Utils;
import entity.Customer;
import entity.User;

import javax.swing.*;

public class DashboardView extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "Müşteri Yönetim Ekranı";
    private final User user;

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

    public DashboardView(User user) {
        this.user = user;
        configureFrame();
        setComboBox();
        setListeners();
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
    }

    private void filterCustomers() {
        String name = this.txt_f_customer_name.getText();
        Customer.CustomerType type = switch (this.cmb_f_customer_type.getSelectedIndex()) {
            case 1 -> Customer.CustomerType.INDIVIDUAL;
            case 2 -> Customer.CustomerType.CORPORATE;
            default -> null;
        };
        // TODO: Filter customers
    }



}
