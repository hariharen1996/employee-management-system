package com.ems.dto;

import java.util.Arrays;

public enum Department {
    INFORMATION_TECHNOLOGY("Information Technology"),
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
        return Arrays.stream(values()).filter(d -> d.name().equalsIgnoreCase(departmentName) || 
        d.departmentName.equalsIgnoreCase(departmentName))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("no enum constants for department: " + departmentName));
    }
}
