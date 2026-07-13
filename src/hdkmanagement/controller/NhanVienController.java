// controller/NhanVienController.java
package hdkmanagement.controller;

import hdkmanagement.dao.NhanVienDAO;
import hdkmanagement.model.NhanVien;
import hdkmanagement.util.ValidateUtil;

import java.util.List;

public class NhanVienController extends BaseController {
    
    private NhanVienDAO nhanVienDAO;
    
    public NhanVienController() {
        super();
        this.nhanVienDAO = new NhanVienDAO();
    }
    
    // Lấy tất cả nhân viên
    public List<NhanVien> getAllNhanVien() {
        try {
            return nhanVienDAO.getAll();
        } catch (Exception e) {
            showError("Lỗi lấy danh sách nhân viên: " + e.getMessage());
            return null;
        }
    }
    
    // Lấy nhân viên theo ID
    public NhanVien getNhanVienById(int id) {
        try {
            return nhanVienDAO.getById(id);
        } catch (Exception e) {
            showError("Lỗi lấy thông tin nhân viên: " + e.getMessage());
            return null;
        }
    }
    
    // Thêm nhân viên
    public boolean addNhanVien(NhanVien nhanVien) {
        try {
            if (!validateNhanVien(nhanVien)) {
                return false;
            }
            return nhanVienDAO.insert(nhanVien);
        } catch (Exception e) {
            showError("Lỗi thêm nhân viên: " + e.getMessage());
            return false;
        }
    }
    
    // Cập nhật nhân viên
    public boolean updateNhanVien(NhanVien nhanVien) {
        try {
            if (!validateNhanVien(nhanVien)) {
                return false;
            }
            return nhanVienDAO.update(nhanVien);
        } catch (Exception e) {
            showError("Lỗi cập nhật nhân viên: " + e.getMessage());
            return false;
        }
    }
    
    // Xóa nhân viên
    public boolean deleteNhanVien(int id) {
        try {
            if (!showConfirm("Bạn có chắc muốn xóa nhân viên này?")) {
                return false;
            }
            return nhanVienDAO.delete(id);
        } catch (Exception e) {
            showError("Lỗi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }
    
    // Tìm kiếm nhân viên
    public List<NhanVien> searchNhanVien(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return nhanVienDAO.getAll();
            }
            return nhanVienDAO.search(keyword);
        } catch (Exception e) {
            showError("Lỗi tìm kiếm nhân viên: " + e.getMessage());
            return null;
        }
    }
    
    // Validate nhân viên
    private boolean validateNhanVien(NhanVien nhanVien) {
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            showError("Họ tên không được để trống!");
            return false;
        }
        
        if (nhanVien.getMaNV_Code() == null || nhanVien.getMaNV_Code().trim().isEmpty()) {
            showError("Mã nhân viên không được để trống!");
            return false;
        }
        
        if (nhanVien.getSdt() != null && !nhanVien.getSdt().trim().isEmpty()) {
            if (!ValidateUtil.isValidPhone(nhanVien.getSdt())) {
                showError("Số điện thoại không hợp lệ!");
                return false;
            }
        }
        
        return true;
    }
}