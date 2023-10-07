package com.shadyplace.springweb.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OpeningDatesConstraintValidator implements ConstraintValidator<OpeningDatesConstraint, Object> {

    @Override
    public void initialize(OpeningDatesConstraint constraint) {
        ConstraintValidator.super.initialize(constraint);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Date date = (Date) o;
        boolean isValid = false;

        if (date != null) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);

            int month = calendar.get(Calendar.MONTH);

            if (month == Calendar.JUNE) {
                isValid = true;
            }
            if (month == Calendar.JULY) {
                isValid = true;
            }
            if (month == Calendar.AUGUST) {
                isValid = true;
            }
            if (month == Calendar.SEPTEMBER) {
                isValid = calendar.get(Calendar.DAY_OF_MONTH) <= 15;
            }

        }
            return isValid;
    }
}
