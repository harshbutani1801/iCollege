package com.example.icollege;

public class View_Faculty_Model {
    String UserID, FullName, Department, Designation;

    View_Faculty_Model(){

    }

    public View_Faculty_Model(String userID, String fullName, String department, String designation) {
        UserID = userID;
        FullName = fullName;
        Department = department;
        Designation = designation;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
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

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}
