package com.company;

import java.util.ArrayList;

public class Course {
    protected String id;
    protected String name;
    protected int numOfCredit;
    ArrayList<Student> enrollStudentList;
    public  Course(String id, String name,int numOfCredit){
        this.id = id;
        this.name = name;
        this.numOfCredit = numOfCredit;
        this.enrollStudentList = new ArrayList<Student>();
    }
    public void enroll(Student student){
        this.enrollStudentList.add(student);
    }
    public void printStudent(){
        for (Student student : enrollStudentList){
            System.out.println(student.getId());
        }
    }
    public String getName() {
        return name;
    }
    public String getId(){
        return id;
    }
    public int getCredit() { return numOfCredit;}

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", numOfCredit=" + numOfCredit +
                '}';
    }

