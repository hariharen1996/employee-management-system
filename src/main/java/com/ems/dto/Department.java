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

    public static Department getEnum(String departmentName){
        for(Department department: Department.values()){
            if(department.getDepartmentName().equalsIgnoreCase(departmentName)){
                return department;
            }
        }
        throw new IllegalArgumentException("no enum constant for department name: " + departmentName);
    }
}
