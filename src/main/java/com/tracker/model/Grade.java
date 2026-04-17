package com.tracker.model;

/**
 * Represents a Grade (Note) obtained by a particular student for a specific subject.
 */
public class Grade {
    private int id;
    private int value;
    private String subject;
    private int studentid;

    public Grade() {
    }

    public Grade(int value, String subject, int studentid) {
        this.value = value;
        this.subject = subject;
        this.studentid = studentid;
    }

    public Grade(int id, int value, String subject, int studentid) {
        this.id = id;
        this.value = value;
        this.subject = subject;
        this.studentid = studentid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    @Override
    public String toString() {
        return subject + " : " + value;
    }
}
