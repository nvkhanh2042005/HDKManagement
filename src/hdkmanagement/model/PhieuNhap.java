// model/PhieuNhap.java
package hdkmanagement.model;

import java.sql.Date;
import java.sql.Timestamp;

public class PhieuNhap {
    private int maPN;
    private String maPN_Code;
    private int maNCC;
    private Date ngayNhap;
    private double tongTien;
    private double daThanhToan;
    private double conNo;
    private String ghiChu;
    private boolean trangThai;
    private int nguoiTao;
    private Timestamp ngayTao;
    
    // Constructor
    public PhieuNhap() {
        this.trangThai = true;
    }
    
    // Getters and Setters
    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }
    public String getMaPN_Code() { return maPN_Code; }
    public void setMaPN_Code(String maPN_Code) { this.maPN_Code = maPN_Code; }
    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }
    public Date getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Date ngayNhap) { this.ngayNhap = ngayNhap; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public double getDaThanhToan() { return daThanhToan; }
    public void setDaThanhToan(double daThanhToan) { this.daThanhToan = daThanhToan; }
    public double getConNo() { return conNo; }
    public void setConNo(double conNo) { this.conNo = conNo; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
    public int getNguoiTao() { return nguoiTao; }
    public void setNguoiTao(int nguoiTao) { this.nguoiTao = nguoiTao; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
}