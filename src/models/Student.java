package models;

public class Student extends User {
    private String batch, department, admissionYear, passingYear;
    private double cgpa;

    public Student(String id, String name, String password) {
        super(id, name, password, "STUDENT");
    }

    @Override
    public void showDashboard() {
        views.StudentDashboard sd = new views.StudentDashboard(this);
        sd.setVisible(true);
    }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(String year) { this.admissionYear = year; }

    public String getPassingYear() { return passingYear; }
    public void setPassingYear(String year) { this.passingYear = year; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
}