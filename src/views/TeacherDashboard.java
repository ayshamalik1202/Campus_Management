package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import models.Grade;
import models.Teacher;
import utils.DataStore;

public class TeacherDashboard extends JFrame {
    private Teacher currentTeacher;

    private Color navyBlue = new Color(28, 40, 65);
    private Color lavender = new Color(230, 230, 250);
    private Color darkPink = new Color(170, 50, 90);
    private Color offWhite = new Color(248, 248, 248);

    private JTextField stuIdField = new JTextField(15);
    private JTextField deptField = new JTextField(15);
    private JTextField batchField = new JTextField(15);
    private JTextField semField = new JTextField(15);
    private JTextField courseField = new JTextField(15);
    private JTextField marksField = new JTextField(15);

    public TeacherDashboard(Teacher teacher) {
        this.currentTeacher = teacher;
        // FIX: teacher.getName() now exists (added to User.java)
        setTitle("Teacher Portal - " + teacher.getName());
        setSize(850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(offWhite);

        // --- Left Panel: Teacher Profile ---
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setPreferredSize(new Dimension(260, 0));
        profilePanel.setBackground(navyBlue);
        profilePanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(navyBlue, 1),
                BorderFactory.createEmptyBorder(30, 20, 20, 20)));

        profilePanel.add(createStyledLabel("Instructor: " + teacher.getName(), Color.WHITE));
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(createStyledLabel("ID: " + teacher.getId(), Color.WHITE));
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(createStyledLabel("Email: " + teacher.getEmail(), Color.WHITE)); // FIX: getEmail() now exists
        profilePanel.add(Box.createVerticalStrut(30));

        JButton logoutBtn = new JButton("Logout Account");
        logoutBtn.setFont(new Font("Georgia", Font.BOLD, 12));
        logoutBtn.setBackground(lavender);
        logoutBtn.setForeground(navyBlue);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> { new RoleSelection().setVisible(true); dispose(); });
        profilePanel.add(logoutBtn);

        // --- Center Panel: Grade Entry Form ---
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 15));
        formPanel.setBackground(offWhite);

        TitledBorder formBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "University Ledger Entry", 0, 0,
                new Font("Georgia", Font.ITALIC, 14), navyBlue);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                formBorder, BorderFactory.createEmptyBorder(20, 30, 20, 30)));

        formPanel.add(createStyledLabel("Student ID:", navyBlue));   formPanel.add(stuIdField);
        formPanel.add(createStyledLabel("Department:", navyBlue));    formPanel.add(deptField);
        formPanel.add(createStyledLabel("Batch:", navyBlue));         formPanel.add(batchField);
        formPanel.add(createStyledLabel("Semester:", navyBlue));      formPanel.add(semField);
        formPanel.add(createStyledLabel("Course ID:", navyBlue));     formPanel.add(courseField);
        formPanel.add(createStyledLabel("Marks (0-100):", navyBlue)); formPanel.add(marksField);

        JButton submitBtn = new JButton("Submit New Record");
        styleButton(submitBtn, darkPink, Color.WHITE);

        JButton updateBtn = new JButton("Update Existing");
        styleButton(updateBtn, lavender, navyBlue);

        JButton removeBtn = new JButton("Remove Record");
        styleButton(removeBtn, offWhite, new Color(150, 0, 0));
        removeBtn.setBorder(BorderFactory.createLineBorder(new Color(150, 0, 0)));

        formPanel.add(new JLabel("")); formPanel.add(submitBtn);
        formPanel.add(new JLabel("")); formPanel.add(updateBtn);
        formPanel.add(new JLabel("")); formPanel.add(removeBtn);

        submitBtn.addActionListener(e -> handleSubmission(false));
        updateBtn.addActionListener(e -> handleSubmission(true));
        removeBtn.addActionListener(e -> handleRemove());

        add(profilePanel, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Georgia", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
    }

    private JLabel createStyledLabel(String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(color);
        lbl.setFont(new Font("Georgia", Font.BOLD, 13));
        return lbl;
    }

    private void handleRemove() {
        String sid = stuIdField.getText().trim();
        String cid = courseField.getText().trim();

        if (sid.isEmpty() || cid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID and Course ID.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this record permanently?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // FIX: removeGrade() now exists in DataStore
            DataStore.getInstance().removeGrade(sid, cid);
            JOptionPane.showMessageDialog(this, "Record successfully removed.");
            clearFields();
        }
    }

    private void handleSubmission(boolean isUpdate) {
        String sid = stuIdField.getText().trim();
        String dept = deptField.getText().trim();
        String batch = batchField.getText().trim();
        String sem = semField.getText().trim();
        String cid = courseField.getText().trim();
        String marksStr = marksField.getText().trim();

        if (sid.isEmpty() || dept.isEmpty() || batch.isEmpty()
                || sem.isEmpty() || cid.isEmpty() || marksStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try {
            double marks = Double.parseDouble(marksStr);
            if (marks < 0 || marks > 100) throw new NumberFormatException();

            if (isUpdate) {
                // FIX: updateGrade now logs a warning if record not found (no silent failure)
                DataStore.getInstance().updateGrade(sid, cid, marks);
                JOptionPane.showMessageDialog(this, "Record updated successfully.");
            } else {
                // Check for duplicate before inserting
                boolean exists = DataStore.getInstance().getGradeList().stream()
                        .anyMatch(g -> g.getStudentId().equals(sid) && g.getCourseId().equals(cid));
                if (exists) {
                    JOptionPane.showMessageDialog(this,
                            "A record for this student and course already exists. Use 'Update Existing'.");
                    return;
                }
                Grade newGrade = new Grade(sid, dept, batch, sem, cid, marks);
                DataStore.getInstance().addGrade(newGrade);
                JOptionPane.showMessageDialog(this, "New record added successfully.");
            }
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Marks must be a number between 0 and 100.");
        }
    }

    private void clearFields() {
        stuIdField.setText("");
        deptField.setText("");
        batchField.setText("");
        semField.setText("");
        courseField.setText("");
        marksField.setText("");
    }
}