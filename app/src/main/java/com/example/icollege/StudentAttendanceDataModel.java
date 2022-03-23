package com.example.icollege;

public class StudentAttendanceDataModel {
    String FullName, EnrollmentNumber, Attendance, Percentage, Department, Branch, TotalAttendance, TotalDay;

    StudentAttendanceDataModel(){

    }

    public StudentAttendanceDataModel(String fullName, String enrollmentNumber, String attendance, String percentage, String department, String branch, String totalAttendance, String totalDay) {
        FullName = fullName;
        EnrollmentNumber = enrollmentNumber;
        Attendance = attendance;
        Percentage = percentage;
        Department = department;
        Branch = branch;
        TotalAttendance = totalAttendance;
        TotalDay = totalDay;
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

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getTotalAttendance() {
        return TotalAttendance;
    }

    public void setTotalAttendance(String totalAttendance) {
        TotalAttendance = totalAttendance;
    }

    public String getTotalDay() {
        return TotalDay;
    }

    public void setTotalDay(String totalDay) {
        TotalDay = totalDay;
    }
}
