package com.ems.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SalaryValidator implements ConstraintValidator<ValidateSalary,Double> {
    public double minSalary;

    public void initialize(ValidateSalary validSalary){
        this.minSalary = validSalary.min();
    }

    @Override
    public boolean isValid(Double salary, ConstraintValidatorContext context) {
        if(salary == null){
            return false;
        }
        return salary >= minSalary;
    }
    
}
