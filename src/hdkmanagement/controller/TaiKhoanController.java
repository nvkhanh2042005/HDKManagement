// controller/TaiKhoanController.java
package hdkmanagement.controller;

import hdkmanagement.dao.TaiKhoanDAO;
import hdkmanagement.dao.NhanVienDAO;
import hdkmanagement.dao.QuyenDAO;
import hdkmanagement.model.TaiKhoan;
import hdkmanagement.model.NhanVien;
import hdkmanagement.model.Quyen;
import hdkmanagement.util.PasswordUtil;

import java.util.List;

public class TaiKhoanController extends BaseController {
    
    private TaiKhoanDAO taiKhoanDAO;
    private NhanVienDAO nhanVienDAO;
    private QuyenDAO quyenDAO;
    
    public TaiKhoanController() {
        super();
        this.taiKhoanDAO = new TaiKhoanDAO();
        this.nhanVienDAO = new NhanVienDAO();
        this.quyenDAO = new QuyenDAO();
    }
    
    // Đăng nhập
    public TaiKhoan login(String username, String password) {
        try {
            return taiKhoanDAO.login(username, password);
        } catch (Exception e) {
            showError("Lỗi đăng nhập: " + e.getMessage());
            return null;
        }
    }
    
    // Lấy tất cả tài khoản
    public List<TaiKhoan> getAllTaiKhoan() {
        try {
            return taiKhoanDAO.getAll();
        } catch (Exception e) {
            showError("Lỗi lấy danh sách tài khoản: " + e.getMessage());
            return null;
        }
    }
    
    // Thêm tài khoản
    public boolean addTaiKhoan(TaiKhoan taiKhoan) {
        try {
            if (taiKhoan.getTenDangNhap() == null || taiKhoan.getTenDangNhap().trim().isEmpty()) {
                showError("Tên đăng nhập không được để trống!");
                return false;
            }
            
            if (taiKhoanDAO.isUsernameExists(taiKhoan.getTenDangNhap())) {
                showError("Tên đăng nhập đã tồn tại!");
                return false;
            }
            
            if (taiKhoan.getMatKhau() == null || taiKhoan.getMatKhau().trim().isEmpty()) {
                showError("Mật khẩu không được để trống!");
                return false;
            }
            
            return taiKhoanDAO.insert(taiKhoan);
        } catch (Exception e) {
            showError("Lỗi thêm tài khoản: " + e.getMessage());
            return false;
        }
    }
    
    // Cập nhật tài khoản
    public boolean updateTaiKhoan(TaiKhoan taiKhoan) {
        try {
            return taiKhoanDAO.update(taiKhoan);
        } catch (Exception e) {
            showError("Lỗi cập nhật tài khoản: " + e.getMessage());
            return false;
        }
    }
    
    // Đổi mật khẩu
    public boolean changePassword(int maTK, String oldPassword, String newPassword) {
        try {
            if (oldPassword == null || oldPassword.trim().isEmpty()) {
                showError("Mật khẩu cũ không được để trống!");
                return false;
            }
            
            if (newPassword == null || newPassword.trim().isEmpty()) {
                showError("Mật khẩu mới không được để trống!");
                return false;
            }
            
            if (newPassword.length() < 6) {
                showError("Mật khẩu mới phải có ít nhất 6 ký tự!");
                return false;
            }
            
            boolean result = taiKhoanDAO.changePassword(maTK, oldPassword, newPassword);
            if (result) {
                showInfo("Đổi mật khẩu thành công!");
            } else {
                showError("Mật khẩu cũ không đúng!");
            }
            return result;
        } catch (Exception e) {
            showError("Lỗi đổi mật khẩu: " + e.getMessage());
            return false;
        }
    }
    
    // Xóa tài khoản
    public boolean deleteTaiKhoan(int id) {
        try {
            if (!showConfirm("Bạn có chắc muốn xóa tài khoản này?")) {
                return false;
            }
            return taiKhoanDAO.delete(id);
        } catch (Exception e) {
            showError("Lỗi xóa tài khoản: " + e.getMessage());
            return false;
        }
    }
}