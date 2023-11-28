package com.shadyplace.springweb.forms;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class AccountForm {
    @Length(max = 50, message = "The user's first name must be no more than 50 characters long.")
    @NotBlank(message = "User's firstname cannot be blank.")
    private String firstname;
    @Length(max = 50, message = "The user's last name must be no more than 50 characters long.")
    @NotBlank(message = "User's lastname cannot be blank.")
    private String lastname;
    @NotBlank(message = "User's email cannot be blank.")
    @Email(message = "User's email format is invalid.")
    private String email;
    @NotNull(message = "User's country cannot be null.")
    private String country;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
