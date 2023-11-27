package com.shadyplace.springweb.constraints;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.PasswordForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SamePasswordConfirmConstraintValidator implements ConstraintValidator<SamePasswordConfirmConstraint, Object> {

    @Override
    public void initialize(SamePasswordConfirmConstraint constraint)  {
        ConstraintValidator.super.initialize(constraint);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        PasswordForm passwordForm = (PasswordForm) o;

        if (passwordForm.getNewPassword() != null && passwordForm.getConfirmPassword() != null){
            return passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword());
        } else {
            return false;
        }
    }
}
