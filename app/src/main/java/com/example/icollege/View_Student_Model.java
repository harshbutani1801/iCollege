package com.example.icollege;

public class View_Student_Model {
    String ID, FullName, Branch, Department;

    public View_Student_Model(){

    }

    public View_Student_Model(String ID, String fullName, String branch, String department) {
        this.ID = ID;
        FullName = fullName;
        Branch = branch;
        Department = department;
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
}
