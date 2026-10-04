package com.event.eventregistration;

public class PublicRegistration {

    private String studentName;
    private String rollNo;

    public PublicRegistration(String studentName, String rollNo) {
        this.studentName = studentName;
        this.rollNo = rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRollNo() {
        return rollNo;
    }
}