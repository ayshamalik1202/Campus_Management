package views;

import java.awt.*;
import javax.swing.*;
import models.Grade;
import models.Student;
import utils.DataStore;

public class StudentDashboard extends JFrame {
    private Student currentStudent;

    private Color navyBlue = new Color(28, 40, 65);
    private Color lavender = new Color(230, 230, 250);
    private Color darkPink = new Color(170, 50, 90);
    private Color offWhite = new Color(248, 248, 248);

    private JTextField searchId = new JTextField(10);
    private JTextField searchSem = new JTextField(10);
    private JTextArea resultArea = new JTextArea(5, 30);

    public StudentDashboard(Student student) {
        this.currentStudent = student;
        // FIX: student.getName() now exists (added to User.java)
        setTitle("Student Portal - " + student.getName());
        setSize(850, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(offWhite);

        // --- Left Panel: Student Profile ---
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setPreferredSize(new Dimension(280, 0));
        profilePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Official Student Info", 0, 0, null, Color.WHITE));
        profilePanel.setBackground(navyBlue);

        // FIX: student.getEmail() now exists (added to User.java)
        JLabel[] labels = {
            new JLabel("Name: " + student.getName()),
            new JLabel("Student ID: " + student.getId()),
            new JLabel("Email: " + student.getEmail()),
            new JLabel("Department: " + nullSafe(student.getDepartment())),
            new JLabel("Batch: " + nullSafe(student.getBatch())),
            new JLabel("Admission Year: " + nullSafe(student.getAdmissionYear())),
            new JLabel("Expected Passing: " + nullSafe(student.getPassingYear()))
        };

        for (JLabel lbl : labels) {
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Georgia", Font.PLAIN, 13));
            lbl.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            profilePanel.add(lbl);
        }

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(lavender);
        logoutBtn.addActionListener(e -> { new RoleSelection().setVisible(true); dispose(); });
        profilePanel.add(Box.createVerticalStrut(30));
        profilePanel.add(logoutBtn);

        // --- Center Panel: Result Search ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(offWhite);

        JPanel searchForm = new JPanel(new GridLayout(3, 2, 5, 5));
        searchForm.setBackground(offWhite);
        searchForm.setBorder(BorderFactory.createTitledBorder("Search Semester Final Result"));

        // FIX: Pre-fill the student's own ID for convenience
        searchId.setText(student.getId());

        searchForm.add(new JLabel("Student ID:")); searchForm.add(searchId);
        searchForm.add(new JLabel("Semester Name:")); searchForm.add(searchSem);

        JButton fetchBtn = new JButton("Fetch Result");
        fetchBtn.setBackground(darkPink);
        fetchBtn.setForeground(Color.WHITE);
        fetchBtn.setOpaque(true);
        fetchBtn.setBorderPainted(false);
        searchForm.add(new JLabel(""));
        searchForm.add(fetchBtn);

        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        resultArea.setBackground(lavender);
        resultArea.setForeground(navyBlue);
        resultArea.setBorder(BorderFactory.createTitledBorder("Academic Transcript"));

        mainPanel.add(searchForm, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        fetchBtn.addActionListener(e -> handleResultSearch());

        add(profilePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    // FIX: Helper to avoid displaying "null" for optional fields
    private String nullSafe(String value) {
        return (value == null || value.isEmpty()) ? "N/A" : value;
    }

    private void handleResultSearch() {
        String id = searchId.getText().trim();
        String sem = searchSem.getText().trim();

        if (id.isEmpty() || sem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID and Semester.");
            return;
        }

        StringBuilder results = new StringBuilder("OFFICIAL ACADEMIC TRANSCRIPT\n");
        results.append("================================\n");
        results.append(String.format("Student ID : %s%n", id));
        results.append(String.format("Semester   : %s%n", sem));
        results.append("================================\n");

        boolean foundAny = false;
        boolean hasPending = false;

        for (Grade g : DataStore.getInstance().getGradeList()) {
            if (g.getStudentId().equals(id) && g.getSemester().equalsIgnoreCase(sem)) {
                if (g.isPublished()) {
                    results.append(String.format("Course: %-10s | Grade: %-3s | Marks: %.1f%n",
                            g.getCourseId(), g.getLetterGrade(), g.getScore()));
                    foundAny = true;
                } else {
                    hasPending = true;
                }
            }
        }

        if (!foundAny) {
            results.append("\nNO PUBLISHED RESULTS FOUND.\n");
            if (hasPending) {
                results.append("Results exist but are pending admin approval.");
            } else {
                results.append("No records found for this ID and semester.");
            }
        }

        resultArea.setText(results.toString());
    }
}