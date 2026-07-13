// dao/DanhMucDAO.java
package hdkmanagement.dao;

import hdkmanagement.model.DanhMuc;
import hdkmanagement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO implements IDAO<DanhMuc> {
    
    private DatabaseConnection db;
    
    public DanhMucDAO() {
        db = DatabaseConnection.getInstance();
    }
    
    @Override
    public boolean insert(DanhMuc entity) {
        String sql = "INSERT INTO DanhMuc (MaDM_Code, TenDanhMuc, MoTa) VALUES (?, ?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getMaDM_Code());
            ps.setString(2, entity.getTenDanhMuc());
            ps.setString(3, entity.getMoTa());
            
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setMaDM(rs.getInt(1));
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
    public boolean update(DanhMuc entity) {
        String sql = "UPDATE DanhMuc SET TenDanhMuc = ?, MoTa = ?, TrangThai = ? WHERE MaDM = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getTenDanhMuc());
            ps.setString(2, entity.getMoTa());
            ps.setBoolean(3, entity.isTrangThai());
            ps.setInt(4, entity.getMaDM());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int id) {
        // Xóa mềm
        String sql = "UPDATE DanhMuc SET TrangThai = 0 WHERE MaDM = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa cứng (cẩn thận khi dùng)
    public boolean deletePermanent(int id) {
        String sql = "DELETE FROM DanhMuc WHERE MaDM = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public DanhMuc getById(int id) {
        String sql = "SELECT * FROM DanhMuc WHERE MaDM = ? AND TrangThai = 1";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToDanhMuc(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<DanhMuc> getAll() {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhMuc WHERE TrangThai = 1 ORDER BY MaDM";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToDanhMuc(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<DanhMuc> search(String keyword) {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhMuc WHERE TrangThai = 1 AND (TenDanhMuc LIKE ? OR MaDM_Code LIKE ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToDanhMuc(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private DanhMuc mapResultSetToDanhMuc(ResultSet rs) throws SQLException {
        DanhMuc dm = new DanhMuc();
        dm.setMaDM(rs.getInt("MaDM"));
        dm.setMaDM_Code(rs.getString("MaDM_Code"));
        dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
        dm.setMoTa(rs.getString("MoTa"));
        dm.setTrangThai(rs.getBoolean("TrangThai"));
        dm.setNgayTao(rs.getTimestamp("NgayTao"));
        return dm;
    }
    
    public DanhMuc getByCode(String code) {
        String sql = "SELECT * FROM DanhMuc WHERE MaDM_Code = ? AND TrangThai = 1";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToDanhMuc(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public DanhMuc getByTenDanhMuc(String tenDanhMuc) {
        String sql = "SELECT * FROM DanhMuc WHERE TenDanhMuc = ? AND TrangThai = 1";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, tenDanhMuc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToDanhMuc(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}