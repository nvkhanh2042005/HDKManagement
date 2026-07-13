// model/TaiKhoan.java
package hdkmanagement.model;

import java.sql.Timestamp;

public class TaiKhoan {
    private int maTK;
    private String tenDangNhap;
    private String matKhau;
    private int maNV;
    private int maQuyen;
    private boolean trangThai;
    private Timestamp ngayTao;
    
    // Đối tượng liên kết
    private NhanVien nhanVien;
    private Quyen quyen;

    public TaiKhoan() {
        this.trangThai = true;
    }

    // Getters và Setters
    public int getMaTK() { return maTK; }
    public void setMaTK(int maTK) { this.maTK = maTK; }
    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public int getMaQuyen() { return maQuyen; }
    public void setMaQuyen(int maQuyen) { this.maQuyen = maQuyen; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }
    public Quyen getQuyen() { return quyen; }
    public void setQuyen(Quyen quyen) { this.quyen = quyen; }
}