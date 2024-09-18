package view;

import business.ProductController;
import entity.Product;
import core.Utils;

import javax.swing.*;

public class ProductView extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "Ürün Düzenleme Ekranı";
    private final Product product;
    private final DashboardView dashboardView;
    private final ProductController productController = new ProductController();

    private JPanel container;
    private JLabel lbl_product;
    private JPanel pnl_top;
    private JLabel lbl_name;
    private JTextField txt_name;
    private JLabel lbl_code;
    private JLabel lbl_price;
    private JLabel lbl_stock;
    private JTextField txt_price;
    private JTextField txt_stock;
    private JPanel pnl_bottom;
    private JButton btn_save;
    private JTextField txt_code;

    public ProductView(DashboardView dashboardView) {
        this.product = null;
        this.dashboardView = dashboardView;
        configureFrame();
        setFields();
    }

    public ProductView(DashboardView dashboardView, Product product) {
        this.product = product;
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
        if (product != null) {
            txt_name.setText(product.getName());
            txt_code.setText(product.getCode());
            txt_price.setText(String.valueOf(product.getPrice()));
            txt_stock.setText(String.valueOf(product.getStock()));
        }

        btn_save.addActionListener(e -> saveProduct());
    }

    private void saveProduct() {
        String name = txt_name.getText();
        String code = txt_code.getText();
        int price = Integer.parseInt(txt_price.getText());
        int stock = Integer.parseInt(txt_stock.getText());

        if (name.isBlank() || code.isBlank() || txt_price.getText().isBlank() || txt_stock.getText().isBlank()) {
            Utils.showErrorMessage("Lütfen tüm alanları doldurunuz.", "Hata", this);
            return;
        } else if (!Utils.isProductCodeValid(code)) {
            Utils.showErrorMessage("Ürün kodu en az 4 karakterden oluşmalıdır ve yalnızca harf, rakam ve tire içerebilir.", "Hata", this);
            return;
        }

        if (product == null) {
            productController.addProduct(name, code, price, stock);
            JOptionPane.showMessageDialog(this, "Ürün başarıyla eklendi.");
            this.dispose();
        } else {
            product.setName(name);
            product.setCode(code);
            product.setPrice(price);
            product.setStock(stock);
            productController.updateProduct(product);
            JOptionPane.showMessageDialog(this, "Ürün başarıyla güncellendi.");
            this.dispose();
        }
    }
}
