package models;

import java.io.Serializable;

public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentId, department, batch, semester, courseId, letterGrade;
    private double score;
    private boolean isPublished = false;

    public Grade(String sid, String dept, String batch, String sem, String cid, double score) {
        this.studentId = sid;
        this.department = dept;
        this.batch = batch;
        this.semester = sem;
        this.courseId = cid;
        this.score = score;
        this.letterGrade = calculateLetter(score);
    }

    // FIX: Complete letter grade scale (was missing C and D)
    private String calculateLetter(double s) {
        if (s >= 80) return "A+";
        if (s >= 75) return "A";
        if (s >= 70) return "A-";
        if (s >= 65) return "B+";
        if (s >= 60) return "B";
        if (s >= 55) return "B-";
        if (s >= 50) return "C+";
        if (s >= 45) return "C";
        if (s >= 40) return "D";
        return "F";
    }

    public void setScore(double score) {
        this.score = score;
        this.letterGrade = calculateLetter(score);
    }

    public String getStudentId() { return studentId; }
    public String getDepartment() { return department; }
    public String getBatch() { return batch; }
    public String getSemester() { return semester; }
    public String getCourseId() { return courseId; }
    public String getLetterGrade() { return letterGrade; }
    public double getScore() { return score; }
    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean status) { this.isPublished = status; }
}