package com.example.icollege;

public class StudentAttendanceModifyModel {
    String ID, FullName, EnrollmentNumber, Attendance;

    StudentAttendanceModifyModel(){

    }

    public StudentAttendanceModifyModel(String ID, String fullName, String enrollmentNumber, String attendance) {
        this.ID = ID;
        FullName = fullName;
        EnrollmentNumber = enrollmentNumber;
        Attendance = attendance;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEnrollmentNumber() {
        return EnrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        EnrollmentNumber = enrollmentNumber;
    }

    public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String attendance) {
        Attendance = attendance;
    }
}
