// view/report/frmBaoCao.java
package hdkmanagement.view.report;

import javax.swing.*;
import java.awt.*;

public class frmBaoCao {
    
    private JPanel mainPanel;
    private JLabel lblTitle;
    
    public frmBaoCao() {
        initComponents();
    }
    
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        lblTitle = new JLabel("BÁO CÁO THỐNG KÊ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        
        JLabel lblContent = new JLabel("📈 Đang phát triển...", SwingConstants.CENTER);
        lblContent.setFont(new Font("Arial", Font.BOLD, 18));
        lblContent.setForeground(Color.GRAY);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(lblContent);
        
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }
    
    public void loadData() {
        // Load dữ liệu sau
    }
}