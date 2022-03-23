package com.example.icollege;

public class StudentAttendanceModel {
    String ID, EnrollmentNumber, FullName, MLA, MLD, DNetA, DNetD, Department, Branch, Semester, ClassRoom, Date, Subject;

    StudentAttendanceModel(){

    }

    public StudentAttendanceModel(String ID, String enrollmentNumber, String fullName, String mla, String mld, String department, String branch, String semester, String classRoom, String date, String subject, String dneta, String dnetd) {
        this.ID = ID;
        EnrollmentNumber = enrollmentNumber;
        FullName = fullName;
        MLA = mla;
        MLD = mld;
        Department = department;
        Branch = branch;
        Semester = semester;
        ClassRoom = classRoom;
        Date = date;
        Subject = subject;
        DNetA = dneta;
        DNetD = dnetd;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEnrollmentNumber() {
        return EnrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        EnrollmentNumber = enrollmentNumber;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMLA() {
        return MLA;
    }

    public void setMLA(String MLA) {
        this.MLA = MLA;
    }

    public String getMLD() {
        return MLD;
    }

    public void setMLD(String MLD) {
        this.MLD = MLD;
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

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getClassRoom() {
        return ClassRoom;
    }

    public void setClassRoom(String classRoom) {
        ClassRoom = classRoom;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDNetA() {
        return DNetA;
    }

    public void setDNetA(String DNetA) {
        this.DNetA = DNetA;
    }

    public String getDNetD() {
        return DNetD;
    }

    public void setDNetD(String DNetD) {
        this.DNetD = DNetD;
    }
}
