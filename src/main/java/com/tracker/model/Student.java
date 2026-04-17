package com.tracker.model;

/**
 * Represents a Student entity in the LaplateformeTracker application.
 * Holds all relevant student information including dynamic grading logic references.
 */
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private double average;

    /**
     * Empty constructor for creating an empty Student instance.
     */
    public Student() {}

    /**
     * Fully parameterized constructor to initialize a new Student.
     * @param id The unique identifier
     * @param firstName The student's first name
     * @param lastName The student's last name
     * @param age The student's age
     * @param email The student's email address
     */
    public Student(int id, String firstName, String lastName, int age, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

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
