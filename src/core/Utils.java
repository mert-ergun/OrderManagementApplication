package core;

import javax.swing.*;

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

    public static void showErrorMessage(String message, String title, JFrame parent) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static String translateCustomerType(String type) {
        return switch (type) {
            case "INDIVIDUAL" -> "Bireysel";
            case "CORPORATE" -> "Kurumsal";
            case "Bireysel" -> "INDIVIDUAL";
            case "Kurumsal" -> "CORPORATE";
            default -> "Belirtilmemiş";
        };
    }
}
