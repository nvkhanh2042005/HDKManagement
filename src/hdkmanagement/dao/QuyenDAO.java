// dao/QuyenDAO.java
package hdkmanagement.dao;

import hdkmanagement.model.Quyen;
import hdkmanagement.util.DatabaseConnection;
import hdkmanagement.util.SQLQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuyenDAO implements IDAO<Quyen> {
    
    private DatabaseConnection db;
    
    public QuyenDAO() {
        db = DatabaseConnection.getInstance();
    }
    
    @Override
    public boolean insert(Quyen entity) {
        String sql = "INSERT INTO Quyen (TenQuyen, MoTa) VALUES (?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getTenQuyen());
            ps.setString(2, entity.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(Quyen entity) {
        String sql = "UPDATE Quyen SET TenQuyen = ?, MoTa = ? WHERE MaQuyen = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getTenQuyen());
            ps.setString(2, entity.getMoTa());
            ps.setInt(3, entity.getMaQuyen());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "UPDATE Quyen SET TrangThai = 0 WHERE MaQuyen = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // dao/QuyenDAO.java - Sửa method getById
@Override
public Quyen getById(int id) {
    String sql = "SELECT * FROM Quyen WHERE MaQuyen = ?";  // Bỏ AND TrangThai = 1
    try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapResultSetToQuyen(rs);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

// Sửa method getAll
@Override
public List<Quyen> getAll() {
    List<Quyen> list = new ArrayList<>();
    String sql = "SELECT * FROM Quyen ORDER BY MaQuyen";  // Bỏ WHERE TrangThai = 1
    try (Statement stmt = db.getConnection().createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            list.add(mapResultSetToQuyen(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

// Sửa method search
@Override
public List<Quyen> search(String keyword) {
    List<Quyen> list = new ArrayList<>();
    String sql = "SELECT * FROM Quyen WHERE TenQuyen LIKE ? OR MoTa LIKE ?";  // Bỏ AND TrangThai = 1
    try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
        String searchPattern = "%" + keyword + "%";
        ps.setString(1, searchPattern);
        ps.setString(2, searchPattern);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(mapResultSetToQuyen(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
    
    // Phương thức chuyển đổi ResultSet thành Quyen
    private Quyen mapResultSetToQuyen(ResultSet rs) throws SQLException {
        Quyen quyen = new Quyen();
        quyen.setMaQuyen(rs.getInt("MaQuyen"));
        quyen.setTenQuyen(rs.getString("TenQuyen"));
        quyen.setMoTa(rs.getString("MoTa"));
        quyen.setNgayTao(rs.getTimestamp("NgayTao"));
        return quyen;
    }
    
    // Lấy quyền theo tên
    public Quyen getByTenQuyen(String tenQuyen) {
        String sql = "SELECT * FROM Quyen WHERE TenQuyen = ? AND TrangThai = 1";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, tenQuyen);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToQuyen(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}