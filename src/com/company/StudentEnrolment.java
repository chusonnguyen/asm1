package com.company;

public class StudentEnrolment {
    private String studentId;
    private String courseId;
    private String semester;

    public StudentEnrolment(String studentId, String courseId, String semester) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "StudentEnrolment{" +
                "studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}
