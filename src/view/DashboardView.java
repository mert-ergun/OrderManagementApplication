package view;

import entity.User;

import javax.swing.*;

public class DashboardView extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "Müşteri Yönetim Ekranı";

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
        
    }
}
