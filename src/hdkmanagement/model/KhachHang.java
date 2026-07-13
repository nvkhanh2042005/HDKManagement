// model/KhachHang.java
package hdkmanagement.model;

import java.sql.Timestamp;

public class KhachHang {
    private int maKH;
    private String maKH_Code;
    private String hoTen;
    private String sdt;
    private String email;
    private String diaChi;
    private double congNo;
    private String ghiChu;
    private boolean trangThai;
    private Timestamp ngayTao;

    public KhachHang() {
        this.trangThai = true;
        this.congNo = 0;
    }

    // Getters và Setters
    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }
    public String getMaKH_Code() { return maKH_Code; }
    public void setMaKH_Code(String maKH_Code) { this.maKH_Code = maKH_Code; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
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
        return hoTen + " - " + sdt;
    }
}