package view;

import business.CartController;
import business.OrderController;
import core.Utils;
import entity.Cart;
import entity.Customer;
import entity.Product;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderView extends JFrame {
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 400;
    private static final String FRAME_TITLE = "Sipariş Oluşturma Ekranı";
    private final DashboardView dashboardView;
    private final Customer customer;
    private final CartController cartController = new CartController();
    private final OrderController orderController = new OrderController();

    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_customer;
    private JLabel lbl_date;
    private JTextField txt_date;
    private JLabel lbl_note;
    private JTextArea txt_note;
    private JButton btn_save;

    public OrderView(DashboardView dashboardView, Customer customer) {
        this.dashboardView = dashboardView;
        this.customer = customer;
        configureFrame();
        setFields();
    }

    private void configureFrame() {
        this.setTitle(FRAME_TITLE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        Utils.centerFrame(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setContentPane(container);
    }

    private void setFields() {
        lbl_customer.setText("Müşteri: " + customer.getName());
        try {
            txt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        txt_date.setText(Utils.getCurrentDate());

        btn_save.addActionListener(e -> saveOrder());
    }

    private void saveOrder() {
        LocalDate date = txt_date.getText().isEmpty() ? LocalDate.now() : Utils.stringToLocalDate(txt_date.getText());
        String note = txt_note.getText();
        ArrayList<Cart> carts = cartController.getCarts();

        for (Cart cart : carts) {
            orderController.saveOrder(customer.getId(), cart.getProduct().getId(), cart.getProduct().getPrice(), date, note);

            Product product = cart.getProduct();
            product.setStock(product.getStock() - 1);
        }

        JOptionPane.showMessageDialog(this, "Siparişler başarıyla kaydedildi.");
        cartController.clearCart();

        this.dispose();
    }
}
