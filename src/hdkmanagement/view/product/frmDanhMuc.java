// view/product/frmDanhMuc.java
package hdkmanagement.view.product;

import hdkmanagement.controller.DanhMucController;
import hdkmanagement.model.DanhMuc;
import hdkmanagement.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class frmDanhMuc {
    
    private JPanel mainPanel;
    private JPanel tablePanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    
    private JTable tblDanhMuc;
    private DefaultTableModel tableModel;
    
    private JTextField txtMaDM;
    private JTextField txtTenDM;
    private JTextArea txtMoTa;
    private JCheckBox chkTrangThai;
    
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnRefresh;
    
    private DanhMucController danhMucController;
    private int selectedId = -1;
    
    private final Color PRIMARY_COLOR = new Color(52, 73, 94);
    private final Color SECONDARY_COLOR = new Color(41, 128, 185);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color WARNING_COLOR = new Color(241, 196, 15);
    private final Color BG_COLOR = new Color(236, 240, 241);
    
    public frmDanhMuc() {
        danhMucController = new DanhMucController();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // ===== Table Panel =====
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📋 Danh sách danh mục",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        
        JTextField txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(200, 30));
        
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSearch.setBackground(SECONDARY_COLOR);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRefresh = new JButton("Làm mới");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefresh.setBackground(new Color(149, 165, 166));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);
        
        // Table
        String[] columns = {"Mã DM", "Tên danh mục", "Mô tả", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDanhMuc = new JTable(tableModel);
        tblDanhMuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblDanhMuc.setRowHeight(30);
        tblDanhMuc.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblDanhMuc.getTableHeader().setBackground(SECONDARY_COLOR);
        tblDanhMuc.getTableHeader().setForeground(Color.WHITE);
        tblDanhMuc.setSelectionBackground(new Color(41, 128, 185, 50));
        
        tblDanhMuc.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblDanhMuc.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblDanhMuc.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblDanhMuc.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(tblDanhMuc);
        scrollPane.setBorder(null);
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // ===== Form Panel =====
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "📝 Thông tin danh mục",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));
        formPanel.setPreferredSize(new Dimension(350, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã danh mục:"), gbc);
        txtMaDM = new JTextField(15);
        txtMaDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMaDM.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(txtMaDM, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tên danh mục:"), gbc);
        txtTenDM = new JTextField(15);
        txtTenDM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTenDM.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(txtTenDM, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Mô tả:"), gbc);
        txtMoTa = new JTextArea(3, 15);
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane moTaScroll = new JScrollPane(txtMoTa);
        moTaScroll.setPreferredSize(new Dimension(200, 60));
        gbc.gridx = 1;
        formPanel.add(moTaScroll, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
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
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        formPanel.add(buttonPanel, gbc);
        
        // Add to mainPanel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(formPanel, BorderLayout.EAST);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Events
        addEvents();
        
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void addEvents() {
        tblDanhMuc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblDanhMuc.getSelectedRow();
                if (row >= 0) {
                    selectRow(row);
                }
            }
        });
        
        btnAdd.addActionListener(e -> addDanhMuc());
        btnUpdate.addActionListener(e -> updateDanhMuc());
        btnDelete.addActionListener(e -> deleteDanhMuc());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadData());
    }
    
    public void loadData() {
        List<DanhMuc> list = danhMucController.getAllDanhMuc();
        displayData(list);
    }
    
    private void displayData(List<DanhMuc> list) {
        tableModel.setRowCount(0);
        if (list != null) {
            for (DanhMuc dm : list) {
                Object[] row = {
                    dm.getMaDM_Code(),
                    dm.getTenDanhMuc(),
                    dm.getMoTa(),
                    dm.isTrangThai() ? "Hoạt động" : "Ngừng"
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void selectRow(int row) {
        String maDM = tableModel.getValueAt(row, 0).toString();
        List<DanhMuc> list = danhMucController.getAllDanhMuc();
        for (DanhMuc dm : list) {
            if (dm.getMaDM_Code().equals(maDM)) {
                selectedId = dm.getMaDM();
                txtMaDM.setText(dm.getMaDM_Code());
                txtTenDM.setText(dm.getTenDanhMuc());
                txtMoTa.setText(dm.getMoTa());
                chkTrangThai.setSelected(dm.isTrangThai());
                
                btnAdd.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                break;
            }
        }
    }
    
    private void addDanhMuc() {
        DanhMuc dm = getFormData();
        if (dm == null) return;
        
        if (danhMucController.addDanhMuc(dm)) {
            MessageUtil.showInfo("Thêm danh mục thành công!");
            clearForm();
            loadData();
        }
    }
    
    private void updateDanhMuc() {
        if (selectedId == -1) {
            MessageUtil.showWarning("Vui lòng chọn danh mục cần sửa!");
            return;
        }
        
        DanhMuc dm = getFormData();
        if (dm == null) return;
        dm.setMaDM(selectedId);
        
        if (danhMucController.updateDanhMuc(dm)) {
            MessageUtil.showInfo("Cập nhật danh mục thành công!");
            clearForm();
            loadData();
        }
    }
    
    private void deleteDanhMuc() {
        if (selectedId == -1) {
            MessageUtil.showWarning("Vui lòng chọn danh mục cần xóa!");
            return;
        }
        
        if (danhMucController.deleteDanhMuc(selectedId)) {
            MessageUtil.showInfo("Xóa danh mục thành công!");
            clearForm();
            loadData();
        }
    }
    
    private DanhMuc getFormData() {
        String maDM = txtMaDM.getText().trim();
        String tenDM = txtTenDM.getText().trim();
        String moTa = txtMoTa.getText().trim();
        boolean trangThai = chkTrangThai.isSelected();
        
        if (maDM.isEmpty() || tenDM.isEmpty()) {
            MessageUtil.showWarning("Vui lòng nhập mã và tên danh mục!");
            return null;
        }
        
        DanhMuc dm = new DanhMuc();
        dm.setMaDM_Code(maDM);
        dm.setTenDanhMuc(tenDM);
        dm.setMoTa(moTa);
        dm.setTrangThai(trangThai);
        return dm;
    }
    
    private void clearForm() {
        selectedId = -1;
        txtMaDM.setText("");
        txtTenDM.setText("");
        txtMoTa.setText("");
        chkTrangThai.setSelected(true);
        
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        txtMaDM.requestFocus();
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
}