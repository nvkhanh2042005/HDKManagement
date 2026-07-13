// HDKManagement.java
package hdkmanagement;

import hdkmanagement.view.auth.frmDangNhap;
import hdkmanagement.util.DatabaseConnection;
import hdkmanagement.util.MessageUtil;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class HDKManagement {
    
    public static void main(String[] args) {
        // Thiết lập giao diện Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Khởi tạo kết nối database
        DatabaseConnection db = DatabaseConnection.getInstance();
        
        // Kiểm tra kết nối
        if (db.isConnected()) {
            System.out.println("✅ Kết nối database thành công!");
            
            // Chạy form đăng nhập
            SwingUtilities.invokeLater(() -> {
                new frmDangNhap().setVisible(true);
            });
        } else {
            System.err.println("❌ Không thể kết nối database!");
            MessageUtil.showError("Không thể kết nối đến database!\nVui lòng kiểm tra XAMPP MySQL.");
            System.exit(1);
        }
    }
}