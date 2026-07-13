// util/SQLQuery.java
package hdkmanagement.util;

public class SQLQuery {
    
    // ============ QUẢN LÝ TÀI KHOẢN ============
    public static final String TAI_KHOAN_LOGIN = 
        "SELECT tk.*, q.*, nv.* FROM TaiKhoan tk " +
        "LEFT JOIN Quyen q ON tk.MaQuyen = q.MaQuyen " +
        "LEFT JOIN NhanVien nv ON tk.MaNV = nv.MaNV " +
        "WHERE tk.TenDangNhap = ? AND tk.MatKhau = ? AND tk.TrangThai = 1";
    
    public static final String TAI_KHOAN_GET_ALL = 
        "SELECT tk.*, q.TenQuyen, nv.HoTen FROM TaiKhoan tk " +
        "LEFT JOIN Quyen q ON tk.MaQuyen = q.MaQuyen " +
        "LEFT JOIN NhanVien nv ON tk.MaNV = nv.MaNV " +
        "WHERE tk.TrangThai = 1";
    
    // ============ QUẢN LÝ SẢN PHẨM ============
    public static final String SAN_PHAM_GET_ALL = 
        "SELECT sp.*, dm.TenDanhMuc FROM SanPham sp " +
        "LEFT JOIN DanhMuc dm ON sp.MaDM = dm.MaDM " +
        "WHERE sp.TrangThai = 1";
    
    public static final String SAN_PHAM_SEARCH = 
        "SELECT sp.*, dm.TenDanhMuc FROM SanPham sp " +
        "LEFT JOIN DanhMuc dm ON sp.MaDM = dm.MaDM " +
        "WHERE sp.TrangThai = 1 AND (sp.MaSP_Code LIKE ? OR sp.TenSP LIKE ?)";
    
    // ============ QUẢN LÝ DANH MỤC ============
    public static final String DANH_MUC_GET_ALL = 
        "SELECT * FROM DanhMuc WHERE TrangThai = 1";
    
    // ============ QUẢN LÝ KHÁCH HÀNG ============
    public static final String KHACH_HANG_GET_ALL = 
        "SELECT * FROM KhachHang WHERE TrangThai = 1";
    
    public static final String KHACH_HANG_SEARCH = 
        "SELECT * FROM KhachHang WHERE TrangThai = 1 AND (HoTen LIKE ? OR SDT LIKE ?)";
    
    // ============ QUẢN LÝ NHÀ CUNG CẤP ============
    public static final String NHA_CUNG_CAP_GET_ALL = 
        "SELECT * FROM NhaCungCap WHERE TrangThai = 1";
    
    // ============ QUẢN LÝ NHÂN VIÊN ============
    public static final String NHAN_VIEN_GET_ALL = 
        "SELECT * FROM NhanVien WHERE TrangThai = 1";
    
    // ============ QUẢN LÝ NHẬP HÀNG ============
    public static final String PHIEU_NHAP_GET_ALL = 
        "SELECT pn.*, ncc.TenNCC, nv.HoTen FROM PhieuNhap pn " +
        "LEFT JOIN NhaCungCap ncc ON pn.MaNCC = ncc.MaNCC " +
        "LEFT JOIN NhanVien nv ON pn.NguoiTao = nv.MaNV " +
        "WHERE pn.TrangThai = 1 ORDER BY pn.NgayTao DESC";
    
    // ============ QUẢN LÝ BÁN HÀNG ============
    public static final String HOA_DON_GET_ALL = 
        "SELECT hd.*, kh.HoTen as TenKH, nv.HoTen as TenNV FROM HoaDon hd " +
        "LEFT JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
        "LEFT JOIN NhanVien nv ON hd.NhanVienBan = nv.MaNV " +
        "WHERE hd.TrangThai = 1 ORDER BY hd.NgayTao DESC";
    
    // ============ THỐNG KÊ ============
    public static final String THONG_KE_DOANH_THU = 
        "SELECT * FROM ThongKeDoanhThu";
    
    public static final String THONG_KE_TON_KHO = 
        "SELECT * FROM ThongKeTonKho";
}