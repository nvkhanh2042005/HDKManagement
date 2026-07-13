// model/DanhMuc.java
package hdkmanagement.model;

import java.sql.Timestamp;

public class DanhMuc {
    private int maDM;
    private String maDM_Code;
    private String tenDanhMuc;
    private String moTa;
    private boolean trangThai;
    private Timestamp ngayTao;

    public DanhMuc() {
        this.trangThai = true;
    }

    public DanhMuc(int maDM, String maDM_Code, String tenDanhMuc, String moTa) {
        this.maDM = maDM;
        this.maDM_Code = maDM_Code;
        this.tenDanhMuc = tenDanhMuc;
        this.moTa = moTa;
        this.trangThai = true;
    }

    // Getters và Setters
    public int getMaDM() { return maDM; }
    public void setMaDM(int maDM) { this.maDM = maDM; }
    public String getMaDM_Code() { return maDM_Code; }
    public void setMaDM_Code(String maDM_Code) { this.maDM_Code = maDM_Code; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }

    @Override
    public String toString() {
        return tenDanhMuc;
    }
}