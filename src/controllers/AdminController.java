package controllers;

import models.*;
import utils.DataStore;

public class AdminController {
    public void registerUser(String id, String name, String password, String role, String email) {
        User newUser;
        if (role.equalsIgnoreCase("STUDENT")) {
            newUser = new Student(id, name, password);
        } else if (role.equalsIgnoreCase("TEACHER")) {
            newUser = new Teacher(id, name, password);
        } else {
            newUser = new Admin(id, name, password);
        }
        newUser.setEmail(email);
        DataStore.getInstance().addUser(newUser);
    }

    public void registerStudent(String id, String name, String password, String email,
                                String dept, String batch, String admissionYear, String passingYear) {
        Student s = new Student(id, name, password);
        s.setEmail(email);
        s.setDepartment(dept);
        s.setBatch(batch);
        s.setAdmissionYear(admissionYear);
        s.setPassingYear(passingYear);
        DataStore.getInstance().addUser(s);
    }
}
