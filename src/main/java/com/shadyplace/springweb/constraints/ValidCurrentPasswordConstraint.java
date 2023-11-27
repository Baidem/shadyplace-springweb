package com.shadyplace.springweb.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidCurrentPasswordConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrentPasswordConstraint {

    String message() default "Current password is incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; // request content

    String fieldCurrentPassword();
}
