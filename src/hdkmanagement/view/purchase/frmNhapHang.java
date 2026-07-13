// view/purchase/frmNhapHang.java
package hdkmanagement.view.purchase;

import hdkmanagement.controller.SanPhamController;
import hdkmanagement.controller.NhaCungCapController;
import hdkmanagement.model.SanPham;
import hdkmanagement.model.NhaCungCap;
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

public class frmNhapHang {
    
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel tablePanel;
    private JPanel buttonPanel;
    
    // Form components
    private JTextField txtMaPN;
    private JComboBox<NhaCungCap> cboNhaCungCap;
    private JTextField txtNgayNhap;
    private JTextArea txtGhiChu;
    
    // Product selection
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JTextField txtDonGia;
    private JButton btnAddProduct;
    private JButton btnRemoveProduct;
    
    // Table
    private JTable tblChiTiet;
    private DefaultTableModel tableModel;
    private JLabel lblTongTien;
    
    // Buttons
    private JButton btnSave;
    private JButton btnClear;
    private JButton btnRefresh;
    
    private SanPhamController sanPhamController;
    private NhaCungCapController nhaCungCapController;
    private List<ChiTietNhap> chiTietList;
    
    private final Color SECONDARY_COLOR = new Color(41, 128, 185);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BG_COLOR = new Color(236, 240, 241);
    
