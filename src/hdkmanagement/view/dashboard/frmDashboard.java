// view/dashboard/frmDashboard.java
package hdkmanagement.view.dashboard;

import hdkmanagement.util.SessionManager;
import hdkmanagement.view.auth.frmDangNhap;
import hdkmanagement.view.customer.frmKhachHang;
import hdkmanagement.view.employee.frmNhanVien;
import hdkmanagement.view.product.frmDanhMuc;
import hdkmanagement.view.product.frmSanPham;
import hdkmanagement.view.purchase.frmNhapHang;
import hdkmanagement.view.sale.frmBanHang;
import hdkmanagement.view.supplier.frmNhaCungCap;
import hdkmanagement.view.inventory.frmKhoHang;
import hdkmanagement.view.report.frmBaoCao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class frmDashboard extends JFrame {

    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JLabel lblTitle;
    private JLabel lblUserInfo;
    private JButton btnLogout;
    private CardLayout cardLayout;
    private JPanel panelDashboard;

    // ===== CÁC FORM =====
    private frmSanPham sanPhamForm;
    private frmDanhMuc danhMucForm;
    private frmKhachHang khachHangForm;
    private frmNhaCungCap nhaCungCapForm;
    private frmNhanVien nhanVienForm;
    private frmNhapHang nhapHangForm;
    private frmBanHang banHangForm;
    private frmKhoHang khoHangForm;
    private frmBaoCao baoCaoForm;

    private final List<JButton> menuButtons = new ArrayList<>();
    private JButton selectedMenuButton;

    // ===== BẢNG MÀU (đồng bộ với màn hình Đăng nhập) =====
    // Xanh dương chủ đạo giống panel bên trái + nút "ĐĂNG NHẬP" của frmDangNhap
    private final Color GRAD_START    = new Color(59, 130, 246);   // blue-500
    private final Color GRAD_END      = new Color(29, 78, 216);    // blue-700
    private final Color PRIMARY_BLUE  = new Color(37, 99, 235);    // blue-600 (màu nút chính)
    private final Color PRIMARY_BLUE_DARK = new Color(29, 78, 216); // blue-700 (hover)
    private final Color MENU_BG       = new Color(30, 41, 59);     // slate-800
    private final Color MENU_HOVER    = new Color(51, 65, 85);     // slate-700
    private final Color MENU_SELECTED = new Color(37, 99, 235);    // blue-600
    private final Color MENU_TEXT     = new Color(148, 163, 184);  // slate-400
    private final Color BG_COLOR      = new Color(248, 250, 252);  // slate-50 (giống nền trắng bên phải)
    private final Color TEXT_DARK     = new Color(30, 41, 59);     // slate-800 (giống tiêu đề "Chào mừng trở lại!")
    private final Color TEXT_MUTED    = new Color(100, 116, 139);  // slate-500 (giống mô tả phụ)
    private final Color DANGER_COLOR  = new Color(239, 68, 68);    // red-500
    private final Color DANGER_HOVER  = new Color(220, 38, 38);    // red-600
    private final Color WHITE         = new Color(255, 255, 255);

    public frmDashboard() {
        // Khởi tạo các form
        sanPhamForm = new frmSanPham();
        danhMucForm = new frmDanhMuc();
        khachHangForm = new frmKhachHang();
        nhaCungCapForm = new frmNhaCungCap();
        nhanVienForm = new frmNhanVien();
        nhapHangForm = new frmNhapHang();
        banHangForm = new frmBanHang();
        khoHangForm = new frmKhoHang();
        baoCaoForm = new frmBaoCao();

        initComponents();
        setLocationRelativeTo(null);
        showDashboard();
    }

    private void initComponents() {
        setTitle("Công Ty HDK - Hệ thống quản lý");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 600));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);

        createHeader();
        createMenu();
        createContentPanels();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void createHeader() {
        headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, GRAD_START, getWidth(), 0, GRAD_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 68));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));

        // Left: Logo + Title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        leftPanel.setOpaque(false);

        JLabel lblLogo = new JLabel("🏗️");
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

        lblTitle = new JLabel("Công Ty HDK");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));
        lblTitle.setForeground(WHITE);

        leftPanel.add(lblLogo);
        leftPanel.add(lblTitle);

        // Right: Avatar + User info + Logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        rightPanel.setOpaque(false);

        SessionManager session = SessionManager.getInstance();
        String employeeName = session.getCurrentEmployeeName();
        String roleName = session.getCurrentUser().getQuyen().getTenQuyen();

        JLabel lblAvatar = new JLabel(initials(employeeName), SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 55));
                g2d.fillOval(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        lblAvatar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAvatar.setForeground(WHITE);
        lblAvatar.setPreferredSize(new Dimension(36, 36));

        JPanel userTextPanel = new JPanel();
        userTextPanel.setOpaque(false);
        userTextPanel.setLayout(new BoxLayout(userTextPanel, BoxLayout.Y_AXIS));

        JLabel lblName = new JLabel(employeeName);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblName.setForeground(WHITE);
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblRole = new JLabel(roleName);
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblRole.setForeground(new Color(219, 234, 254)); // blue-100, đồng bộ tông xanh
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);

        userTextPanel.add(lblName);
        userTextPanel.add(lblRole);
        lblUserInfo = lblName;

        btnLogout = createFlatButton("ĐĂNG XUẤT", DANGER_COLOR, DANGER_HOVER, WHITE);
        btnLogout.addActionListener(e -> logout());

        rightPanel.add(lblAvatar);
        rightPanel.add(userTextPanel);
        rightPanel.add(btnLogout);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
    }

    private String initials(String fullName) {
        if (fullName == null || fullName.isBlank()) return "?";
        String[] parts = fullName.trim().split("\\s+");
        String first = String.valueOf(parts[0].charAt(0));
        String last = parts.length > 1 ? String.valueOf(parts[parts.length - 1].charAt(0)) : "";
        return (first + last).toUpperCase();
    }

    private JButton createFlatButton(String text, Color bg, Color hoverBg, Color fg) {
        JButton button = new JButton(text) {
            private Color currentBg = bg;

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentBg = hoverBg;
                        repaint();
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        currentBg = bg;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(currentBg);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void createMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(MENU_BG);
        menuPanel.setPreferredSize(new Dimension(240, 0));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 10, 0));

        String[][] menuItems = {
            {"📊", "Tổng quan", "dashboard"},
            {"📦", "Sản phẩm", "sanpham"},
            {"📁", "Danh mục", "danhmuc"},
            {"👤", "Khách hàng", "khachhang"},
            {"🏢", "Nhà cung cấp", "nhacungcap"},
            {"👨‍💼", "Nhân viên", "nhanvien"},
            {"📥", "Nhập hàng", "nhaphang"},
            {"💰", "Bán hàng", "banhang"},
            {"📊", "Kho hàng", "khohang"},
            {"📈", "Báo cáo", "baocao"}
        };

        JLabel sectionLabel = new JLabel("  MENU CHÍNH");
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        sectionLabel.setForeground(new Color(100, 116, 139));
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 0));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(sectionLabel);

        for (String[] item : menuItems) {
            JButton btn = createMenuItem(item[0], item[1], item[2]);
            menuButtons.add(btn);
            menuPanel.add(btn);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        }

        menuPanel.add(Box.createVerticalGlue());
    }

    private JButton createMenuItem(String icon, String text, String action) {
        JButton btn = new JButton("  " + icon + "   " + text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean selected = this == selectedMenuButton;
                boolean hovered = getModel().isRollover();

                if (selected) {
                    g2d.setColor(MENU_SELECTED);
                    g2d.fill(new RoundRectangle2D.Double(10, 2, getWidth() - 20, getHeight() - 4, 12, 12));
                    g2d.setColor(new Color(255, 255, 255, 200));
                    g2d.fillRoundRect(0, getHeight() / 2 - 10, 4, 20, 4, 4);
                } else if (hovered) {
                    g2d.setColor(MENU_HOVER);
                    g2d.fill(new RoundRectangle2D.Double(10, 2, getWidth() - 20, getHeight() - 4, 12, 12));
                }
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(MENU_TEXT);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder(11, 12, 11, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(240, 46));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn != selectedMenuButton) btn.setForeground(WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn != selectedMenuButton) btn.setForeground(MENU_TEXT);
            }
        });

        btn.addActionListener(e -> {
            selectMenuItem(btn);
            showPanel(action);
        });

        return btn;
    }

    private void selectMenuItem(JButton btn) {
        for (JButton b : menuButtons) {
            b.setForeground(MENU_TEXT);
        }
        selectedMenuButton = btn;
        btn.setForeground(WHITE);
        menuPanel.repaint();
    }

    private void createContentPanels() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Dashboard
        panelDashboard = createDashboardPanel();
        contentPanel.add(panelDashboard, "dashboard");

        // ===== CÁC MODULE THỰC TẾ =====
        contentPanel.add(sanPhamForm.getPanel(), "sanpham");
        contentPanel.add(danhMucForm.getPanel(), "danhmuc");
        contentPanel.add(khachHangForm.getPanel(), "khachhang");
        contentPanel.add(nhaCungCapForm.getPanel(), "nhacungcap");
        contentPanel.add(nhanVienForm.getPanel(), "nhanvien");
        contentPanel.add(nhapHangForm.getPanel(), "nhaphang");
        contentPanel.add(banHangForm.getPanel(), "banhang");
        contentPanel.add(khoHangForm.getPanel(), "khohang");
        contentPanel.add(baoCaoForm.getPanel(), "baocao");
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 24));
        panel.setBackground(BG_COLOR);

        // ---- Tiêu đề chào mừng ----
        JPanel welcomeBox = new JPanel(new BorderLayout());
        welcomeBox.setBackground(BG_COLOR);

        SessionManager session = SessionManager.getInstance();
        JLabel lblWelcome = new JLabel("Chào mừng trở lại, " + session.getCurrentEmployeeName() + " 👋");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblWelcome.setForeground(TEXT_DARK);

        JLabel lblSub = new JLabel("Tổng quan hệ thống quản lý kinh doanh vật liệu xây dựng");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(TEXT_MUTED);
        lblSub.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        JPanel textStack = new JPanel();
        textStack.setLayout(new BoxLayout(textStack, BoxLayout.Y_AXIS));
        textStack.setOpaque(false);
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        textStack.add(lblWelcome);
        textStack.add(lblSub);

        welcomeBox.add(textStack, BorderLayout.WEST);

        // ---- Các thẻ thống kê nhanh ----
        JPanel statsGrid = new JPanel(new GridLayout(1, 4, 18, 0));
        statsGrid.setBackground(BG_COLOR);
        statsGrid.add(createStatCard("📦", "Sản phẩm", getProductCount(), PRIMARY_BLUE));
        statsGrid.add(createStatCard("💰", "Doanh thu", getRevenue(), new Color(16, 185, 129))); // emerald-500
        statsGrid.add(createStatCard("📥", "Đơn nhập", getImportCount(), new Color(249, 115, 22))); // orange-500
        statsGrid.add(createStatCard("👤", "Khách hàng", getCustomerCount(), new Color(14, 165, 233))); // sky-500

        // ---- Khu vực nội dung chính ----
        JPanel infoCard = createCardPanel();
        infoCard.setLayout(new GridBagLayout());
        JLabel lblInfo = new JLabel("📈  Chào mừng đến với hệ thống quản lý Công Ty HDK");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblInfo.setForeground(TEXT_MUTED);
        infoCard.add(lblInfo);

        JPanel centerWrap = new JPanel(new BorderLayout(0, 18));
        centerWrap.setBackground(BG_COLOR);
        centerWrap.add(statsGrid, BorderLayout.NORTH);
        centerWrap.add(infoCard, BorderLayout.CENTER);

        panel.add(welcomeBox, BorderLayout.NORTH);
        panel.add(centerWrap, BorderLayout.CENTER);

        return panel;
    }

    // ===== LẤY DỮ LIỆU THỐNG KÊ =====
    private String getProductCount() {
        try {
            hdkmanagement.controller.SanPhamController ctrl = new hdkmanagement.controller.SanPhamController();
            int count = ctrl.getAllSanPham().size();
            return String.valueOf(count);
        } catch (Exception e) {
            return "--";
        }
    }

    private String getRevenue() {
        try {
            hdkmanagement.dao.HoaDonDAO dao = new hdkmanagement.dao.HoaDonDAO();
            double revenue = dao.getTotalRevenue();
            return String.format("%,.0f ₫", revenue);
        } catch (Exception e) {
            return "-- ₫";
        }
    }

    private String getImportCount() {
        try {
            hdkmanagement.dao.PhieuNhapDAO dao = new hdkmanagement.dao.PhieuNhapDAO();
            int count = dao.getAll().size();
            return String.valueOf(count);
        } catch (Exception e) {
            return "--";
        }
    }

    private String getCustomerCount() {
        try {
            hdkmanagement.controller.KhachHangController ctrl = new hdkmanagement.controller.KhachHangController();
            int count = ctrl.getAllKhachHang().size();
            return String.valueOf(count);
        } catch (Exception e) {
            return "--";
        }
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        return card;
    }

    private JPanel createStatCard(String icon, String label, String value, Color accent) {
        JPanel card = createCardPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        card.setPreferredSize(new Dimension(0, 100));

        JLabel lblIcon = new JLabel(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 30));
                g2d.fillOval(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcon.setPreferredSize(new Dimension(40, 40));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblValue.setForeground(TEXT_DARK);
        lblValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLabel.setForeground(TEXT_MUTED);
        lblLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(lblValue);
        textPanel.add(lblLabel);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.add(lblIcon, BorderLayout.WEST);
        topRow.add(textPanel, BorderLayout.CENTER);

        card.add(topRow, BorderLayout.CENTER);
        return card;
    }

    private void showPanel(String name) {
        cardLayout.show(contentPanel, name);
        
        // Reload dữ liệu khi chuyển panel
        switch (name) {
            case "dashboard":
                break;
            case "sanpham":
                sanPhamForm.loadData();
                break;
            case "danhmuc":
                danhMucForm.loadData();
                break;
            case "khachhang":
                khachHangForm.loadData();
                break;
            case "nhacungcap":
                nhaCungCapForm.loadData();
                break;
            case "nhanvien":
                nhanVienForm.loadData();
                break;
            case "nhaphang":
                break;
            case "banhang":
                break;
            case "khohang":
                break;
            case "baocao":
                break;
        }
    }

    private void showDashboard() {
        if (!menuButtons.isEmpty()) {
            selectMenuItem(menuButtons.get(0));
        }
        showPanel("dashboard");
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn đăng xuất?",
            "Xác nhận đăng xuất",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.getInstance().logout();
            dispose();
            new frmDangNhap().setVisible(true);
        }
    }
}