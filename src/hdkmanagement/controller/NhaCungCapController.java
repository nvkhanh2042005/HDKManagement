// controller/NhaCungCapController.java
package hdkmanagement.controller;

import hdkmanagement.dao.NhaCungCapDAO;
import hdkmanagement.model.NhaCungCap;
import hdkmanagement.util.ValidateUtil;

import java.util.List;

public class NhaCungCapController extends BaseController {
    
    private NhaCungCapDAO nhaCungCapDAO;
    
    public NhaCungCapController() {
        super();
        this.nhaCungCapDAO = new NhaCungCapDAO();
    }
    
    // Lấy tất cả nhà cung cấp
    public List<NhaCungCap> getAllNhaCungCap() {
        try {
            return nhaCungCapDAO.getAll();
        } catch (Exception e) {
            showError("Lỗi lấy danh sách nhà cung cấp: " + e.getMessage());
            return null;
        }
    }
    
    // Lấy nhà cung cấp theo ID
    public NhaCungCap getNhaCungCapById(int id) {
        try {
            return nhaCungCapDAO.getById(id);
        } catch (Exception e) {
            showError("Lỗi lấy thông tin nhà cung cấp: " + e.getMessage());
            return null;
        }
    }
    
    // Thêm nhà cung cấp
    public boolean addNhaCungCap(NhaCungCap nhaCungCap) {
        try {
            if (!validateNhaCungCap(nhaCungCap)) {
                return false;
            }
            return nhaCungCapDAO.insert(nhaCungCap);
        } catch (Exception e) {
            showError("Lỗi thêm nhà cung cấp: " + e.getMessage());
            return false;
        }
    }
    
    // Cập nhật nhà cung cấp
    public boolean updateNhaCungCap(NhaCungCap nhaCungCap) {
        try {
            if (!validateNhaCungCap(nhaCungCap)) {
                return false;
            }
            return nhaCungCapDAO.update(nhaCungCap);
        } catch (Exception e) {
            showError("Lỗi cập nhật nhà cung cấp: " + e.getMessage());
            return false;
        }
    }
    
    // Xóa nhà cung cấp
    public boolean deleteNhaCungCap(int id) {
        try {
            if (!showConfirm("Bạn có chắc muốn xóa nhà cung cấp này?")) {
                return false;
            }
            return nhaCungCapDAO.delete(id);
        } catch (Exception e) {
            showError("Lỗi xóa nhà cung cấp: " + e.getMessage());
            return false;
        }
    }
    
    // Tìm kiếm nhà cung cấp
    public List<NhaCungCap> searchNhaCungCap(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return nhaCungCapDAO.getAll();
            }
            return nhaCungCapDAO.search(keyword);
        } catch (Exception e) {
            showError("Lỗi tìm kiếm nhà cung cấp: " + e.getMessage());
            return null;
        }
    }
    
    // Validate nhà cung cấp
    private boolean validateNhaCungCap(NhaCungCap nhaCungCap) {
        if (nhaCungCap.getTenNCC() == null || nhaCungCap.getTenNCC().trim().isEmpty()) {
            showError("Tên nhà cung cấp không được để trống!");
            return false;
        }
        
        if (nhaCungCap.getMaNCC_Code() == null || nhaCungCap.getMaNCC_Code().trim().isEmpty()) {
            showError("Mã nhà cung cấp không được để trống!");
            return false;
        }
        
        return true;
    }
}