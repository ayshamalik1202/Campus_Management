package models;

public class Admin extends User {
    public Admin(String id, String name, String password) {
        super(id, name, password, "ADMIN");
    }

    @Override
    public void showDashboard() {
        new views.AdminDashboard().setVisible(true);
    }
}