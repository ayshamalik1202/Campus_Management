package models;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {
    private String qualification;
    private String experience;
    private List<String> publications = new ArrayList<>();

    public Teacher(String id, String name, String password) {
        super(id, name, password, "TEACHER");
    }

    @Override
    public void showDashboard() {
        new views.TeacherDashboard(this).setVisible(true);
    }

    public String getQualification() { return qualification; }
    public void setQualification(String q) { this.qualification = q; }

    public String getExperience() { return experience; }
    public void setExperience(String e) { this.experience = e; }

    public void addPublication(String link) { this.publications.add(link); }
    public List<String> getPublications() { return publications; }
}