// view/supplier/frmNhaCungCap.java
package hdkmanagement.view.supplier;

import hdkmanagement.controller.NhaCungCapController;
import hdkmanagement.model.NhaCungCap;
import hdkmanagement.util.MessageUtil;
import hdkmanagement.util.ValidateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class frmNhaCungCap {
    
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JPanel tablePanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnRefresh;
    
    private JTable tblNhaCungCap;
    private DefaultTableModel tableModel;
    
    // Form components
    private JTextField txtMaNCC;
    private JTextField txtTenNCC;
    private JTextField txtNguoiDaiDien;
    private JTextArea txtDiaChi;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTextField txtCongNo;
    private JTextArea txtGhiChu;
    private JCheckBox chkTrangThai;
    
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    
    private NhaCungCapController nhaCungCapController;
    private int selectedId = -1;
    
    private final Color SECONDARY_COLOR = new Color(41, 128, 185);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color WARNING_COLOR = new Color(241, 196, 15);
    private final Color BG_COLOR = new Color(236, 240, 241);
    
    public frmNhaCungCap() {
        nhaCungCapController = new NhaCungCapController();
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
            "🔍 Tìm kiếm nhà cung cấp",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        
        txtSearch = new JTextField(30);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(250, 35));
        
        btnSearch = createButton("Tìm kiếm", SECONDARY_COLOR);
        btnRefresh = createButton("Làm mới", new Color(149, 165, 166));
        
        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);
        
        // ===== Table Panel =====
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📋 Danh sách nhà cung cấp",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        
        String[] columns = {"Mã NCC", "Tên NCC", "Người đại diện", "SĐT", "Email", "Công nợ", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblNhaCungCap = new JTable(tableModel);
        tblNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNhaCungCap.setRowHeight(30);
        tblNhaCungCap.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblNhaCungCap.getTableHeader().setBackground(SECONDARY_COLOR);
        tblNhaCungCap.getTableHeader().setForeground(Color.WHITE);
        tblNhaCungCap.setSelectionBackground(new Color(41, 128, 185, 50));
        tblNhaCungCap.setShowGrid(true);
        tblNhaCungCap.setGridColor(new Color(230, 230, 230));
        
        tblNhaCungCap.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblNhaCungCap.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblNhaCungCap.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblNhaCungCap.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblNhaCungCap.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblNhaCungCap.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblNhaCungCap.getColumnModel().getColumn(6).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(tblNhaCungCap);
        scrollPane.setBorder(null);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // ===== Form Panel =====
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📝 Thông tin nhà cung cấp",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        formPanel.setPreferredSize(new Dimension(380, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Mã NCC
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã NCC:"), gbc);
        txtMaNCC = new JTextField(15);
        txtMaNCC.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMaNCC.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(txtMaNCC, gbc);
        
        // Tên NCC
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tên NCC:"), gbc);
        txtTenNCC = new JTextField(15);
        txtTenNCC.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTenNCC.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(txtTenNCC, gbc);
        
        // Người đại diện
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Người đại diện:"), gbc);
        txtNguoiDaiDien = new JTextField(15);
        txtNguoiDaiDien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNguoiDaiDien.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(txtNguoiDaiDien, gbc);
        
        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        txtDiaChi = new JTextArea(2, 15);
        txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDiaChi.setLineWrap(true);
        txtDiaChi.setWrapStyleWord(true);
        JScrollPane diaChiScroll = new JScrollPane(txtDiaChi);
        diaChiScroll.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 1;
        formPanel.add(diaChiScroll, gbc);
        
        // SĐT
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);
        txtSDT = new JTextField(15);
        txtSDT.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSDT.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(txtSDT, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(15);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtEmail.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);
        
        // Công nợ
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Công nợ:"), gbc);
        txtCongNo = new JTextField(15);
        txtCongNo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCongNo.setPreferredSize(new Dimension(200, 30));
        txtCongNo.setEditable(false);
        txtCongNo.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1;
        formPanel.add(txtCongNo, gbc);
        
        // Ghi chú
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Ghi chú:"), gbc);
        txtGhiChu = new JTextArea(2, 15);
        txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        JScrollPane ghiChuScroll = new JScrollPane(txtGhiChu);
        ghiChuScroll.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 1;
        formPanel.add(ghiChuScroll, gbc);
        
        // Trạng thái
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(new JLabel("Trạng thái:"), gbc);
        chkTrangThai = new JCheckBox("Hoạt động");
        chkTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkTrangThai.setSelected(true);
        gbc.gridx = 1;
        formPanel.add(chkTrangThai, gbc);
        
        // Buttons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        btnAdd = createButton("Thêm", ACCENT_COLOR);
        btnUpdate = createButton("Sửa", WARNING_COLOR);
        btnDelete = createButton("Xóa", DANGER_COLOR);
        btnClear = createButton("Nhập lại", new Color(149, 165, 166));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        gbc.gridx = 0; gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        formPanel.add(buttonPanel, gbc);
        
        // Add to mainPanel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(formPanel, BorderLayout.EAST);
        
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        addEvents();
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
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
    
    private void addEvents() {
        btnSearch.addActionListener(e -> search());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadData();
        });
        txtSearch.addActionListener(e -> search());
        
        tblNhaCungCap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblNhaCungCap.getSelectedRow();
                if (row >= 0) {
                    selectRow(row);
                }
            }
        });
        
        btnAdd.addActionListener(e -> addNhaCungCap());
        btnUpdate.addActionListener(e -> updateNhaCungCap());
        btnDelete.addActionListener(e -> deleteNhaCungCap());
        btnClear.addActionListener(e -> clearForm());
    }
    
    public void loadData() {
        List<NhaCungCap> list = nhaCungCapController.getAllNhaCungCap();
        displayData(list);
    }
    
    private void search() {
        String keyword = txtSearch.getText().trim();
        List<NhaCungCap> list = nhaCungCapController.searchNhaCungCap(keyword);
        displayData(list);
    }
    
    private void displayData(List<NhaCungCap> list) {
        tableModel.setRowCount(0);
        if (list != null) {
            for (NhaCungCap ncc : list) {
                Object[] row = {
                    ncc.getMaNCC_Code(),
                    ncc.getTenNCC(),
                    ncc.getNguoiDaiDien(),
                    ncc.getSdt(),
                    ncc.getEmail(),
                    ValidateUtil.formatCurrencyVND(ncc.getCongNo()),
                    ncc.isTrangThai() ? "Hoạt động" : "Ngừng"
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void selectRow(int row) {
        String maNCC = tableModel.getValueAt(row, 0).toString();
        List<NhaCungCap> list = nhaCungCapController.getAllNhaCungCap();
        for (NhaCungCap ncc : list) {
            if (ncc.getMaNCC_Code().equals(maNCC)) {
                selectedId = ncc.getMaNCC();
                txtMaNCC.setText(ncc.getMaNCC_Code());
                txtTenNCC.setText(ncc.getTenNCC());
                txtNguoiDaiDien.setText(ncc.getNguoiDaiDien());
                txtDiaChi.setText(ncc.getDiaChi());
                txtSDT.setText(ncc.getSdt());
                txtEmail.setText(ncc.getEmail());
                txtCongNo.setText(String.valueOf(ncc.getCongNo()));
                txtGhiChu.setText(ncc.getGhiChu());
                chkTrangThai.setSelected(ncc.isTrangThai());
                
                btnAdd.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                break;
            }
        }
    }
    
    private void addNhaCungCap() {
        NhaCungCap ncc = getFormData();
        if (ncc == null) return;
        
        if (nhaCungCapController.addNhaCungCap(ncc)) {
            MessageUtil.showInfo("Thêm nhà cung cấp thành công!");
            clearForm();
            loadData();
        }
    }
    
    private void updateNhaCungCap() {
        if (selectedId == -1) {
            MessageUtil.showWarning("Vui lòng chọn nhà cung cấp cần sửa!");
            return;
        }
        
        NhaCungCap ncc = getFormData();
        if (ncc == null) return;
        ncc.setMaNCC(selectedId);
        
        if (nhaCungCapController.updateNhaCungCap(ncc)) {
            MessageUtil.showInfo("Cập nhật nhà cung cấp thành công!");
            clearForm();
            loadData();
        }
    }
    
    private void deleteNhaCungCap() {
        if (selectedId == -1) {
            MessageUtil.showWarning("Vui lòng chọn nhà cung cấp cần xóa!");
            return;
        }
        
        if (nhaCungCapController.deleteNhaCungCap(selectedId)) {
            MessageUtil.showInfo("Xóa nhà cung cấp thành công!");
            clearForm();
            loadData();
        }
    }
    
    private NhaCungCap getFormData() {
        String maNCC = txtMaNCC.getText().trim();
        String tenNCC = txtTenNCC.getText().trim();
        String nguoiDaiDien = txtNguoiDaiDien.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();
        boolean trangThai = chkTrangThai.isSelected();
        
        if (maNCC.isEmpty() || tenNCC.isEmpty()) {
            MessageUtil.showWarning("Vui lòng nhập mã và tên nhà cung cấp!");
            return null;
        }
        
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC_Code(maNCC);
        ncc.setTenNCC(tenNCC);
        ncc.setNguoiDaiDien(nguoiDaiDien);
        ncc.setDiaChi(diaChi);
        ncc.setSdt(sdt);
        ncc.setEmail(email);
        ncc.setCongNo(0);
        ncc.setGhiChu(ghiChu);
        ncc.setTrangThai(trangThai);
        
        return ncc;
    }
    
    private void clearForm() {
        selectedId = -1;
        txtMaNCC.setText("");
        txtTenNCC.setText("");
        txtNguoiDaiDien.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtCongNo.setText("");
        txtGhiChu.setText("");
        chkTrangThai.setSelected(true);
        
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        txtMaNCC.requestFocus();
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
}