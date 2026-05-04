package views;

import controllers.AuthController;
import java.awt.*;
import javax.swing.*;
import models.User;

public class LoginPanel extends JFrame {
    private AuthController auth = new AuthController();
    private String selectedRole;
    private JTextField idField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    private JButton loginBtn = new JButton("Login");

    public LoginPanel(String role) {
        this.selectedRole = role;
        setTitle(role + " Login");
        setSize(350, 250);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Enter ID:"));
        add(idField);
        add(new JLabel("Enter Password:"));
        add(passField);
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String pass = new String(passField.getPassword());

            User user = auth.login(id, pass);

            if (user != null) {
                // FIX: selectedRole is already uppercase, user.getRole() is uppercase — consistent now
                if (user.getRole().equalsIgnoreCase(selectedRole)) {
                    user.showDashboard();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Role mismatch! Your account is registered as: " + user.getRole());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Password.");
            }
        });

        setLocationRelativeTo(null);
    }
}