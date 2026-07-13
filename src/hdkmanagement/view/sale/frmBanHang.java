// view/sale/frmBanHang.java
package hdkmanagement.view.sale;

import hdkmanagement.controller.SanPhamController;
import hdkmanagement.controller.KhachHangController;
import hdkmanagement.model.SanPham;
import hdkmanagement.model.KhachHang;
import hdkmanagement.util.MessageUtil;
import hdkmanagement.util.DateUtil;
import hdkmanagement.util.SessionManager;
import hdkmanagement.util.ValidateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class frmBanHang {
    
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel tablePanel;
    private JPanel buttonPanel;
    private JPanel searchPanel;
    
    // Form components
    private JTextField txtMaHD;
    private JComboBox<KhachHang> cboKhachHang;
    private JTextField txtNgayBan;
    private JTextField txtTimKiemSP;
    private JButton btnTimKiemSP;
    private JTextArea txtGhiChu;
    
    // Product selection
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JTextField txtDonGia;
    private JButton btnAddProduct;
    private JButton btnRemoveProduct;
    private JLabel lblTonKho;
    
    // Table
    private JTable tblChiTiet;
    private DefaultTableModel tableModel;
    private JLabel lblTongTien;
    private JTextField txtChietKhau;
    private JTextField txtThanhToan;
    private JLabel lblConNo;
    
    // Buttons
    private JButton btnSave;
    private JButton btnClear;
    private JButton btnInHoaDon;
    
    private SanPhamController sanPhamController;
    private KhachHangController khachHangController;
    private List<ChiTietBan> chiTietList;
    private SanPham selectedSanPham = null;
    
    private final Color SECONDARY_COLOR = new Color(41, 128, 185);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BG_COLOR = new Color(236, 240, 241);
    
    public frmBanHang() {
        sanPhamController = new SanPhamController();
        khachHangController = new KhachHangController();
        chiTietList = new ArrayList<>();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // ===== Search Panel =====
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "🔍 Tìm kiếm sản phẩm",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        
        txtTimKiemSP = new JTextField(25);
        txtTimKiemSP.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiemSP.setPreferredSize(new Dimension(250, 35));
        
        btnTimKiemSP = new JButton("Tìm kiếm");
        btnTimKiemSP.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnTimKiemSP.setBackground(SECONDARY_COLOR);
        btnTimKiemSP.setForeground(Color.WHITE);
        btnTimKiemSP.setFocusPainted(false);
        btnTimKiemSP.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        searchPanel.add(new JLabel("Tên/Mã SP:"));
        searchPanel.add(txtTimKiemSP);
        searchPanel.add(btnTimKiemSP);
        
        // ===== Form Panel =====
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📝 Thông tin hóa đơn",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Mã hóa đơn
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã hóa đơn:"), gbc);
        txtMaHD = new JTextField(15);
        txtMaHD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMaHD.setPreferredSize(new Dimension(200, 30));
        txtMaHD.setEditable(false);
        txtMaHD.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1;
        formPanel.add(txtMaHD, gbc);
        
        // Khách hàng
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Khách hàng:"), gbc);
        cboKhachHang = new JComboBox<>();
        cboKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboKhachHang.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(cboKhachHang, gbc);
        
        // Ngày bán
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Ngày bán:"), gbc);
        txtNgayBan = new JTextField(15);
        txtNgayBan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNgayBan.setPreferredSize(new Dimension(200, 30));
        txtNgayBan.setText(DateUtil.getCurrentDateString());
        gbc.gridx = 1;
        formPanel.add(txtNgayBan, gbc);
        
        // Ghi chú
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Ghi chú:"), gbc);
        txtGhiChu = new JTextArea(2, 15);
        txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        JScrollPane ghiChuScroll = new JScrollPane(txtGhiChu);
        ghiChuScroll.setPreferredSize(new Dimension(200, 45));
        gbc.gridx = 1;
        formPanel.add(ghiChuScroll, gbc);
        
        // ===== Product Selection Panel =====
        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📦 Chọn sản phẩm",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        productPanel.setPreferredSize(new Dimension(0, 120));
        
        GridBagConstraints pgc = new GridBagConstraints();
        pgc.insets = new Insets(5, 10, 5, 10);
        pgc.fill = GridBagConstraints.HORIZONTAL;
        
        // Sản phẩm
        pgc.gridx = 0; pgc.gridy = 0;
        productPanel.add(new JLabel("Sản phẩm:"), pgc);
        cboSanPham = new JComboBox<>();
        cboSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboSanPham.setPreferredSize(new Dimension(200, 30));
        pgc.gridx = 1;
        productPanel.add(cboSanPham, pgc);
        
        // Tồn kho
        pgc.gridx = 2; pgc.gridy = 0;
        productPanel.add(new JLabel("Tồn kho:"), pgc);
        lblTonKho = new JLabel("0");
        lblTonKho.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTonKho.setForeground(SECONDARY_COLOR);
        pgc.gridx = 3;
        productPanel.add(lblTonKho, pgc);
        
        // Số lượng
        pgc.gridx = 0; pgc.gridy = 1;
        productPanel.add(new JLabel("Số lượng:"), pgc);
        txtSoLuong = new JTextField(10);
        txtSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSoLuong.setPreferredSize(new Dimension(100, 30));
        pgc.gridx = 1;
        productPanel.add(txtSoLuong, pgc);
        
        // Đơn giá
        pgc.gridx = 2; pgc.gridy = 1;
        productPanel.add(new JLabel("Đơn giá:"), pgc);
        txtDonGia = new JTextField(10);
        txtDonGia.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDonGia.setPreferredSize(new Dimension(100, 30));
        pgc.gridx = 3;
        productPanel.add(txtDonGia, pgc);
        
        // Buttons
        btnAddProduct = new JButton("➕ Thêm");
        btnAddProduct.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAddProduct.setBackground(SECONDARY_COLOR);
        btnAddProduct.setForeground(Color.WHITE);
        btnAddProduct.setFocusPainted(false);
        btnAddProduct.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnAddProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pgc.gridx = 4; pgc.gridy = 1;
        productPanel.add(btnAddProduct, pgc);
        
        btnRemoveProduct = new JButton("🗑️ Xóa");
        btnRemoveProduct.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRemoveProduct.setBackground(DANGER_COLOR);
        btnRemoveProduct.setForeground(Color.WHITE);
        btnRemoveProduct.setFocusPainted(false);
        btnRemoveProduct.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnRemoveProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pgc.gridx = 5; pgc.gridy = 1;
        productPanel.add(btnRemoveProduct, pgc);
        
        // ===== Table Panel =====
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📋 Chi tiết hóa đơn",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        tablePanel.setPreferredSize(new Dimension(0, 200));
        
        String[] columns = {"Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblChiTiet = new JTable(tableModel);
        tblChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblChiTiet.setRowHeight(30);
        tblChiTiet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblChiTiet.getTableHeader().setBackground(SECONDARY_COLOR);
        tblChiTiet.getTableHeader().setForeground(Color.WHITE);
        tblChiTiet.setSelectionBackground(new Color(41, 128, 185, 50));
        tblChiTiet.setShowGrid(true);
        tblChiTiet.setGridColor(new Color(230, 230, 230));
        
        tblChiTiet.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblChiTiet.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblChiTiet.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblChiTiet.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblChiTiet.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);
        scrollPane.setBorder(null);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // ===== Payment Panel =====
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBackground(Color.WHITE);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints pgc2 = new GridBagConstraints();
        pgc2.insets = new Insets(5, 15, 5, 15);
        pgc2.fill = GridBagConstraints.HORIZONTAL;
        
        pgc2.gridx = 0; pgc2.gridy = 0;
        paymentPanel.add(new JLabel("Tổng tiền:"), pgc2);
        lblTongTien = new JLabel("0 ₫");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTongTien.setForeground(new Color(231, 76, 60));
        pgc2.gridx = 1;
        paymentPanel.add(lblTongTien, pgc2);
        
        pgc2.gridx = 2; pgc2.gridy = 0;
        paymentPanel.add(new JLabel("Chiết khấu (%):"), pgc2);
        txtChietKhau = new JTextField(8);
        txtChietKhau.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtChietKhau.setPreferredSize(new Dimension(80, 30));
        txtChietKhau.setText("0");
        pgc2.gridx = 3;
        paymentPanel.add(txtChietKhau, pgc2);
        
        pgc2.gridx = 4; pgc2.gridy = 0;
        paymentPanel.add(new JLabel("Thanh toán:"), pgc2);
        txtThanhToan = new JTextField(10);
        txtThanhToan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtThanhToan.setPreferredSize(new Dimension(120, 30));
        pgc2.gridx = 5;
        paymentPanel.add(txtThanhToan, pgc2);
        
        pgc2.gridx = 6; pgc2.gridy = 0;
        paymentPanel.add(new JLabel("Công nợ:"), pgc2);
        lblConNo = new JLabel("0 ₫");
        lblConNo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblConNo.setForeground(DANGER_COLOR);
        pgc2.gridx = 7;
        paymentPanel.add(lblConNo, pgc2);
        
        tablePanel.add(paymentPanel, BorderLayout.SOUTH);
        
        // ===== Button Panel =====
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnSave = createButton("💾 Lưu hóa đơn", ACCENT_COLOR);
        btnClear = createButton("🔄 Nhập lại", new Color(149, 165, 166));
        btnInHoaDon = createButton("🖨️ In hóa đơn", SECONDARY_COLOR);
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnInHoaDon);
        
        // ===== Add to mainPanel =====
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_COLOR);
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(BG_COLOR);
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(productPanel, BorderLayout.CENTER);
        centerPanel.add(tablePanel, BorderLayout.SOUTH);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        addEvents();
        generateMaHD();
        loadSanPham();
    }
    
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }
    
    private void generateMaHD() {
        String code = "HD" + System.currentTimeMillis() % 1000000;
        txtMaHD.setText(code);
    }
    
    private void loadData() {
        loadKhachHang();
        loadSanPham();
    }
    
    private void loadKhachHang() {
        List<KhachHang> list = khachHangController.getAllKhachHang();
        cboKhachHang.removeAllItems();
        if (list != null && !list.isEmpty()) {
            cboKhachHang.addItem(null); // Khách lẻ
            for (KhachHang kh : list) {
                cboKhachHang.addItem(kh);
            }
        }
    }
    
    private void loadSanPham() {
        List<SanPham> list = sanPhamController.getAllSanPham();
        cboSanPham.removeAllItems();
        if (list != null && !list.isEmpty()) {
            for (SanPham sp : list) {
                if (sp.isTrangThai() && sp.getTonKho() > 0) {
                    cboSanPham.addItem(sp);
                }
            }
        }
        
        // Cập nhật tồn kho khi chọn sản phẩm
        cboSanPham.addActionListener(e -> {
            SanPham sp = (SanPham) cboSanPham.getSelectedItem();
            if (sp != null) {
                lblTonKho.setText(String.valueOf(sp.getTonKho()));
                txtDonGia.setText(String.valueOf(sp.getGiaBan()));
                selectedSanPham = sp;
            }
        });
    }
    
    private void addEvents() {
        btnTimKiemSP.addActionListener(e -> searchProduct());
        btnAddProduct.addActionListener(e -> addChiTiet());
        btnRemoveProduct.addActionListener(e -> removeChiTiet());
        btnSave.addActionListener(e -> saveHoaDon());
        btnClear.addActionListener(e -> clearForm());
        btnInHoaDon.addActionListener(e -> printHoaDon());
        
        txtChietKhau.addActionListener(e -> updatePayment());
        txtThanhToan.addActionListener(e -> updatePayment());
        
        tblChiTiet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Select row
            }
        });
    }
    
    private void searchProduct() {
        String keyword = txtTimKiemSP.getText().trim();
        if (keyword.isEmpty()) {
            loadSanPham();
            return;
        }
        
        List<SanPham> list = sanPhamController.searchSanPham(keyword);
        cboSanPham.removeAllItems();
        if (list != null && !list.isEmpty()) {
            for (SanPham sp : list) {
                if (sp.isTrangThai() && sp.getTonKho() > 0) {
                    cboSanPham.addItem(sp);
                }
            }
        }
        if (cboSanPham.getItemCount() == 0) {
            MessageUtil.showInfo("Không tìm thấy sản phẩm nào!");
        }
    }
    
    private void addChiTiet() {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        if (sp == null) {
            MessageUtil.showWarning("Vui lòng chọn sản phẩm!");
            return;
        }
        
        int soLuong;
        double donGia;
        
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong <= 0) {
                MessageUtil.showWarning("Số lượng phải lớn hơn 0!");
                return;
            }
            if (soLuong > sp.getTonKho()) {
                MessageUtil.showWarning("Số lượng vượt quá tồn kho! (Tồn: " + sp.getTonKho() + ")");
                return;
            }
        } catch (NumberFormatException e) {
            MessageUtil.showWarning("Số lượng không hợp lệ!");
            return;
        }
        
        try {
            donGia = Double.parseDouble(txtDonGia.getText().trim());
            if (donGia <= 0) {
                MessageUtil.showWarning("Đơn giá phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            MessageUtil.showWarning("Đơn giá không hợp lệ!");
            return;
        }
        
        // Kiểm tra trùng sản phẩm
        for (ChiTietBan ct : chiTietList) {
            if (ct.getMaSP() == sp.getMaSP()) {
                MessageUtil.showWarning("Sản phẩm đã có trong danh sách!");
                return;
            }
        }
        
        ChiTietBan ct = new ChiTietBan();
        ct.setMaSP(sp.getMaSP());
        ct.setTenSP(sp.getTenSP());
        ct.setSoLuong(soLuong);
        ct.setDonGia(donGia);
        ct.setThanhTien(soLuong * donGia);
        
        chiTietList.add(ct);
        updateTable();
        updatePayment();
        
        txtSoLuong.setText("");
        cboSanPham.requestFocus();
    }
    
    private void removeChiTiet() {
        int row = tblChiTiet.getSelectedRow();
        if (row == -1) {
            MessageUtil.showWarning("Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        
        if (MessageUtil.showConfirm("Bạn có chắc muốn xóa sản phẩm này?")) {
            chiTietList.remove(row);
            updateTable();
            updatePayment();
        }
    }
    
    private void updateTable() {
        tableModel.setRowCount(0);
        for (ChiTietBan ct : chiTietList) {
            Object[] row = {
                ct.getMaSP(),
                ct.getTenSP(),
                ct.getSoLuong(),
                ValidateUtil.formatCurrencyVND(ct.getDonGia()),
                ValidateUtil.formatCurrencyVND(ct.getThanhTien())
            };
            tableModel.addRow(row);
        }
    }
    
    private void updatePayment() {
        double total = 0;
        for (ChiTietBan ct : chiTietList) {
            total += ct.getThanhTien();
        }
        
        double chietKhau = 0;
        try {
            chietKhau = Double.parseDouble(txtChietKhau.getText().trim());
            if (chietKhau > 100) chietKhau = 100;
            if (chietKhau < 0) chietKhau = 0;
        } catch (NumberFormatException e) {
            chietKhau = 0;
        }
        
        double tongSauCK = total * (1 - chietKhau / 100);
        lblTongTien.setText(ValidateUtil.formatCurrencyVND(tongSauCK));
        
        double thanhToan = 0;
        try {
            thanhToan = Double.parseDouble(txtThanhToan.getText().trim());
        } catch (NumberFormatException e) {
            thanhToan = 0;
        }
        
        double conNo = tongSauCK - thanhToan;
        if (conNo < 0) conNo = 0;
        lblConNo.setText(ValidateUtil.formatCurrencyVND(conNo));
    }
    
    private void saveHoaDon() {
        if (chiTietList.isEmpty()) {
            MessageUtil.showWarning("Vui lòng thêm sản phẩm vào hóa đơn!");
            return;
        }
        
        if (!MessageUtil.showConfirm("Bạn có chắc muốn lưu hóa đơn này?")) {
            return;
        }
        
        // TODO: Lưu vào database
        // Hiện tại chỉ hiển thị thông báo
        MessageUtil.showInfo("✅ Lưu hóa đơn thành công!");
        clearForm();
        generateMaHD();
    }
    
    private void clearForm() {
        cboKhachHang.setSelectedIndex(0);
        txtNgayBan.setText(DateUtil.getCurrentDateString());
        txtGhiChu.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        txtChietKhau.setText("0");
        txtThanhToan.setText("");
        chiTietList.clear();
        updateTable();
        updatePayment();
        loadSanPham();
    }
    
    private void printHoaDon() {
        if (chiTietList.isEmpty()) {
            MessageUtil.showWarning("Không có dữ liệu để in!");
            return;
        }
        MessageUtil.showInfo("🖨️ Đang in hóa đơn...");
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
    
    // Inner class
    private class ChiTietBan {
        private int maSP;
        private String tenSP;
        private int soLuong;
        private double donGia;
        private double thanhTien;
        
        public int getMaSP() { return maSP; }
        public void setMaSP(int maSP) { this.maSP = maSP; }
        public String getTenSP() { return tenSP; }
        public void setTenSP(String tenSP) { this.tenSP = tenSP; }
        public int getSoLuong() { return soLuong; }
        public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
        public double getDonGia() { return donGia; }
        public void setDonGia(double donGia) { this.donGia = donGia; }
        public double getThanhTien() { return thanhTien; }
        public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
    }
}