package com.ems.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateEmail {
    String message() default "invalid email domain";
    String[] domainsAllowed() default {"gmail.com"};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; 
}
