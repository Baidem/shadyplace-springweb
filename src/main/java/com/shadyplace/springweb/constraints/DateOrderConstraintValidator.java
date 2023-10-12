package com.shadyplace.springweb.constraints;

import com.shadyplace.springweb.forms.BookingForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOrderConstraintValidator implements ConstraintValidator<DateOrderConstraint, Object> {

    @Override
    public void initialize(DateOrderConstraint constraint)  {
        ConstraintValidator.super.initialize(constraint);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        BookingForm bookingForm = (BookingForm) o;
        boolean isValid = false;

        if (bookingForm.getDateStart() != null && bookingForm.getDateEnd() != null){
            boolean b = bookingForm.getDateStart().getTime() <= bookingForm.getDateEnd().getTime();
            System.out.println(b);
            return bookingForm.getDateStart().getTime() <= bookingForm.getDateEnd().getTime();
        } else {
            return false;
        }
    }
}
