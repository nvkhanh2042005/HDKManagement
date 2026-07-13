// dao/SanPhamDAO.java
package hdkmanagement.dao;

import hdkmanagement.model.SanPham;
import hdkmanagement.model.DanhMuc;
import hdkmanagement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO implements IDAO<SanPham> {
    
    private DatabaseConnection db;
    private DanhMucDAO danhMucDAO;
    
    public SanPhamDAO() {
        db = DatabaseConnection.getInstance();
        danhMucDAO = new DanhMucDAO();
    }
    
    @Override
    public boolean insert(SanPham entity) {
        String sql = "INSERT INTO SanPham (MaSP_Code, TenSP, MaDM, DonViTinh, GiaNhap, GiaBan, " +
                     "TonKho, TonToiThieu, HinhAnh, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getMaSP_Code());
            ps.setString(2, entity.getTenSP());
            ps.setInt(3, entity.getMaDM());
            ps.setString(4, entity.getDonViTinh());
            ps.setDouble(5, entity.getGiaNhap());
            ps.setDouble(6, entity.getGiaBan());
            ps.setInt(7, entity.getTonKho());
            ps.setInt(8, entity.getTonToiThieu());
            ps.setString(9, entity.getHinhAnh());
            ps.setString(10, entity.getMoTa());
            
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    entity.setMaSP(rs.getInt(1));
                }
                // Cập nhật bảng Kho
                updateKho(entity.getMaSP(), entity.getTonKho());
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean update(SanPham entity) {
        String sql = "UPDATE SanPham SET TenSP = ?, MaDM = ?, DonViTinh = ?, GiaNhap = ?, " +
                     "GiaBan = ?, TonKho = ?, TonToiThieu = ?, HinhAnh = ?, MoTa = ?, TrangThai = ? " +
                     "WHERE MaSP = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getTenSP());
            ps.setInt(2, entity.getMaDM());
            ps.setString(3, entity.getDonViTinh());
            ps.setDouble(4, entity.getGiaNhap());
            ps.setDouble(5, entity.getGiaBan());
            ps.setInt(6, entity.getTonKho());
            ps.setInt(7, entity.getTonToiThieu());
            ps.setString(8, entity.getHinhAnh());
            ps.setString(9, entity.getMoTa());
            ps.setBoolean(10, entity.isTrangThai());
            ps.setInt(11, entity.getMaSP());
            
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                updateKho(entity.getMaSP(), entity.getTonKho());
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "UPDATE SanPham SET TrangThai = 0 WHERE MaSP = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public SanPham getById(int id) {
        String sql = "SELECT * FROM SanPham WHERE MaSP = ? AND TrangThai = 1";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SanPham sp = mapResultSetToSanPham(rs);
                sp.setDanhMuc(danhMucDAO.getById(sp.getMaDM()));
                return sp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.*, dm.TenDanhMuc FROM SanPham sp " +
                     "LEFT JOIN DanhMuc dm ON sp.MaDM = dm.MaDM " +
                     "WHERE sp.TrangThai = 1 ORDER BY sp.MaSP";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SanPham sp = mapResultSetToSanPham(rs);
                // Lấy thông tin danh mục
                DanhMuc dm = new DanhMuc();
                dm.setMaDM(rs.getInt("MaDM"));
                dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sp.setDanhMuc(dm);
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<SanPham> search(String keyword) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.*, dm.TenDanhMuc FROM SanPham sp " +
                     "LEFT JOIN DanhMuc dm ON sp.MaDM = dm.MaDM " +
                     "WHERE sp.TrangThai = 1 AND (sp.MaSP_Code LIKE ? OR sp.TenSP LIKE ?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = mapResultSetToSanPham(rs);
                DanhMuc dm = new DanhMuc();
                dm.setMaDM(rs.getInt("MaDM"));
                dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
                sp.setDanhMuc(dm);
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    private SanPham mapResultSetToSanPham(ResultSet rs) throws SQLException {
        SanPham sp = new SanPham();
        sp.setMaSP(rs.getInt("MaSP"));
        sp.setMaSP_Code(rs.getString("MaSP_Code"));
        sp.setTenSP(rs.getString("TenSP"));
        sp.setMaDM(rs.getInt("MaDM"));
        sp.setDonViTinh(rs.getString("DonViTinh"));
        sp.setGiaNhap(rs.getDouble("GiaNhap"));
        sp.setGiaBan(rs.getDouble("GiaBan"));
        sp.setTonKho(rs.getInt("TonKho"));
        sp.setTonToiThieu(rs.getInt("TonToiThieu"));
        sp.setHinhAnh(rs.getString("HinhAnh"));
        sp.setMoTa(rs.getString("MoTa"));
        sp.setTrangThai(rs.getBoolean("TrangThai"));
        sp.setNgayTao(rs.getTimestamp("NgayTao"));
        return sp;
    }
    
    // Cập nhật bảng kho
    private void updateKho(int maSP, int soLuong) {
        String sql = "INSERT INTO Kho (MaSP, SoLuong) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE SoLuong = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, maSP);
            ps.setInt(2, soLuong);
            ps.setInt(3, soLuong);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Cập nhật tồn kho
    public boolean updateTonKho(int maSP, int soLuong) {
        String sql = "UPDATE SanPham SET TonKho = ? WHERE MaSP = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maSP);
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                updateKho(maSP, soLuong);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Lấy sản phẩm sắp hết
    public List<SanPham> getProductsNearExpiry() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE TrangThai = 1 AND TonKho <= TonToiThieu ORDER BY TonKho ASC";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToSanPham(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy sản phẩm bán chạy
    public List<SanPham> getBestSellers() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.*, SUM(cthd.SoLuong) as TotalSold " +
                     "FROM SanPham sp " +
                     "JOIN ChiTietHoaDon cthd ON sp.MaSP = cthd.MaSP " +
                     "JOIN HoaDon hd ON cthd.MaHD = hd.MaHD " +
                     "WHERE hd.TrangThai = 1 AND hd.NgayBan >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                     "GROUP BY sp.MaSP ORDER BY TotalSold DESC LIMIT 10";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToSanPham(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public SanPham getByCode(String code) {
        String sql = "SELECT * FROM SanPham WHERE MaSP_Code = ? AND TrangThai = 1";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToSanPham(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}