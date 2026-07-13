// controller/BaseController.java
package hdkmanagement.controller;

import hdkmanagement.util.DatabaseConnection;
import hdkmanagement.util.SessionManager;
import hdkmanagement.util.MessageUtil;
import java.sql.Connection;

public abstract class BaseController {
    
    protected DatabaseConnection db;
    protected SessionManager session;
    protected Connection connection;
    
    public BaseController() {
        this.db = DatabaseConnection.getInstance();
        this.session = SessionManager.getInstance();
        this.connection = db.getConnection();
    }
    
    // Kiểm tra quyền
    protected boolean hasPermission(String permission) {
        return session.hasPermission(permission);
    }
    
    // Kiểm tra đăng nhập
    protected boolean isLoggedIn() {
        return session.isLoggedIn();
    }
    
    // Hiển thị thông báo
    protected void showInfo(String message) {
        MessageUtil.showInfo(message);
    }
    
    protected void showError(String message) {
        MessageUtil.showError(message);
    }
    
    protected void showWarning(String message) {
        MessageUtil.showWarning(message);
    }
    
    protected boolean showConfirm(String message) {
        return MessageUtil.showConfirm(message);
    }
}