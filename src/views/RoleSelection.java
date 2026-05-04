package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class RoleSelection extends JFrame {
    private Color backgroundDark = new Color(13, 11, 23);
    private Color neonPink = new Color(236, 21, 137);
    private Color buttonDark = new Color(30, 30, 45);
    private Color brightWhite = new Color(255, 255, 255);

    public RoleSelection() {
        setTitle("Smart Campus - Select User Type");
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundDark);

        JLabel title = new JLabel("SMART CAMPUS PORTAL", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setOpaque(true);
        title.setBackground(backgroundDark);
        title.setForeground(neonPink);
        title.setPreferredSize(new Dimension(0, 100));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(backgroundDark);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        // FIX: Pass role in its original UPPERCASE form so LoginPanel's equalsIgnoreCase works cleanly
        String[] roles = {"ADMIN", "TEACHER", "STUDENT"};
        for (String role : roles) {
            JButton btn = new JButton(role);
            btn.setFont(new Font("SansSerif", Font.BOLD, 15));
            btn.setBackground(buttonDark);
            btn.setForeground(brightWhite);
            btn.setFocusPainted(false);
            btn.setBorder(new LineBorder(new Color(60, 60, 80), 1));
            btn.addActionListener(e -> openLogin(role)); // FIX: Pass role as-is (was mangled before)
            buttonPanel.add(btn);
        }
        add(buttonPanel, BorderLayout.CENTER);

        JButton signupBtn = new JButton("Register New Account");
        signupBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        signupBtn.setForeground(neonPink);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setBorderPainted(false);
        signupBtn.addActionListener(e -> {
            new RegistrationPanel().setVisible(true);
            dispose();
        });

        JPanel southPanel = new JPanel();
        southPanel.setBackground(backgroundDark);
        southPanel.add(signupBtn);
        add(southPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void openLogin(String role) {
        new LoginPanel(role).setVisible(true);
        this.dispose();
    }
}