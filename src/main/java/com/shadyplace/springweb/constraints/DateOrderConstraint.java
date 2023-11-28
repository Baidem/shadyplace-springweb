package com.shadyplace.springweb.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateOrderConstraintValidator.class)
@Target({ElementType.TYPE}) // two fields!
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOrderConstraint {
    String message() default "Start date must be before end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; // request content
    String fieldStart();
    String fieldEnd();
}
