package com.company;

import java.util.ArrayList;
import java.util.List;

public class Student {
    protected String id;
    protected String name;
    protected String birthday;
    ArrayList<Course> enrolledCourse;
    public Student(String id, String name, String birthday){
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.enrolledCourse = new ArrayList<Course>();
    }
    public String getId(){
        return id;
    }
    public String getName() { return name;}
    public String getBirthday() {return birthday;}

    public void setEnrolledCourse(Course course) {
        this.enrolledCourse.add(course);
    }

    public void printCourse(int sem){
        for (Course course : enrolledCourse){
            System.out.println(course.toString());
        }
    }
    public boolean checkValidity(String courseId){
        boolean valid = true;
        for (Course course : enrolledCourse){
            if (course.getId().equals(courseId)){
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