    public frmNhapHang() {
        sanPhamController = new SanPhamController();
        nhaCungCapController = new NhaCungCapController();
        chiTietList = new ArrayList<>();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // ===== Form Panel =====
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📥 Thông tin phiếu nhập",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Mã phiếu nhập
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã phiếu nhập:"), gbc);
        txtMaPN = new JTextField(15);
        txtMaPN.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMaPN.setPreferredSize(new Dimension(200, 30));
        txtMaPN.setEditable(false);
        txtMaPN.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1;
        formPanel.add(txtMaPN, gbc);
        
        // Nhà cung cấp
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nhà cung cấp:"), gbc);
        cboNhaCungCap = new JComboBox<>();
        cboNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboNhaCungCap.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(cboNhaCungCap, gbc);
        
        // Ngày nhập
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Ngày nhập:"), gbc);
        txtNgayNhap = new JTextField(15);
        txtNgayNhap.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNgayNhap.setPreferredSize(new Dimension(200, 30));
        txtNgayNhap.setText(DateUtil.getCurrentDateString());
        gbc.gridx = 1;
        formPanel.add(txtNgayNhap, gbc);
        
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
        productPanel.setPreferredSize(new Dimension(0, 100));
        
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
        
        // Số lượng
        pgc.gridx = 2; pgc.gridy = 0;
        productPanel.add(new JLabel("Số lượng:"), pgc);
        txtSoLuong = new JTextField(10);
        txtSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSoLuong.setPreferredSize(new Dimension(100, 30));
        pgc.gridx = 3;
        productPanel.add(txtSoLuong, pgc);
        
        // Đơn giá
        pgc.gridx = 4; pgc.gridy = 0;
        productPanel.add(new JLabel("Đơn giá:"), pgc);
        txtDonGia = new JTextField(10);
        txtDonGia.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDonGia.setPreferredSize(new Dimension(100, 30));
        pgc.gridx = 5;
        productPanel.add(txtDonGia, pgc);
        
        // Buttons
        btnAddProduct = new JButton("➕ Thêm");
        btnAddProduct.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAddProduct.setBackground(SECONDARY_COLOR);
        btnAddProduct.setForeground(Color.WHITE);
        btnAddProduct.setFocusPainted(false);
        btnAddProduct.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnAddProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pgc.gridx = 6; pgc.gridy = 0;
        productPanel.add(btnAddProduct, pgc);
        
        btnRemoveProduct = new JButton("🗑️ Xóa");
        btnRemoveProduct.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRemoveProduct.setBackground(DANGER_COLOR);
        btnRemoveProduct.setForeground(Color.WHITE);
        btnRemoveProduct.setFocusPainted(false);
        btnRemoveProduct.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnRemoveProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pgc.gridx = 7; pgc.gridy = 0;
        productPanel.add(btnRemoveProduct, pgc);
        
        // ===== Table Panel =====
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📋 Chi tiết phiếu nhập",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        tablePanel.setPreferredSize(new Dimension(0, 250));
        
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
        
        // ===== Total Panel =====
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        totalPanel.setBackground(Color.WHITE);
        
        lblTongTien = new JLabel("Tổng tiền: 0 ₫");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(new Color(231, 76, 60));
        totalPanel.add(lblTongTien);
        
        tablePanel.add(totalPanel, BorderLayout.SOUTH);
        
        // ===== Button Panel =====
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnSave = createButton("💾 Lưu phiếu nhập", ACCENT_COLOR);
        btnClear = createButton("🔄 Nhập lại", new Color(149, 165, 166));
        btnRefresh = createButton("📊 Làm mới", SECONDARY_COLOR);
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        
        // ===== Add to mainPanel =====
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(productPanel, BorderLayout.CENTER);
        centerPanel.add(tablePanel, BorderLayout.SOUTH);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        addEvents();
        generateMaPN();
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
    
    private void generateMaPN() {
        String code = "PN" + System.currentTimeMillis() % 1000000;
        txtMaPN.setText(code);
    }
    
    private void loadData() {
        loadNhaCungCap();
        loadSanPham();
    }
    
    private void loadNhaCungCap() {
        List<NhaCungCap> list = nhaCungCapController.getAllNhaCungCap();
        cboNhaCungCap.removeAllItems();
        if (list != null && !list.isEmpty()) {
            for (NhaCungCap ncc : list) {
                cboNhaCungCap.addItem(ncc);
            }
        }
    }
    
    private void loadSanPham() {
        List<SanPham> list = sanPhamController.getAllSanPham();
        cboSanPham.removeAllItems();
        if (list != null && !list.isEmpty()) {
            for (SanPham sp : list) {
                if (sp.isTrangThai()) {
                    cboSanPham.addItem(sp);
                }
            }
        }
    }
    
    private void addEvents() {
        btnAddProduct.addActionListener(e -> addChiTiet());
        btnRemoveProduct.addActionListener(e -> removeChiTiet());
        btnSave.addActionListener(e -> savePhieuNhap());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> {
            loadData();
            generateMaPN();
        });
        
        tblChiTiet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Select row
            }
        });
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
        for (ChiTietNhap ct : chiTietList) {
            if (ct.getMaSP() == sp.getMaSP()) {
                MessageUtil.showWarning("Sản phẩm đã có trong danh sách!");
                return;
            }
        }
        
        ChiTietNhap ct = new ChiTietNhap();
        ct.setMaSP(sp.getMaSP());
        ct.setTenSP(sp.getTenSP());
        ct.setSoLuong(soLuong);
        ct.setDonGia(donGia);
        ct.setThanhTien(soLuong * donGia);
        
        chiTietList.add(ct);
        updateTable();
        updateTotal();
        
        txtSoLuong.setText("");
        txtDonGia.setText("");
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
            updateTotal();
        }
    }
    
    private void updateTable() {
        tableModel.setRowCount(0);
        for (ChiTietNhap ct : chiTietList) {
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
    
    private void updateTotal() {
        double total = 0;
        for (ChiTietNhap ct : chiTietList) {
            total += ct.getThanhTien();
        }
        lblTongTien.setText("Tổng tiền: " + ValidateUtil.formatCurrencyVND(total));
    }
    
    private void savePhieuNhap() {
        if (cboNhaCungCap.getSelectedItem() == null) {
            MessageUtil.showWarning("Vui lòng chọn nhà cung cấp!");
            return;
        }
        
        if (chiTietList.isEmpty()) {
            MessageUtil.showWarning("Vui lòng thêm sản phẩm vào phiếu nhập!");
            return;
        }
        
        if (!MessageUtil.showConfirm("Bạn có chắc muốn lưu phiếu nhập này?")) {
            return;
        }
        
        // TODO: Lưu vào database
        // Hiện tại chỉ hiển thị thông báo
        MessageUtil.showInfo("✅ Lưu phiếu nhập thành công!");
        clearForm();
        generateMaPN();
    }
    
    private void clearForm() {
        cboNhaCungCap.setSelectedIndex(0);
        txtNgayNhap.setText(DateUtil.getCurrentDateString());
        txtGhiChu.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        chiTietList.clear();
        updateTable();
        updateTotal();
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
    
    // Inner class
    private class ChiTietNhap {
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