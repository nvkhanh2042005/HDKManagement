// controller/DanhMucController.java
package hdkmanagement.controller;

import hdkmanagement.dao.DanhMucDAO;
import hdkmanagement.model.DanhMuc;

import java.util.List;

public class DanhMucController extends BaseController {
    
    private DanhMucDAO danhMucDAO;
    
    public DanhMucController() {
        super();
        this.danhMucDAO = new DanhMucDAO();
    }
    
    // Lấy tất cả danh mục
    public List<DanhMuc> getAllDanhMuc() {
        try {
            return danhMucDAO.getAll();
        } catch (Exception e) {
            showError("Lỗi lấy danh sách danh mục: " + e.getMessage());
            return null;
        }
    }
    
    // Lấy danh mục theo ID
    public DanhMuc getDanhMucById(int id) {
        try {
            return danhMucDAO.getById(id);
        } catch (Exception e) {
            showError("Lỗi lấy thông tin danh mục: " + e.getMessage());
            return null;
        }
    }
    
    // Thêm danh mục
    public boolean addDanhMuc(DanhMuc danhMuc) {
        try {
            if (!validateDanhMuc(danhMuc)) {
                return false;
            }
            
            if (danhMucDAO.getByCode(danhMuc.getMaDM_Code()) != null) {
                showError("Mã danh mục đã tồn tại!");
                return false;
            }
            
            return danhMucDAO.insert(danhMuc);
        } catch (Exception e) {
            showError("Lỗi thêm danh mục: " + e.getMessage());
            return false;
        }
    }
    
    // Cập nhật danh mục
    public boolean updateDanhMuc(DanhMuc danhMuc) {
        try {
            if (!validateDanhMuc(danhMuc)) {
                return false;
            }
            return danhMucDAO.update(danhMuc);
        } catch (Exception e) {
            showError("Lỗi cập nhật danh mục: " + e.getMessage());
            return false;
        }
    }
    
    // Xóa danh mục
    public boolean deleteDanhMuc(int id) {
        try {
            if (!showConfirm("Bạn có chắc muốn xóa danh mục này?")) {
                return false;
            }
            return danhMucDAO.delete(id);
        } catch (Exception e) {
            showError("Lỗi xóa danh mục: " + e.getMessage());
            return false;
        }
    }
    
    // Tìm kiếm danh mục
    public List<DanhMuc> searchDanhMuc(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return danhMucDAO.getAll();
            }
            return danhMucDAO.search(keyword);
        } catch (Exception e) {
            showError("Lỗi tìm kiếm danh mục: " + e.getMessage());
            return null;
        }
    }
    
    // Validate danh mục
    private boolean validateDanhMuc(DanhMuc danhMuc) {
        if (danhMuc.getTenDanhMuc() == null || danhMuc.getTenDanhMuc().trim().isEmpty()) {
            showError("Tên danh mục không được để trống!");
            return false;
        }
        
        if (danhMuc.getMaDM_Code() == null || danhMuc.getMaDM_Code().trim().isEmpty()) {
            showError("Mã danh mục không được để trống!");
            return false;
        }
        
        return true;
    }
}