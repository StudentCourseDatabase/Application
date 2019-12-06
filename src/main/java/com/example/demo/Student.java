package com.example.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String dob;
    private String url;

    @ManyToMany
    private Set<Course> courses;

    public Student() {
    }

    public Student(String firstName, String lastName, String dob, String url) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.url = url;
    }

    public Student(String firstName, String lastName, String dob, String url, Set<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.url = url;
        this.courses = courses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
