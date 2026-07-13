// view/customer/frmKhachHang.java
package hdkmanagement.view.customer;

import hdkmanagement.controller.KhachHangController;
import hdkmanagement.model.KhachHang;
import hdkmanagement.util.MessageUtil;
import hdkmanagement.util.ValidateUtil;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class frmKhachHang {

    private JPanel mainPanel, searchPanel, tablePanel, formPanel, buttonPanel;
    private JTextField txtSearch, txtMaKH, txtHoTen, txtSDT, txtEmail, txtCongNo;
    private JTextArea txtDiaChi, txtGhiChu;
    private JCheckBox chkTrangThai;
    private JTable tblKhachHang;
    private DefaultTableModel tableModel;
    private JButton btnSearch, btnRefresh, btnAdd, btnUpdate, btnDelete, btnClear;

    private KhachHangController khachHangController;
    private int selectedId = -1;

    // ===== ĐỒNG BỘ MÀU VỚI frmDangNhap / frmDashboard =====
    private final Color GRAD_START   = new Color(25, 118, 210);   // Xanh dương đậm
    private final Color GRAD_END     = new Color(13, 71, 161);    // Xanh navy
    private final Color PRIMARY     = new Color(25, 118, 210);    // Xanh chủ đạo
    private final Color PRIMARY_HOVER = new Color(21, 101, 192);  // Xanh hover
    private final Color SUCCESS     = new Color(76, 175, 80);     // Xanh lá
    private final Color SUCCESS_HOVER = new Color(56, 142, 60);
    private final Color WARNING     = new Color(255, 193, 7);     // Vàng
    private final Color WARNING_HOVER = new Color(255, 160, 0);
    private final Color DANGER      = new Color(244, 67, 54);     // Đỏ
    private final Color DANGER_HOVER = new Color(211, 47, 47);
    private final Color GRAY        = new Color(158, 158, 158);   // Xám
    private final Color GRAY_HOVER  = new Color(130, 130, 130);
    private final Color BG          = new Color(241, 245, 249);   // Nền xám nhạt
    private final Color CARD        = Color.WHITE;                // Trắng
    private final Color TEXT_DARK   = new Color(55, 55, 55);      // Chữ đậm
    private final Color TEXT_MUTED  = new Color(158, 158, 158);   // Chữ mờ
    private final Color FIELD_BG    = new Color(245, 245, 245);   // Nền field
    private final Color FIELD_BORDER = new Color(224, 224, 224);  // Viền field
    private final Color FIELD_FOCUS = new Color(25, 118, 210);    // Viền focus
    private final Color TABLE_HEADER_BG = new Color(25, 118, 210);
    private final Color TABLE_SELECTION = new Color(187, 222, 251);

    // ===== ĐỒNG BỘ FONT VỚI frmDangNhap =====
    private final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD, 13);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 17);
    private final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    private final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);

    public frmKhachHang() {
        khachHangController = new KhachHangController();
        initComponents();
        loadData();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(16, 16));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ===== HEADER =====
        JLabel title = new JLabel("Quản lý khách hàng");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Tìm kiếm, thêm, sửa và quản lý thông tin khách hàng");
        subtitle.setFont(FONT_SUBTITLE);
        subtitle.setForeground(TEXT_MUTED);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.setOpaque(false);
        headerPanel.add(title);
        headerPanel.add(subtitle);

        // ===== SEARCH PANEL =====
        searchPanel = createCardPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        txtSearch = createTextField(28);
        btnSearch = createButton("Tìm kiếm", PRIMARY, PRIMARY_HOVER);
        btnRefresh = createButton("Làm mới", GRAY, GRAY_HOVER);

        searchPanel.add(createLabel("Từ khóa"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        JPanel topPanel = new JPanel(new BorderLayout(0, 14));
        topPanel.setOpaque(false);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        // ===== TABLE PANEL =====
        tablePanel = createCardPanel(new BorderLayout(0, 10));
        tablePanel.add(createSectionTitle("Danh sách khách hàng"), BorderLayout.NORTH);

        String[] columns = {"Mã KH", "Họ tên", "SĐT", "Email", "Địa chỉ", "Công nợ", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKhachHang = new JTable(tableModel);
        styleTable(tblKhachHang);

        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
        scrollPane.setBorder(new LineBorder(new Color(226, 232, 240)));
        scrollPane.getViewport().setBackground(CARD);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // ===== FORM PANEL =====
        formPanel = createCardPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(410, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        addFormTitle(gbc, "Thông tin khách hàng");

        txtMaKH = createTextField(18);
        txtHoTen = createTextField(18);
        txtSDT = createTextField(18);
        txtEmail = createTextField(18);
        txtCongNo = createTextField(18);
        txtCongNo.setEditable(false);
        txtCongNo.setBackground(new Color(248, 250, 252));

        txtDiaChi = createTextArea();
        txtGhiChu = createTextArea();

        chkTrangThai = new JCheckBox("Hoạt động");
        chkTrangThai.setFont(FONT_PLAIN);
        chkTrangThai.setBackground(CARD);
        chkTrangThai.setSelected(true);

        addField(gbc, 1, "Mã KH", txtMaKH);
        addField(gbc, 2, "Họ tên", txtHoTen);
        addField(gbc, 3, "Số điện thoại", txtSDT);
        addField(gbc, 4, "Email", txtEmail);
        addField(gbc, 5, "Địa chỉ", new JScrollPane(txtDiaChi));
        addField(gbc, 6, "Công nợ", txtCongNo);
        addField(gbc, 7, "Ghi chú", new JScrollPane(txtGhiChu));
        addField(gbc, 8, "Trạng thái", chkTrangThai);

        // ===== BUTTON PANEL =====
        buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setOpaque(false);

        btnAdd = createButton("➕ Thêm", SUCCESS, SUCCESS_HOVER);
        btnUpdate = createButton("✏️ Sửa", WARNING, WARNING_HOVER);
        btnDelete = createButton("🗑️ Xóa", DANGER, DANGER_HOVER);
        btnClear = createButton("🔄 Nhập lại", GRAY, GRAY_HOVER);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 8, 0, 8);
        formPanel.add(buttonPanel, gbc);

        // ===== CENTER PANEL =====
        JPanel centerPanel = new JPanel(new BorderLayout(16, 0));
        centerPanel.setOpaque(false);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(formPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        addEvents();

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    // ===== CARD DẠNG BO GÓC, VẼ TAY GIỐNG frmDashboard =====
    private JPanel createCardPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(CARD);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        return panel;
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_SECTION);
        label.setForeground(TEXT_DARK);
        return label;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text + ":");
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_MUTED);
        return label;
    }

    private JTextField createTextField(int columns) {
        JTextField txt = new JTextField(columns);
        txt.setFont(FONT_PLAIN);
        txt.setPreferredSize(new Dimension(230, 38));
        txt.setBackground(FIELD_BG);
        txt.setForeground(TEXT_DARK);
        txt.setCaretColor(TEXT_DARK);
        txt.setBorder(new CompoundBorder(
                new LineBorder(FIELD_BORDER, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));

        txt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txt.setBorder(new CompoundBorder(
                        new LineBorder(FIELD_FOCUS, 2, true),
                        new EmptyBorder(5, 9, 5, 9)
                ));
                txt.setBackground(CARD);
            }
            @Override
            public void focusLost(FocusEvent e) {
                txt.setBorder(new CompoundBorder(
                        new LineBorder(FIELD_BORDER, 1, true),
                        new EmptyBorder(6, 10, 6, 10)
                ));
                txt.setBackground(FIELD_BG);
            }
        });

        return txt;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea(3, 18);
        area.setFont(FONT_PLAIN);
        area.setForeground(TEXT_DARK);
        area.setBackground(FIELD_BG);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new CompoundBorder(
                new LineBorder(FIELD_BORDER, 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));

        area.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                area.setBorder(new CompoundBorder(
                        new LineBorder(FIELD_FOCUS, 2, true),
                        new EmptyBorder(5, 7, 5, 7)
                ));
                area.setBackground(CARD);
            }
            @Override
            public void focusLost(FocusEvent e) {
                area.setBorder(new CompoundBorder(
                        new LineBorder(FIELD_BORDER, 1, true),
                        new EmptyBorder(6, 8, 6, 8)
                ));
                area.setBackground(FIELD_BG);
            }
        });

        return area;
    }

    // ===== NÚT BO GÓC, VẼ TAY GIỐNG frmDashboard (createFlatButton) =====
    private JButton createButton(String text, Color bg, Color hoverBg) {
        JButton button = new JButton(text) {
            private Color currentBg = bg;

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (isEnabled()) {
                            currentBg = hoverBg;
                            repaint();
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        currentBg = bg;
                        repaint();
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isEnabled()) {
                            currentBg = hoverBg.darker();
                            repaint();
                        }
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (isEnabled()) {
                            currentBg = hoverBg;
                            repaint();
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(isEnabled() ? currentBg : GRAY.brighter());
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(FONT_BUTTON);
        button.setForeground(CARD);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleTable(JTable table) {
        table.setFont(FONT_PLAIN);
        table.setRowHeight(36);
        table.setSelectionBackground(TABLE_SELECTION);
        table.setSelectionForeground(TEXT_DARK);
        table.setGridColor(new Color(226, 232, 240));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(CARD);
        header.setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(180);
        table.getColumnModel().getColumn(4).setPreferredWidth(180);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
    }

    private void addFormTitle(GridBagConstraints gbc, String text) {
        JLabel title = createSectionTitle(text);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 14, 8);
        formPanel.add(title, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 8, 5, 8);
    }

    private void addField(GridBagConstraints gbc, int y, String label, Component field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.25;
        gbc.insets = new Insets(5, 8, 5, 8);
        formPanel.add(createLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.75;

        if (field instanceof JScrollPane) {
            field.setPreferredSize(new Dimension(230, 72));
            ((JScrollPane) field).setBorder(new LineBorder(FIELD_BORDER, 1, true));
        }

        formPanel.add(field, gbc);
    }

    private void addEvents() {
        btnSearch.addActionListener(e -> search());

        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadData();
        });

        txtSearch.addActionListener(e -> search());

        tblKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblKhachHang.getSelectedRow();
                if (row >= 0) {
                    selectRow(row);
                }
            }
        });

        btnAdd.addActionListener(e -> addKhachHang());
        btnUpdate.addActionListener(e -> updateKhachHang());
        btnDelete.addActionListener(e -> deleteKhachHang());
        btnClear.addActionListener(e -> clearForm());
    }

    public void loadData() {
        List<KhachHang> list = khachHangController.getAllKhachHang();
        displayData(list);
    }

    private void search() {
        String keyword = txtSearch.getText().trim();
        List<KhachHang> list = khachHangController.searchKhachHang(keyword);
        displayData(list);
    }

    private void displayData(List<KhachHang> list) {
        tableModel.setRowCount(0);

        if (list != null) {
            for (KhachHang kh : list) {
                Object[] row = {
                        kh.getMaKH_Code(),
                        kh.getHoTen(),
                        kh.getSdt(),
                        kh.getEmail(),
                        kh.getDiaChi(),
                        ValidateUtil.formatCurrencyVND(kh.getCongNo()),
                        kh.isTrangThai() ? "🟢 Hoạt động" : "🔴 Ngừng"
                };
                tableModel.addRow(row);
            }
        }
    }

    private void selectRow(int row) {
        String maKH = tableModel.getValueAt(row, 0).toString();
        List<KhachHang> list = khachHangController.getAllKhachHang();

        for (KhachHang kh : list) {
            if (kh.getMaKH_Code().equals(maKH)) {
                selectedId = kh.getMaKH();

                txtMaKH.setText(kh.getMaKH_Code());
                txtHoTen.setText(kh.getHoTen());
                txtSDT.setText(kh.getSdt());
                txtEmail.setText(kh.getEmail());
                txtDiaChi.setText(kh.getDiaChi());
                txtCongNo.setText(ValidateUtil.formatCurrencyVND(kh.getCongNo()));
                txtGhiChu.setText(kh.getGhiChu());
                chkTrangThai.setSelected(kh.isTrangThai());

                btnAdd.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                break;
            }
        }
    }

    private void addKhachHang() {
        KhachHang kh = getFormData();
        if (kh == null) return;

        if (khachHangController.addKhachHang(kh)) {
            MessageUtil.showInfo("✅ Thêm khách hàng thành công!");
            clearForm();
            loadData();
        }
    }

    private void updateKhachHang() {
        if (selectedId == -1) {
            MessageUtil.showWarning("⚠️ Vui lòng chọn khách hàng cần sửa!");
            return;
        }

        KhachHang kh = getFormData();
        if (kh == null) return;

        kh.setMaKH(selectedId);

        if (khachHangController.updateKhachHang(kh)) {
            MessageUtil.showInfo("✅ Cập nhật khách hàng thành công!");
            clearForm();
            loadData();
        }
    }

    private void deleteKhachHang() {
        if (selectedId == -1) {
            MessageUtil.showWarning("⚠️ Vui lòng chọn khách hàng cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(mainPanel,
                "Bạn có chắc chắn muốn xóa khách hàng này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (khachHangController.deleteKhachHang(selectedId)) {
                MessageUtil.showInfo("✅ Xóa khách hàng thành công!");
                clearForm();
                loadData();
            }
        }
    }

    private KhachHang getFormData() {
        String maKH = txtMaKH.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();
        boolean trangThai = chkTrangThai.isSelected();

        if (maKH.isEmpty() || hoTen.isEmpty()) {
            MessageUtil.showWarning("⚠️ Vui lòng nhập mã và họ tên khách hàng!");
            return null;
        }

        if (sdt.isEmpty()) {
            MessageUtil.showWarning("⚠️ Vui lòng nhập số điện thoại!");
            return null;
        }

        if (!ValidateUtil.isValidPhone(sdt)) {
            MessageUtil.showWarning("⚠️ Số điện thoại không hợp lệ!");
            return null;
        }

        KhachHang kh = new KhachHang();
        kh.setMaKH_Code(maKH);
        kh.setHoTen(hoTen);
        kh.setSdt(sdt);
        kh.setEmail(email);
        kh.setDiaChi(diaChi);
        kh.setCongNo(0);
        kh.setGhiChu(ghiChu);
        kh.setTrangThai(trangThai);

        return kh;
    }

    private void clearForm() {
        selectedId = -1;

        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtCongNo.setText("");
        txtGhiChu.setText("");
        chkTrangThai.setSelected(true);

        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        txtMaKH.requestFocus();
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}