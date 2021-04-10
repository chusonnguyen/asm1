package com.company;

public interface StudentEnrolmentManager {
    void add(String studentId, String semester, String courseId) throws InterruptedException;
    void update(String studentID, String semester) throws InterruptedException;
    void delete(String studentId, String courseId, String semester) throws InterruptedException;
    void getOne();
    void getOne(String courseID, String semester);
    void getOne(String semester);
    void getAll();
}
