package view;

import business.CartController;
import business.CustomerController;
import business.OrderController;
import business.ProductController;
import core.Utils;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DashboardView extends JFrame {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "Müşteri Yönetim Ekranı";
    private final User user;

    private final CustomerController customerController = new CustomerController();
    private final ProductController productController = new ProductController();
    private final CartController cartController = new CartController();
    private final OrderController orderController = new OrderController();

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
    private JPanel pnl_product;
    private JScrollPane scrl_product;
    private JTable tbl_product;
    private JLabel lbl_f_product_name;
    private JTextField txt_f_product_name;
    private JLabel lbl_f_product_code;
    private JTextField txt_f_product_code;
    private JLabel lbl_f_product_stock;
    private JComboBox cmb_f_product_stock;
    private JButton btn_product_filter;
    private JButton btn_f_clear_product;
    private JButton btn_f_product_new;
    private JPanel pnl_cart;
    private JPanel pnl_cart_top;
    private JScrollPane scrl_cart;
    private JLabel lbl_cart_selectcustomer;
    private JLabel lbl_cart_price;
    private JLabel lbl_cart_count;
    private JComboBox cmb_cart_customer;
    private JLabel lbl_cart_price_txt;
    private JLabel lbl_cart_count_txt;
    private JButton btn_cart_clear;
    private JButton btn_cart_create;
    private JTable tbl_cart;
    private JPanel pnl_order;
    private JScrollPane scrl_order;
    private JTable tbl_order;

    private JPopupMenu customerMenu = new JPopupMenu();
    private JPopupMenu productMenu = new JPopupMenu();
    private JPopupMenu cartMenu = new JPopupMenu();
    private JPopupMenu orderMenu = new JPopupMenu();

    public DashboardView(User user) {
        this.user = user;
        configureFrame();
        setComboBoxCustomer();
        setComboBoxProduct();
        setComboBoxCart();
        setListeners();
        loadCustomerTable(null);
        loadProductTable(null);
        loadCartTable(null);
        loadOrderTable(null);
        loadCustomerMenu();
        loadProductMenu();
        loadCartMenu();
        loadOrderMenu();
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
            clearFiltersCustomer();
        });

        this.btn_customer_filter.addActionListener(e -> {
            filterCustomers();
        });

        this.btn_f_customer_new.addActionListener(e -> {
            CustomerView customerView = new CustomerView(this);
            customerView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    filterCustomers();
                }
            });
        });

        this.btn_f_clear_product.addActionListener(e -> {
            clearFiltersProduct();
        });

        this.btn_product_filter.addActionListener(e -> {
            filterProducts();
        });

        this.btn_f_product_new.addActionListener(e -> {
            ProductView productView = new ProductView(this);
            productView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    filterProducts();
                    setComboBoxCart();
                }
            });
        });

        this.btn_cart_clear.addActionListener(e -> {
            cartController.clearCart();
            JOptionPane.showMessageDialog(this, "Sepet temizlendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            loadCartTable(null);
        });

        this.btn_cart_create.addActionListener(e -> {
            int selectedCustomerIndex = this.cmb_cart_customer.getSelectedIndex();
            if (selectedCustomerIndex == 0) {
                JOptionPane.showMessageDialog(this, "Lütfen müşteri seçiniz.", "Müşteri Seçimi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int customerId = customerController.getCustomers().get(selectedCustomerIndex - 1).getId();
            ArrayList<Cart> carts = cartController.getCarts();
            if (carts.size() == 0) {
                JOptionPane.showMessageDialog(this, "Sepetinizde ürün bulunmamaktadır.", "Sepet Boş", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int totalPrice = 0;
            for (Cart cart : carts) {
                Product product = productController.getProduct(cart.getProductId());
                totalPrice += product.getPrice();
            }

            int choice = JOptionPane.showConfirmDialog(this, "Toplam tutar: " + totalPrice + " TL\nSatışı gerçekleştirmek istediğinize emin misiniz?", "Satış", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            OrderView orderView = new OrderView(this, customerController.getCustomer(customerId));
            orderView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    loadCartTable(null);
                    loadOrderTable(null);
                }
            });
        });
    }

    private void setComboBoxCustomer() {
        DefaultComboBoxModel<String> customerTypes = new DefaultComboBoxModel<>();
        customerTypes.addElement("Hepsi");
        customerTypes.addElement("Bireysel");
        customerTypes.addElement("Kurumsal");
        this.cmb_f_customer_type.setModel(customerTypes);
    }

    private void setComboBoxProduct() {
        DefaultComboBoxModel<String> stockTypes = new DefaultComboBoxModel<>();
        stockTypes.addElement("Hepsi");
        stockTypes.addElement("Stokta Var");
        stockTypes.addElement("Stokta Yok");
        this.cmb_f_product_stock.setModel(stockTypes);
    }

    private void setComboBoxCart() {
        DefaultComboBoxModel<String> customers = new DefaultComboBoxModel<>();
        ArrayList<Customer> customerList = customerController.getCustomers();
        customers.addElement("Müşteri Seçiniz");
        for (Customer customer : customerList) {
            customers.addElement(customer.getName());
        }
        this.cmb_cart_customer.setModel(customers);
    }

    private void handleLogout() {
        this.dispose();
        new LoginView();
    }

    private void clearFiltersCustomer() {
        this.txt_f_customer_name.setText("");
        this.cmb_f_customer_type.setSelectedIndex(0);

        loadCustomerTable(null);
    }

    private void clearFiltersProduct() {
        this.txt_f_product_name.setText("");
        this.txt_f_product_code.setText("");
        this.cmb_f_product_stock.setSelectedIndex(0);

        loadProductTable(null);
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

    private void filterProducts() {
        String name = this.txt_f_product_name.getText();
        String code = this.txt_f_product_code.getText();
        int stockType = this.cmb_f_product_stock.getSelectedIndex();
        if (stockType == 0 && name.isEmpty() && code.isEmpty()) {
            loadProductTable(null);
            return;
        }

        ArrayList<Product> products = productController.filterProducts(name, code, stockType);
        loadProductTable(products);
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

        this.tbl_customer.setAutoCreateRowSorter(true);
        this.tbl_customer.getRowSorter().toggleSortOrder(0);

        this.tbl_customer.setDefaultEditor(Object.class, null);
    }

    private void loadProductTable(ArrayList<Product> products) {
        if (products == null) {
            products = productController.getProducts();
        }

        String[] columnNames = {"ID", "Ad", "Kod", "Fiyat", "Stok"};

        Object[][] data = new Object[products.size()][5];

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getId();
            data[i][1] = product.getName();
            data[i][2] = product.getCode();
            data[i][3] = product.getPrice();
            data[i][4] = product.getStock();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        this.tbl_product.setModel(model);

        this.tbl_product.getColumnModel().getColumn(0).setMinWidth(50);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.getColumnModel().getColumn(3).setMinWidth(100);
        this.tbl_product.getColumnModel().getColumn(3).setMaxWidth(100);
        this.tbl_product.getColumnModel().getColumn(4).setMinWidth(50);
        this.tbl_product.getColumnModel().getColumn(4).setMaxWidth(50);

        this.tbl_product.getTableHeader().setReorderingAllowed(false);

        this.tbl_product.setAutoCreateRowSorter(true);
        this.tbl_product.getRowSorter().toggleSortOrder(0);

        this.tbl_product.setDefaultEditor(Object.class, null);
    }

    private void loadCartTable(ArrayList<Cart> carts) {
        String[] columnNames = {"ID", "Ürün Adı", "Ürün Kodu", "Fiyat", "Stok"};

        if (carts == null) {
            carts = cartController.getCarts();
        }

        Object[][] data = new Object[carts.size()][5];

        int totalPrice = 0;
        int cartCount = 0;

        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            Product product = productController.getProduct(cart.getProductId());
            data[i][0] = cart.getId();
            data[i][1] = product.getName();
            data[i][2] = product.getCode();
            data[i][3] = product.getPrice();
            data[i][4] = product.getStock();
            totalPrice += product.getPrice();
            cartCount++;
        }

        lbl_cart_price_txt.setText(String.valueOf(totalPrice) + " TL");
        lbl_cart_count_txt.setText(String.valueOf(cartCount) + " Adet");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        this.tbl_cart.setModel(model);

        this.tbl_cart.getColumnModel().getColumn(0).setMinWidth(50);
        this.tbl_cart.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_cart.getColumnModel().getColumn(3).setMinWidth(100);
        this.tbl_cart.getColumnModel().getColumn(3).setMaxWidth(100);
        this.tbl_cart.getColumnModel().getColumn(4).setMinWidth(50);
        this.tbl_cart.getColumnModel().getColumn(4).setMaxWidth(50);

        this.tbl_cart.getTableHeader().setReorderingAllowed(false);

        this.tbl_cart.setAutoCreateRowSorter(true);

        this.tbl_cart.setDefaultEditor(Object.class, null);
    }

    private void loadOrderTable(ArrayList<Order> orders) {
        String[] columnNames = {"ID", "Müşteri", "Ürün", "Fiyat", "Tarih", "Not"};

        if (orders == null) {
            orders = orderController.getOrders();
        }

        Object[][] data = new Object[orders.size()][6];

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Customer customer = customerController.getCustomer(order.getCustomerId());
            Product product = productController.getProduct(order.getProductId());
            data[i][0] = order.getId();
            data[i][1] = customer.getName();
            data[i][2] = product.getName();
            data[i][3] = product.getPrice();
            data[i][4] = order.getDate();
            data[i][5] = order.getNote();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        this.tbl_order.setModel(model);

        this.tbl_order.getColumnModel().getColumn(0).setMinWidth(50);
        this.tbl_order.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_order.getColumnModel().getColumn(3).setMinWidth(100);
        this.tbl_order.getColumnModel().getColumn(3).setMaxWidth(100);
        this.tbl_order.getColumnModel().getColumn(4).setMinWidth(100);
        this.tbl_order.getColumnModel().getColumn(4).setMaxWidth(100);
        this.tbl_order.getColumnModel().getColumn(5).setMinWidth(200);


        this.tbl_order.getTableHeader().setReorderingAllowed(false);

        this.tbl_order.setAutoCreateRowSorter(true);

        this.tbl_order.setDefaultEditor(Object.class, null);
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
            CustomerView customerView = new CustomerView(this, customer);
            customerView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    filterCustomers();
                    setComboBoxCart();
                }
            });
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
            setComboBoxCart();
            filterCustomers();
        });
    }

    private void loadProductMenu() {
        JMenuItem itemUpdate = new JMenuItem("Güncelle");
        JMenuItem itemDelete = new JMenuItem("Sil");
        JMenuItem itemAddToCart = new JMenuItem("Sepete Ekle");

        productMenu.add(itemUpdate);
        productMenu.add(itemDelete);
        productMenu.add(itemAddToCart);

        tbl_product.setComponentPopupMenu(productMenu);

        itemUpdate.addActionListener(e -> {
            int selectedRow = this.tbl_product.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int productId = (int) this.tbl_product.getValueAt(selectedRow, 0);
            Product product = productController.getProduct(productId);

            ProductView productView = new ProductView(this, product);
            productView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                      filterProducts();
                      loadCartTable(null);
                }
            });
        });

        itemDelete.addActionListener(e -> {
            int selectedRow = this.tbl_product.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int productId = (int) this.tbl_product.getValueAt(selectedRow, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Ürünü silmek istediğinize emin misiniz?", "Ürün Sil", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
            productController.deleteProduct(productId);
            loadCartTable(null);
            filterProducts();
        });

        itemAddToCart.addActionListener(e -> {
            int selectedRow = this.tbl_product.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int productId = (int) this.tbl_product.getValueAt(selectedRow, 0);
            Product product = productController.getProduct(productId);

            if (product.getStock() == 0) {
                JOptionPane.showMessageDialog(this, "Stokta ürün bulunmamaktadır.", "Stok Hatası", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cart cart = new Cart(productId);
            cartController.saveCart(cart);
            loadCartTable(null);

            JOptionPane.showMessageDialog(this, "Ürün sepete eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void loadCartMenu() {
        JMenuItem itemDelete = new JMenuItem("Sil");

        cartMenu.add(itemDelete);
        tbl_cart.setComponentPopupMenu(cartMenu);

        itemDelete.addActionListener(e -> {
            int selectedRow = this.tbl_cart.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int cartId = (int) this.tbl_cart.getValueAt(selectedRow, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Ürünü silmek istediğinize emin misiniz?", "Ürün Sil", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
            cartController.deleteCart(cartId);
            loadCartTable(null);
        });
    }

    private void loadOrderMenu() {
        JMenuItem itemDelete = new JMenuItem("Sil");

        orderMenu.add(itemDelete);
        tbl_order.setComponentPopupMenu(orderMenu);

        itemDelete.addActionListener(e -> {
            int selectedRow = this.tbl_order.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int orderId = (int) this.tbl_order.getValueAt(selectedRow, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Siparişi silmek istediğinize emin misiniz?", "Sipariş Sil", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
            orderController.deleteOrder(orderId);
            loadOrderTable(null);
        });
    }

}
