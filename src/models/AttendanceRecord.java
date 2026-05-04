package models;

import java.io.Serializable;
import java.util.Date;

// FIX: Implements Serializable so it can be saved to the .ser file
public class AttendanceRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentId;
    private Date date;
    private boolean isPresent;

    public AttendanceRecord(String studentId, Date date, boolean isPresent) {
        this.studentId = studentId;
        this.date = date;
        this.isPresent = isPresent;
    }

    // FIX: Added getters (were completely missing)
    public String getStudentId() { return studentId; }
    public Date getDate() { return date; }
    public boolean isPresent() { return isPresent; }
}