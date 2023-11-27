package com.shadyplace.springweb.constraints;

import com.shadyplace.springweb.forms.PasswordForm;
import com.shadyplace.springweb.services.userAuth.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class ValidCurrentPasswordConstraintValidator implements ConstraintValidator<ValidCurrentPasswordConstraint, Object> {

    @Autowired
    UserService userService;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void initialize(ValidCurrentPasswordConstraint constraint) {
        ConstraintValidator.super.initialize(constraint);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        PasswordForm passwordForm = (PasswordForm) o;

        if (passwordForm.getCurrentPassword() != null){
            String passwordHash = userService
                    .findByEmail(
                            authentication.getName()
                    ).getPassword();
            return encoder
                    .matches(
                            passwordForm.getCurrentPassword(),
                            passwordHash
                    );
        } else {
            return false;
        }
    }
}
