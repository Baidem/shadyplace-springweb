package com.shadyplace.springweb.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SamePasswordConfirmConstraintValidator.class)
@Target({ElementType.TYPE}) // two fields!
@Retention(RetentionPolicy.RUNTIME)
public @interface SamePasswordConfirmConstraint {
    String message() default "The new password must be the same as the confirmation password.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; // request content
    String fieldNewPassword();
    String fieldConfirmPassword();
}
