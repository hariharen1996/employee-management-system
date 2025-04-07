package com.ems.dto;

public enum Department {
    IT("Information Technology"),
    MARKETING("Marketing"),
    FINANCE("Finance"),
    HR("Hr");

    private final String departmentName;

    Department(String departmentName){
        this.departmentName = departmentName;
    }

    public String getDepartmentName(){
        return departmentName;
    }
}
