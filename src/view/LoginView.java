package view;

import business.UserController;
import core.Utils;
import entity.User;

import javax.swing.*;

public class LoginView extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;
    private static final String FRAME_TITLE = "Kullanıcı Giriş Ekranı";

    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_top;
    private JPanel pnl_btm;
    private JLabel lbl_mail;
    private JTextField txt_mail;
    private JButton btn_logn;
    private JLabel lbl_pswd;
    private JPasswordField txt_paswd;

    private final UserController userController;

    public LoginView() {
        this.userController = new UserController();
        configureFrame();
        setupListeners();
    }

    private void configureFrame() {
        this.add(container);
        this.setTitle(FRAME_TITLE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        Utils.centerFrame(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setupListeners() {
        this.btn_logn.addActionListener(e -> {
            handleLogin();
        });
    }

    private void handleLogin() {
        String email = txt_mail.getText();
        char[] password = txt_paswd.getPassword();

        if (Utils.isEmailValid(email) && Utils.isPasswordValid(password)) {
            User user = userController.login(email, String.valueOf(password));
            if (user != null) {
                openDashboard(user);
            } else {
                Utils.showErrorMessage("Kullanıcı Bulunamadı!", "HATA!", this);
            }
        } else if (!Utils.isEmailValid(email)) {
            Utils.showErrorMessage("Lütfen geçerli bir email adresi giriniz!", "HATA!", this);
        } else if (!Utils.isPasswordValid(password)) {
            Utils.showErrorMessage("Şifre en az 4 karakter olmalıdır!", "HATA!", this);
        }
    }

    private void openDashboard(User user) {
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            // Show a message dialog with the user's name
            JOptionPane.showMessageDialog(this, "Hoşgeldin " + user.getName(), "Başarılı!", JOptionPane.INFORMATION_MESSAGE);
            new DashboardView(user);
        });
    }
}
