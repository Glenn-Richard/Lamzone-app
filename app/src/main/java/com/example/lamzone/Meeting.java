package com.example.lamzone;

import java.sql.Timestamp;
import java.util.List;

public class Meeting {

    //VARIABLES
    private  int id;
    private long timestamp;
    private String subject;
    private List<String> emails;


    //CONSTRUCTOR
    public Meeting(int id, long timestamp, String subject, List<String> emails) {
        this.id = id;
        this.timestamp = timestamp;
        this.subject = subject;
        this.emails = emails;
    }

    //GETTER AND SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}

