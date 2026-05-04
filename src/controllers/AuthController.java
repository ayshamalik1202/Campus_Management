package controllers;

import models.User;
import utils.DataStore;

public class AuthController {
    public User login(String id, String password) {
        User user = DataStore.getInstance().getUser(id);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
