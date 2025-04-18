package com.shadyplace.springweb.forms;

import com.shadyplace.springweb.constraints.SamePasswordConfirmConstraint;
import com.shadyplace.springweb.constraints.ValidCurrentPasswordConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@SamePasswordConfirmConstraint(fieldNewPassword = "newPassword", fieldConfirmPassword = "confirmPassword")
@ValidCurrentPasswordConstraint(fieldCurrentPassword = "currentPassword")
public class PasswordForm {
    @NotBlank(message = "Current password cannot be blank.")
    private String currentPassword;
    @NotBlank(message = "New password cannot be blank.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "The password must contain at least 8 characters, with at least one capital letter, one number and one special character."
    )
    private String newPassword;
    @NotBlank(message = "Confirm password cannot be blank.")
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
