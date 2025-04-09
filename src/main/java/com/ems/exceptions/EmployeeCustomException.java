package com.ems.exceptions;

public class EmployeeCustomException extends RuntimeException {
    public EmployeeCustomException(String message){
        super(message);
    }

    public EmployeeCustomException(String message,Throwable error){
        super(message,error);
    }
}
