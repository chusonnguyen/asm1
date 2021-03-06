package com.company;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class Main implements StudentEnrolmentManager {
    ArrayList<Student> studentsList;
    ArrayList<Course> coursesList;
    ArrayList<StudentEnrolment> enrolments;

    final String enrollmentData = "enrolmentList.cvs";
    final String studentData = "Students.cvs";
    final String courseData = "Courses.cvs";

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String ENROLLMENT_FILE_HEADER = "Course,Student,Semester";
    private static final String STUDENT_FILE_HEADER = "Student ID, Name, Birthday";
    private static final String COURSE_FILE_HEADER = "Course ID, Name, Number of credit";

    boolean checkStudentId(String id){
        boolean found = false;
        for (int i = 0; i < studentsList.size();i++){
            if ( studentsList.get(i).getId().equals(id)){
                found = true;
            }
        }
        return found;
    }
    boolean checkCourseId(String id){
        boolean found = false;
        for (int i = 0; i < coursesList.size();i++){
            if ( coursesList.get(i).getId().equals(id)){
                found = true;
            }
        }
        return found;
    }
    boolean checkStudentEnrollment(String id, String semester){
        boolean found = false;
        for ( StudentEnrolment enrollment : enrolments){
            if ( enrollment.getStudentId().equals(id) && enrollment.getSemester().equals(semester)){
                found = true;
            }
        }
        return found;
    }
    boolean checkCourseEnrollment(String id, String semester){
        boolean found = false;
        for ( StudentEnrolment enrollment : enrolments){
            if ( enrollment.getCourseId().equals(id) && enrollment.getSemester().equals(semester)){
                found = true;
            }
        }
        return found;
    }
    public Main(){
        studentsList = new ArrayList<Student>();
        coursesList = new ArrayList<Course>();
        enrolments = new ArrayList<StudentEnrolment>();
    }

    Course getCourse(String courseID){
        Course newCourse = null;
        for ( Course course : coursesList){
            if ( courseID.equals(course.getId())){
                return course;
            }
        }
        return newCourse;
    }

    Student getStudent(String studentId){
        Student newStudent = null;
        for ( Student student : studentsList){
            if ( studentId.equals(student.getId())){ return student;}
        }
        return newStudent;
    }

    void Menu(){
        System.out.println("========== ========== ========== ========== ========== ==========");
        System.out.println("1. Enroll student\n2. Update enrollment\n3. Print all course for one student in 1 semester\n" +
                "4. Print all students of 1 course\n5. Print all courses offered in 1 semester\n6. Print all student and course information\n7. Exit");
    }
    void waitScreen(){
        System.out.println("Press enter to continue...");
        try{
            System.in.read();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void add(String studentId, String semester, String courseId){
        for (Student stu : studentsList){
            if ( stu != null && studentId.equals(stu.getId())){
                if (!stu.checkValidity(courseId)){
                    System.out.println("This student has already enrolled in this course.");
                    return;
                }
                for ( Course course : coursesList){
                    if ( course != null && courseId.equals(course.getId())){
                        course.enroll(stu);
                        stu.setEnrolledCourse(course);
                    }
                }
            }
        }
        enrolments.add(new StudentEnrolment(studentId,courseId,semester));
        System.out.println("Enrollment successful !!!");
    }
    public void Interface() throws InterruptedException{
        Scanner in = new Scanner(System.in);
        boolean correct = false;
        while(!correct) {
            try {
                Menu();
                System.out.print("Please enter a command: ");
                int choice = in.nextInt();
                in.nextLine();
                switch (choice) {
                    case 1: {
                        String enrollOrder;
                        System.out.println("Please enter [year] [semester] [studentId] [courseId] ( example :2021 A s3742891 cosc123 ): ");
                        try {
                            Date d = new Date();
                            enrollOrder = in.nextLine();
                            String[] tokens = enrollOrder.split(" ");
                            if (Integer.parseInt(tokens[0]) < d.getYear()+1900){
                                System.out.println("You can only add course for current year.");
                                Thread.sleep(1000);
                                return;
                            }
                            if (!tokens[1].equals("A") && !tokens[1].equals("B") && !tokens[1].equals("C")){
                                System.out.println("Semester must be A/B/C.");
                                Thread.sleep(1000);
                                return;
                            }
                            if ( checkCourseId(tokens[3]) && checkStudentId(tokens[2])){
                                add(tokens[2],tokens[0].concat(tokens[1]), tokens[3]);
                            } else {
                                System.out.println("Invalid course id / student id.");
                                return;
                            }
                            Thread.sleep(1000);
                        } catch (NoSuchElementException e) {
                            System.out.println("Invalid input");
                            Thread.sleep(1000);
                        } catch (NumberFormatException e) {
                            System.out.println("Year must be number.");
                            Thread.sleep(1000);
                        } catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("Incorrect input");
                            Thread.sleep(1000);
                        }
                        break;
                    }
                    case 2: {
                        String command;
                        System.out.print("Please enter student id and semester, sepearted by space(example: s3742891 2021A): ");
                        try {
                            command = in.nextLine();
                            String[] tokens = command.split(" ");
                            if (checkStudentEnrollment(tokens[0],tokens[1])){
                                for ( StudentEnrolment enrollment : enrolments){
                                    if ( enrollment.getStudentId().equals(tokens[0]) && enrollment.getSemester().equals(tokens[1])){
                                        System.out.println(enrollment.toString());
                                    }
                                }
                            } else {
                                System.out.println("Student " + tokens[0] + " does not enroll in any courses in semester " + tokens[1]);
                                Thread.sleep(2000);
                                break;
                            }
                            update(tokens[0], tokens[1]);
                        } catch (NoSuchElementException e){
                            break;
                        } catch (IndexOutOfBoundsException e){
                            System.out.println("Invalid command.");
                            break;
                        }
                        Thread.sleep(1000);
                        break;
                    }
                    case 3: {
                        String command;
                        System.out.print("Please enter student and semester, sepearted by space(example: s3742891 2021A): ");
                        try {
                            command = in.nextLine();
                            String[] tokens = command.split(" ");
                            if (checkStudentEnrollment(tokens[0],tokens[1])){
                               getAllCourse(tokens[0], tokens[1]);
                            } else {
                                System.out.println("Student " + tokens[0] + " does not enroll in any courses in semester " + tokens[1]);
                                Thread.sleep(2000);
                                break;
                            }
                        } catch (NoSuchElementException e){
                            break;
                        } catch (IndexOutOfBoundsException e){
                            System.out.println("Invalid command.");
                            break;
                        }
                        waitScreen();
                        break;
                    }
                    case 4: {
                        String command;
                        System.out.print("Please enter courseID and semester, sepearted by space(example: cosc123 2021A): ");
                        try {
                            command = in.nextLine();
                            String[] tokens = command.split(" ");
                            if (checkCourseEnrollment(tokens[0],tokens[1])){
                                getAllStudent(tokens[0], tokens[1]);
                            } else {
                                System.out.println("Course " + tokens[0] + " does not have any student in semester " + tokens[1]);
                                Thread.sleep(2000);
                                break;
                            }
                        } catch (NoSuchElementException e){
                            break;
                        } catch (IndexOutOfBoundsException e){
                            System.out.println("Invalid command.");
                            break;
                        }
                        waitScreen();
                        break;
                    }
                    case 5: {
                        String command;
                        System.out.print("Please enter the semester: ");
                        boolean found = false;
                        try {
                            command = in.nextLine();
                            for (StudentEnrolment enrollment : enrolments){
                                if ( enrollment.getSemester().equals(command)){
                                    found = true;
                                }
                            }
                            if (!found) {
                                System.out.println("No course offered in semester" + command);
                            } else {
                                getOne(command);
                            }
                        } catch (NoSuchElementException e){
                            System.out.println("Invalid input");
                            Thread.sleep(1000);
                            break;
                        }
                        break;
                    }
                    case 6:
                        getAll();
                        break;
                    case 7:
                        correct = true;
                        break;
                    default:
                        System.out.println("Invalid input");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid integer input ( must be between 1 and 7)");
                in.next();
                Thread.sleep(1000);
                continue;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Main sys = new Main();
        sys.loadSampleData();
        sys.Interface();
        sys.updateEnrollment(sys.enrollmentData);
    }
    public void loadSampleData(){
        BufferedReader br = null;
        BufferedReader br2 = null;
        BufferedReader br3 = null;
        try {
            String line, line1, line2;
            br = new BufferedReader(new FileReader(enrollmentData));
            br2 = new BufferedReader(new FileReader(studentData));
            br3 = new BufferedReader(new FileReader(courseData));
            while ((line = br.readLine()) != null) {
                if (line.equals(ENROLLMENT_FILE_HEADER)){
                    continue;
                } else {
                    String[] tokens = line.split(COMMA_DELIMITER);
                    enrolments.add(new StudentEnrolment(tokens[0],tokens[3],tokens[6]));
                }
            }
            while ((line1 = br2.readLine()) != null) {
                if (line1.equals(STUDENT_FILE_HEADER)){
                    continue;
                } else {
                    String[] tokens1 = line1.split(COMMA_DELIMITER);
                    studentsList.add(new Student(tokens1[0], tokens1[1], tokens1[2]));
                }
            }
            while ((line2 = br3.readLine()) != null) {
                if (line2.equals(COURSE_FILE_HEADER)){
                    continue;
                } else {
                    String[] tokens2 = line2.split(COMMA_DELIMITER);
                    coursesList.add(new Course(tokens2[0], tokens2[1], Integer.parseInt(tokens2[2])));
                }
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }
    public void updateEnrollment(String fileName) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.append(ENROLLMENT_FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (StudentEnrolment enrollment : enrolments){
                fileWriter.append(enrollment.getStudentId());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(getStudent(enrollment.getStudentId()).getName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(getStudent(enrollment.getStudentId()).getBirthday());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(enrollment.getCourseId());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(getCourse(enrollment.getCourseId()).getName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(getCourse(enrollment.getCourseId()).getCredit()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(enrollment.getSemester());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            System.out.println("Enrollment file updated");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(String studentID, String semester) throws InterruptedException {
        Scanner in;
        String command;
        System.out.print("Please enter a command in format [add/drop] [courseID](example: drop cosc123)  : ");
        in = new Scanner(System.in);
        try {
            command = in.nextLine();
        } catch (NoSuchElementException e) {
            return;
        }
        try {
            String[] tokens = command.split(" ");
            if (checkCourseId(tokens[1])) {
                if (tokens[0].equals("add")) {
                    add(studentID, semester, tokens[1]);
                } else if (tokens[0].equals("drop")) {
                    delete(studentID, tokens[1], semester);
                }
            } else {
                System.out.println("Invalid course id.");
                Thread.sleep(1000);
                return;
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Invalid command");
            return;
        }
    }

    @Override
    public void delete(String studentId, String courseId, String semester) throws InterruptedException {
        boolean found = false;
        for (StudentEnrolment enrollment : enrolments) {
            if (enrollment.getStudentId().equals(studentId) && enrollment.getCourseId().equals(courseId) && enrollment.getSemester().equals(semester)) {
                found = true;
            }
        }
        if (found == false) {
            System.out.println("Student " + studentId + " does not enroll in course " + courseId);
            return;
        }
        for (int i = 0; i < enrolments.size(); i++) {
            StudentEnrolment enrolment1 = enrolments.get(i);
            if (enrolment1.getCourseId().equals(courseId) && enrolment1.getStudentId().equals(studentId)) {
                enrolments.remove(enrolment1);
            }
        }
        System.out.println("Drop course successful");
        Thread.sleep(1000);
    }

    @Override
    public void getAllCourse(String studentId, String semester) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter("PrintCourse.cvs", true));
            for ( StudentEnrolment enrollment : enrolments){
                if ( enrollment.getStudentId().equals(studentId) && enrollment.getSemester().equals(semester)){
                    System.out.println(getCourse(enrollment.getCourseId()).toString());
                    output.append(timestamp.toString());
                    output.append(COMMA_DELIMITER);
                    output.append(getCourse(enrollment.getCourseId()).toString());
                    output.append(NEW_LINE_SEPARATOR);
                }
            }
            output.close();
        } catch (IOException E){
            System.out.println("File error.");
            return;
        }
    }

    @Override
    public void getAllStudent(String courseID, String semester) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter("PrintStudent.cvs", true));
            for (StudentEnrolment enrolment : enrolments) {
                if (enrolment.getCourseId().equals(courseID) && enrolment.getSemester().equals(semester)) {
                    System.out.println(getStudent(enrolment.getStudentId()).toString());
                    output.append(timestamp.toString());
                    output.append(COMMA_DELIMITER);
                    output.append(getStudent(enrolment.getStudentId()).toString());
                    output.append(NEW_LINE_SEPARATOR);
                }
            }
            output.close();
        } catch (IOException E){
            System.out.println("File error.");
            return;
        }
    }

    @Override
    public void getOne(String semester){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        BufferedWriter output = null;
        ArrayList<String> courseIdList = new ArrayList<>();
        try {
            output = new BufferedWriter(new FileWriter("PrintCourseOffered.cvs", true));
            for ( StudentEnrolment enrollment : enrolments){
                if ( enrollment.getSemester().equals(semester)){
                    if (courseIdList.contains(enrollment.getCourseId())){
                        continue;
                    } else {
                        courseIdList.add(enrollment.getCourseId());
                        System.out.println(getCourse(enrollment.getCourseId()).toString());
                        output.append(timestamp.toString());
                        output.append(COMMA_DELIMITER);
                        output.append(getCourse(enrollment.getCourseId()).toString());
                        output.append(NEW_LINE_SEPARATOR);
                    }
                }
            }
            output.close();
        } catch (IOException E){
            System.out.println("File error.");
            return;
        }
        waitScreen();
    }

    @Override
    public void getAll() {
        for (Student student: studentsList){
            System.out.println(student.toString());
        }
        for (Course course : coursesList){
            System.out.println(course.toString());
        }
        waitScreen();
    }
}

