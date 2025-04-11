package com.ems.exceptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ems.dto.Department;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.web.bind.MethodArgumentNotValidException;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmployeeCustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmployeeCustomException(EmployeeCustomException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String names = ((FieldError) error).getField();
            String errorMsg = error.getDefaultMessage();
            errors.put(names, errorMsg);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("validation failed",errors));
    }    

    @ExceptionHandler(InvalidFormatException.class) 
    public ResponseEntity<ApiResponse<Void>> handleInvalidFormatException(InvalidFormatException exception){
        if(exception.getTargetType() == Department.class){
            String validValues = Arrays.stream(Department.values()).map(Enum::name).collect(Collectors.joining(","));
            String errorMsg = "invalid department value. only accepted values are: " + validValues;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(errorMsg));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("invalid input format: " + exception.getOriginalMessage()));
       
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An error occurred. Please check the data given: " + exception.getMessage()));
    }

}
