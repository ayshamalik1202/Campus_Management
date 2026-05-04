package views;

import controllers.AdminController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class RegistrationPanel extends JFrame {
    private Color bgDark = new Color(13, 11, 23);
    private Color fieldDark = new Color(30, 30, 45);
    private Color neonPink = new Color(236, 21, 137);
    private Color neonCyan = new Color(0, 169, 157);
    private Color textWhite = new Color(245, 245, 245);

    private JComboBox<String> roleBox = new JComboBox<>(new String[]{"STUDENT", "TEACHER"});
    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField emailField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);

    // Student Specific Fields
    private JTextField deptField = new JTextField(15);
    private JTextField batchField = new JTextField(15);
    private JTextField adminYearField = new JTextField(15);
    private JTextField passYearField = new JTextField(15);

    private JButton regBtn = new JButton("Register & Save");
    private JPanel dynamicPanel = new JPanel(new GridLayout(0, 2, 10, 15));

    public RegistrationPanel() {
        setTitle("University Portal - Official Registration");
        setSize(480, 650);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(bgDark);

        JLabel header = new JLabel("Create Official Account", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setForeground(neonPink);
        header.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        dynamicPanel.setBackground(bgDark);
        dynamicPanel.setBorder(new EmptyBorder(10, 40, 10, 40));

        styleComponent(roleBox);
        styleComponent(idField);
        styleComponent(nameField);
        styleComponent(emailField);
        styleComponent(passField);
        styleComponent(deptField);
        styleComponent(batchField);
        styleComponent(adminYearField);
        styleComponent(passYearField);

        updateForm("STUDENT");

        JScrollPane scrollPane = new JScrollPane(dynamicPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        roleBox.addActionListener(e -> updateForm(roleBox.getSelectedItem().toString()));
        regBtn.addActionListener(e -> handleRegistration());

        regBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        regBtn.setBackground(neonCyan);
        regBtn.setForeground(bgDark);
        regBtn.setFocusPainted(false);
        regBtn.setPreferredSize(new Dimension(200, 40));
        regBtn.setBorder(null);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgDark);
        bottomPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        bottomPanel.add(regBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void styleComponent(JComponent c) {
        c.setBackground(fieldDark);
        c.setForeground(textWhite);
        c.setFont(new Font("SansSerif", Font.PLAIN, 14));
        if (c instanceof JTextField) {
            ((JTextField) c).setCaretColor(textWhite);
            ((JTextField) c).setBorder(new LineBorder(new Color(60, 60, 80), 1));
        }
        if (c instanceof JComboBox) {
            c.setBorder(new LineBorder(new Color(60, 60, 80), 1));
        }
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textWhite);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }

    private void updateForm(String role) {
        dynamicPanel.removeAll();
        dynamicPanel.add(createStyledLabel("Select Role:")); dynamicPanel.add(roleBox);
        dynamicPanel.add(createStyledLabel("Official ID:")); dynamicPanel.add(idField);
        dynamicPanel.add(createStyledLabel("Full Name:")); dynamicPanel.add(nameField);
        dynamicPanel.add(createStyledLabel("Email Address:")); dynamicPanel.add(emailField);
        dynamicPanel.add(createStyledLabel("Password:")); dynamicPanel.add(passField);

        if (role.equals("STUDENT")) {
            dynamicPanel.add(createStyledLabel("Department:")); dynamicPanel.add(deptField);
            dynamicPanel.add(createStyledLabel("Batch:")); dynamicPanel.add(batchField);
            dynamicPanel.add(createStyledLabel("Admission Year:")); dynamicPanel.add(adminYearField);
            dynamicPanel.add(createStyledLabel("Passing Year:")); dynamicPanel.add(passYearField);
        }

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void handleRegistration() {
        String role = roleBox.getSelectedItem().toString();
        String email = emailField.getText().trim();
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String password = new String(passField.getPassword());

        // Validation: Empty Fields
        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        // Security Check: Teacher Email Domain
        if (role.equals("TEACHER") && !email.toLowerCase().endsWith("@metrouni.com")) {
            JOptionPane.showMessageDialog(this, "Error: Teachers must use @metrouni.com email.",
                    "Unauthorized", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AdminController controller = new AdminController();

        // FIX: Student fields (dept, batch, years) are now actually saved
        if (role.equals("STUDENT")) {
            String dept = deptField.getText().trim();
            String batch = batchField.getText().trim();
            String admissionYear = adminYearField.getText().trim();
            String passingYear = passYearField.getText().trim();

            if (dept.isEmpty() || batch.isEmpty() || admissionYear.isEmpty() || passingYear.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all student fields.");
                return;
            }
            controller.registerStudent(id, name, password, email, dept, batch, admissionYear, passingYear);
        } else {
            // FIX: Email is now passed and saved for teachers too
            controller.registerUser(id, name, password, role, email);
        }

        JOptionPane.showMessageDialog(this, "Account created successfully for " + role);
        new RoleSelection().setVisible(true);
        dispose();
    }
}