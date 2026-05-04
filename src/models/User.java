package models;
import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id, name, password, role, email;

    public User(String id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = "";
    }

    public abstract void showDashboard();

    public String getId() { return id; }
    public String getName() { return name; }           // FIX: Added missing getName()
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getEmail() { return email; }         // FIX: Added missing getEmail()
    public void setEmail(String email) { this.email = email; } // FIX: Added setter
}