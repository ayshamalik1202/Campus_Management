package models;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String content;
    private Date date;

    public Notice(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Date getDate() { return date; }
}