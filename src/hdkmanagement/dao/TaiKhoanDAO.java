// dao/TaiKhoanDAO.java
package hdkmanagement.dao;

import hdkmanagement.model.TaiKhoan;
import hdkmanagement.model.NhanVien;
import hdkmanagement.model.Quyen;
import hdkmanagement.util.DatabaseConnection;
import hdkmanagement.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO implements IDAO<TaiKhoan> {
    
    private DatabaseConnection db;
    private NhanVienDAO nhanVienDAO;
    private QuyenDAO quyenDAO;
    
    public TaiKhoanDAO() {
        db = DatabaseConnection.getInstance();
        nhanVienDAO = new NhanVienDAO();
        quyenDAO = new QuyenDAO();
    }
    
    @Override
    public boolean insert(TaiKhoan entity) {
        String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, MaNV, MaQuyen) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getTenDangNhap());
            ps.setString(2, PasswordUtil.md5(entity.getMatKhau())); // Mã hóa mật khẩu
            ps.setInt(3, entity.getMaNV());
            ps.setInt(4, entity.getMaQuyen());
            
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setMaTK(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(TaiKhoan entity) {
        String sql = "UPDATE TaiKhoan SET TenDangNhap = ?, MaQuyen = ?, TrangThai = ? WHERE MaTK = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getTenDangNhap());
            ps.setInt(2, entity.getMaQuyen());
            ps.setBoolean(3, entity.isTrangThai());
            ps.setInt(4, entity.getMaTK());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Đổi mật khẩu
    public boolean changePassword(int maTK, String oldPassword, String newPassword) {
        // Kiểm tra mật khẩu cũ
        TaiKhoan tk = getById(maTK);
        if (tk == null || !PasswordUtil.md5(oldPassword).equals(tk.getMatKhau())) {
            return false;
        }
        
        String sql = "UPDATE TaiKhoan SET MatKhau = ? WHERE MaTK = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, PasswordUtil.md5(newPassword));
            ps.setInt(2, maTK);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "UPDATE TaiKhoan SET TrangThai = 0 WHERE MaTK = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public TaiKhoan getById(int id) {
        String sql = "SELECT * FROM TaiKhoan WHERE MaTK = ? AND TrangThai = 1";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToTaiKhoan(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<TaiKhoan> getAll() {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan WHERE TrangThai = 1 ORDER BY MaTK";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToTaiKhoan(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<TaiKhoan> search(String keyword) {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan WHERE TrangThai = 1 AND TenDangNhap LIKE ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToTaiKhoan(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Đăng nhập
    public TaiKhoan login(String username, String password) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1";
        String hashedPassword = PasswordUtil.md5(password);
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TaiKhoan taiKhoan = mapResultSetToTaiKhoan(rs);
                // Lấy thông tin nhân viên và quyền
                taiKhoan.setNhanVien(nhanVienDAO.getById(taiKhoan.getMaNV()));
                taiKhoan.setQuyen(quyenDAO.getById(taiKhoan.getMaQuyen()));
                return taiKhoan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Kiểm tra tên đăng nhập đã tồn tại
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE TenDangNhap = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private TaiKhoan mapResultSetToTaiKhoan(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTK(rs.getInt("MaTK"));
        tk.setTenDangNhap(rs.getString("TenDangNhap"));
        tk.setMatKhau(rs.getString("MatKhau"));
        tk.setMaNV(rs.getInt("MaNV"));
        tk.setMaQuyen(rs.getInt("MaQuyen"));
        tk.setTrangThai(rs.getBoolean("TrangThai"));
        tk.setNgayTao(rs.getTimestamp("NgayTao"));
        return tk;
    }
}