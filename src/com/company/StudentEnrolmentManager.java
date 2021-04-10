package com.company;

public interface StudentEnrolmentManager {
    void add(String studentId, String semester, String courseId) throws InterruptedException;
    void update(String studentID, String semester) throws InterruptedException;
    void delete(String studentId, String courseId, String semester) throws InterruptedException;
    void getAllCourse(String studentId, String semester);
    void getAllStudent(String courseID, String semester);
    void getOne(String semester);
    void getAll();
}
