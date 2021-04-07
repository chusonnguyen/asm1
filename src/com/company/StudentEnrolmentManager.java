package com.company;

public interface StudentEnrolmentManager {
    void add() throws InterruptedException;
    void update(String studentID, String semester) throws InterruptedException;
    void delete(StudentEnrolment enrolment) throws InterruptedException;
    void getOne();
    void getOne(String courseID, String semester);
    void getOne(String semester);
    void getAll();
}
