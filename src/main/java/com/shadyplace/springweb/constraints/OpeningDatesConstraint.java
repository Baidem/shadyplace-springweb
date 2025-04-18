package com.shadyplace.springweb.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OpeningDatesConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpeningDatesConstraint {
    String message() default "We are closed on this date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
