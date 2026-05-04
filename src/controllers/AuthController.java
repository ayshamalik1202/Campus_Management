package controllers;

import models.User;
import utils.DataStore;

public class AuthController {
    // NOTE: Passwords are compared as plaintext here.
    // For production, use BCrypt or similar hashing before storing/comparing.
    public User login(String id, String password) {
        User user = DataStore.getInstance().getUser(id);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}