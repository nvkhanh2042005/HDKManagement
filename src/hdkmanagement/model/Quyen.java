// model/Quyen.java
package hdkmanagement.model;

import java.sql.Timestamp;

public class Quyen {
    private int maQuyen;
    private String tenQuyen;
    private String moTa;
    private Timestamp ngayTao;

    public Quyen() {}

    public Quyen(int maQuyen, String tenQuyen, String moTa) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.moTa = moTa;
    }

    // Getters và Setters
    public int getMaQuyen() { return maQuyen; }
    public void setMaQuyen(int maQuyen) { this.maQuyen = maQuyen; }
    public String getTenQuyen() { return tenQuyen; }
    public void setTenQuyen(String tenQuyen) { this.tenQuyen = tenQuyen; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }

    @Override
    public String toString() {
        return tenQuyen;
    }
}