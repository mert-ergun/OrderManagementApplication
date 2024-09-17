package core;

public class Utils {
    public static void centerFrame(javax.swing.JFrame frame) {
        java.awt.Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isPasswordValid(char[] password) {
        return password.length >= 4;
    }
}
