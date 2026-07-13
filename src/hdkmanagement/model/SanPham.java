// model/SanPham.java
package hdkmanagement.model;

import java.sql.Timestamp;

public class SanPham {
    private int maSP;
    private String maSP_Code;
    private String tenSP;
    private int maDM;
    private String donViTinh;
    private double giaNhap;
    private double giaBan;
    private int tonKho;
    private int tonToiThieu;
    private String hinhAnh;
    private String moTa;
    private boolean trangThai;
    private Timestamp ngayTao;
    
    // Đối tượng liên kết
    private DanhMuc danhMuc;

    public SanPham() {
        this.trangThai = true;
        this.tonKho = 0;
        this.tonToiThieu = 5;
    }

    // Getters và Setters
    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }
    public String getMaSP_Code() { return maSP_Code; }
    public void setMaSP_Code(String maSP_Code) { this.maSP_Code = maSP_Code; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public int getMaDM() { return maDM; }
    public void setMaDM(int maDM) { this.maDM = maDM; }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }
    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public int getTonKho() { return tonKho; }
    public void setTonKho(int tonKho) { this.tonKho = tonKho; }
    public int getTonToiThieu() { return tonToiThieu; }
    public void setTonToiThieu(int tonToiThieu) { this.tonToiThieu = tonToiThieu; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }
    public DanhMuc getDanhMuc() { return danhMuc; }
    public void setDanhMuc(DanhMuc danhMuc) { this.danhMuc = danhMuc; }

    @Override
    public String toString() {
        return tenSP + " (" + maSP_Code + ")";
    }
}