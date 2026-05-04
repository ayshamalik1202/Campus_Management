package controllers;
import models.Grade;
import models.Student;
import models.Teacher;
import models.User;
import utils.DataStore;

public class TeacherController {
    private Teacher teacher;

    public TeacherController(Teacher t) {
        this.teacher = t;
    }
    public void inputGrade(String studentId, String dept, String batch,
                           String semester, String courseId, double gradeValue) {
        User u = DataStore.getInstance().getUser(studentId);
        if (u instanceof Student) {
            Grade newGrade = new Grade(studentId, dept, batch, semester, courseId, gradeValue);
            DataStore.getInstance().addGrade(newGrade);
            System.out.println("Grade submitted for " + u.getName());
        } else {
            System.out.println("Error: Student ID '" + studentId + "' not found.");
        }
    }
}
