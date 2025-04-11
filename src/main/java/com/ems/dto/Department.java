package com.ems.dto;


public enum Department {
    INFORMATION_TECHNOLOGY(1,"Information Technology"),
    MARKETING(2,"Marketing"),
    FINANCE(3,"Finance"),
    HR(4,"Hr");

    private final int id;
    private final String departmentName;

    Department(int id,String departmentName){
        this.id = id;
        this.departmentName = departmentName;
    }

    public int getId(){
        return id;
    }

    public String getDepartmentName(){
        return departmentName;
    }

    public static Department getById(int id){
        for(Department d: values()){
            if(d.id == id){
                return d;
            }
        }

        throw new IllegalArgumentException("no department id: " + id);
    }

    public static Department getByName(String departmentName){
        for(Department d: values()){
            if(d.departmentName.equalsIgnoreCase(departmentName) || d.name().equalsIgnoreCase(departmentName)){
                return d;
            }
        }

        throw new IllegalArgumentException("no department name: " + departmentName);
    }
}
