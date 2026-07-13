// util/MessageUtil.java
package hdkmanagement.util;

import javax.swing.JOptionPane;

public class MessageUtil {
    
    // Thông báo thông tin
    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Thông báo cảnh báo
    public static void showWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }
    
    // Thông báo lỗi
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    // Xác nhận
    public static boolean showConfirm(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Xác nhận", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    // Xác nhận với tùy chọn
    public static int showConfirmWithOptions(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, 
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    
    // Nhập liệu
    public static String showInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message, "Nhập dữ liệu", JOptionPane.QUESTION_MESSAGE);
    }
    
    public static String showInputDialog(String message, String defaultValue) {
        return JOptionPane.showInputDialog(null, message, defaultValue);
    }
    
    // Chọn từ danh sách
    public static String showOptionDialog(String message, Object[] options, String title) {
        int result = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (result >= 0) {
            return options[result].toString();
        }
        return null;
    }
}