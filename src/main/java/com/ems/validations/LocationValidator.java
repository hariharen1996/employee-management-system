package com.ems.validations;

import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocationValidator implements ConstraintValidator<ValidateLocation,String> {
    private static final Set<String> locations = Set.of("madurai","chennai","bengaluru","coimbatore","delhi","mumbai","hyderabad");
        
    @Override
    public boolean isValid(String location, ConstraintValidatorContext context) {
        if(location == null){
            return false;
        } 
        return locations.contains(location);
    }
}
