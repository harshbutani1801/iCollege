package com.example.icollege;

public class StudentAttendanceOverallModel {
    String FullName, Department, Branch, MLPercentage, DNetPercentage;

    StudentAttendanceOverallModel(){

    }

    public StudentAttendanceOverallModel(String fullName, String department, String branch, String MLPercentage, String dNetPercentage) {
        FullName = fullName;
        Department = department;
        Branch = branch;
        this.MLPercentage = MLPercentage;
        DNetPercentage = dNetPercentage;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
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

    public String getMLPercentage() {
        return MLPercentage;
    }

    public void setMLPercentage(String MLPercentage) {
        this.MLPercentage = MLPercentage;
    }

    public String getDNetPercentage() {
        return DNetPercentage;
    }

    public void setDNetPercentage(String DNetPercentage) {
        this.DNetPercentage = DNetPercentage;
    }
}
