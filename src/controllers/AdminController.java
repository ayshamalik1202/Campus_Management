package controllers;

import models.*;
import utils.DataStore;

public class AdminController {

    // FIX: Added email parameter so it is actually saved on the user
    public void registerUser(String id, String name, String password, String role, String email) {
        User newUser;
        if (role.equalsIgnoreCase("STUDENT")) {
            newUser = new Student(id, name, password);
        } else if (role.equalsIgnoreCase("TEACHER")) {
            newUser = new Teacher(id, name, password);
        } else {
            newUser = new Admin(id, name, password);
        }
        newUser.setEmail(email); // FIX: Email is now actually stored
        DataStore.getInstance().addUser(newUser);
    }

    // FIX: Overload for Student with extra fields (batch, dept, admissionYear, passingYear)
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