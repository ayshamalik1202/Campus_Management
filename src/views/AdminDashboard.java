package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Grade;
import utils.DataStore;

public class AdminDashboard extends JFrame {
    private Color navyBlue = new Color(28, 40, 65);
    private Color lavender = new Color(230, 230, 250);
    private Color darkPink = new Color(170, 50, 90);
    private Color offWhite = new Color(248, 248, 248);

    private JTable scrutinyTable;
    private DefaultTableModel tableModel;

    public AdminDashboard() {
        setTitle("University Scrutiny Board");
        setSize(950, 650);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(offWhite);

        JLabel header = new JLabel("Academic Scrutiny Panel", SwingConstants.CENTER);
        header.setFont(new Font("Georgia", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(navyBlue);
        header.setForeground(offWhite);
        header.setPreferredSize(new Dimension(0, 70));
        add(header, BorderLayout.NORTH);

        // FIX: Table now shows Department and Batch columns too
        tableModel = new DefaultTableModel(
                new String[]{"Student ID", "Dept", "Batch", "Semester", "Course", "Marks", "Grade", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        scrutinyTable = new JTable(tableModel);
        scrutinyTable.setFont(new Font("Georgia", Font.PLAIN, 14));
        scrutinyTable.setRowHeight(30);

        refreshTableData();
        add(new JScrollPane(scrutinyTable), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        controls.setBackground(offWhite);

        JButton logoutBtn = new JButton("Logout");
        JButton updateBtn = new JButton("Update Marks");
        JButton removeBtn = new JButton("Remove Record");
        JButton publishBtn = new JButton("Publish Selected");

        styleBtn(logoutBtn, offWhite, navyBlue);
        styleBtn(updateBtn, lavender, navyBlue);
        styleBtn(removeBtn, Color.WHITE, new Color(150, 0, 0));
        styleBtn(publishBtn, darkPink, Color.WHITE);

        logoutBtn.addActionListener(e -> { new RoleSelection().setVisible(true); dispose(); });
        updateBtn.addActionListener(e -> handleUpdate());
        removeBtn.addActionListener(e -> handleRemove());
        publishBtn.addActionListener(e -> handlePublish());

        controls.add(logoutBtn);
        controls.add(updateBtn);
        controls.add(removeBtn);
        controls.add(publishBtn);
        add(controls, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void styleBtn(JButton b, Color bg, Color fg) {
        b.setFont(new Font("Georgia", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(fg);
    }

    private void refreshTableData() {
        tableModel.setRowCount(0);
        // FIX: getGradeList() now exists in DataStore
        for (Grade g : DataStore.getInstance().getGradeList()) {
            tableModel.addRow(new Object[]{
                g.getStudentId(), g.getDepartment(), g.getBatch(),
                g.getSemester(), g.getCourseId(), g.getScore(),
                g.getLetterGrade(), g.isPublished() ? "PUBLISHED" : "PENDING"
            });
        }
    }

    private void handleUpdate() {
        int row = scrutinyTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row first."); return; }

        String currentVal = String.valueOf(tableModel.getValueAt(row, 5));
        String val = JOptionPane.showInputDialog(this, "Enter new marks (0-100):", currentVal);
        if (val == null || val.trim().isEmpty()) return;

        try {
            double marks = Double.parseDouble(val.trim());
            if (marks < 0 || marks > 100) throw new NumberFormatException();
            DataStore.getInstance().updateGrade(
                    (String) tableModel.getValueAt(row, 0),
                    (String) tableModel.getValueAt(row, 4),
                    marks);
            refreshTableData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number between 0 and 100.");
        }
    }

    private void handleRemove() {
        int row = scrutinyTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a row first."); return; }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this record permanently?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // FIX: removeGrade() now exists in DataStore
            DataStore.getInstance().removeGrade(
                    (String) tableModel.getValueAt(row, 0),
                    (String) tableModel.getValueAt(row, 4));
            refreshTableData();
        }
    }

    private void handlePublish() {
        int[] selectedRows = scrutinyTable.getSelectedRows();
        if (selectedRows.length == 0) { JOptionPane.showMessageDialog(this, "Please select rows to publish."); return; }

        for (int row : selectedRows) {
            String sid = (String) tableModel.getValueAt(row, 0);
            String cid = (String) tableModel.getValueAt(row, 4);
            for (Grade g : DataStore.getInstance().getGradeList()) {
                if (g.getStudentId().equals(sid) && g.getCourseId().equals(cid)) {
                    g.setPublished(true);
                }
            }
        }
        DataStore.getInstance().saveData();
        refreshTableData();
        JOptionPane.showMessageDialog(this, selectedRows.length + " record(s) published.");
    }
}