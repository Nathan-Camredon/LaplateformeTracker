package com.tracker.model;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private double average;

    // Constructeur vide
    public Student() {}

    // Constructeur complet
    public Student(int id, String firstName, String lastName, int age, double average) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.average = average;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getAverage() { return average; }
    public void setAverage(double average) { this.average = average; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + firstName + " " + lastName + '\'' +
                ", average=" + average +
                '}';
    }
}
