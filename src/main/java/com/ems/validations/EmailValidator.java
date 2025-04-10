package com.ems.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidateEmail,String> {
    private String[] domains;

    public void initialize(ValidateEmail validateEmail){
        this.domains = validateEmail.domainsAllowed();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email == null){
            return true;
        }

        for(String domain: domains){
            if(email.endsWith("@" + domain)){
                return true;
            }
        }
        return false;
    }
    
}
