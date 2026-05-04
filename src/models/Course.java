package models;

import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private String courseId;
    private String courseName;
    private int credits;

    public Course(String id, String name, int credits) {
        this.courseId = id;
        this.courseName = name;
        this.credits = credits;
    }

    // FIX: Added missing getCourseId() getter
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
}