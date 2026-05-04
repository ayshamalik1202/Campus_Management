package utils;

import java.io.*;
import java.util.*;
import models.*;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private static transient DataStore instance; // FIX: transient so it's not serialized
    private Map<String, User> users = new HashMap<>();
    private List<Grade> gradeList = new ArrayList<>();
    private static final String FILE_NAME = "university_data.ser";

    private DataStore() {
        // Default admin account - only created on first run
        users.put("admin", new Admin("admin", "System Admin", "123"));
    }

    // FIX: readResolve() prevents multiple instances after deserialization
    protected Object readResolve() {
        instance = this;
        return instance;
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = loadData();
        }
        return instance;
    }

    // --- User Operations ---
    public void addUser(User u) { users.put(u.getId(), u); saveData(); }
    public User getUser(String id) { return users.get(id); }
    public Map<String, User> getUsers() { return users; }

    // --- Grade Operations ---
    public void addGrade(Grade g) { gradeList.add(g); saveData(); }

    // FIX: Added missing getGradeList() getter
    public List<Grade> getGradeList() { return gradeList; }

    public void updateGrade(String sid, String cid, double marks) {
        for (Grade g : gradeList) {
            if (g.getStudentId().equals(sid) && g.getCourseId().equals(cid)) {
                g.setScore(marks);
                saveData();
                return;
            }
        }
        // FIX: No silent failure — caller can check return or we log it
        System.out.println("Warning: No grade found for student=" + sid + " course=" + cid);
    }

    // FIX: Added missing removeGrade() method (used by Admin and Teacher dashboards)
    public void removeGrade(String sid, String cid) {
        gradeList.removeIf(g -> g.getStudentId().equals(sid) && g.getCourseId().equals(cid));
        saveData();
    }

    // --- Persistence ---
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DataStore loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new DataStore();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (DataStore) ois.readObject();
        } catch (Exception e) {
            System.out.println("Could not load saved data, starting fresh. Reason: " + e.getMessage());
            return new DataStore();
        }
    }
}