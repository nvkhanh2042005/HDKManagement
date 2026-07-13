// model/NhaCungCap.java
package hdkmanagement.model;

import java.sql.Timestamp;

public class NhaCungCap {
    private int maNCC;
    private String maNCC_Code;
    private String tenNCC;
    private String nguoiDaiDien;
    private String diaChi;
    private String sdt;
    private String email;
    private double congNo;
    private String ghiChu;
    private boolean trangThai;
    private Timestamp ngayTao;

    public NhaCungCap() {
        this.trangThai = true;
        this.congNo = 0;
    }

    // Getters và Setters
    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }
    public String getMaNCC_Code() { return maNCC_Code; }
    public void setMaNCC_Code(String maNCC_Code) { this.maNCC_Code = maNCC_Code; }
    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }
    public String getNguoiDaiDien() { return nguoiDaiDien; }
    public void setNguoiDaiDien(String nguoiDaiDien) { this.nguoiDaiDien = nguoiDaiDien; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public double getCongNo() { return congNo; }
    public void setCongNo(double congNo) { this.congNo = congNo; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }

    @Override
    public String toString() {
        return tenNCC;
    }
}