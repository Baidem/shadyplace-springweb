package com.shadyplace.springweb.forms;

import com.shadyplace.springweb.models.userAuth.FamilyLink;
import com.shadyplace.springweb.models.userAuth.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserForm {
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
    @NotNull(message = "User's family link cannot be null.")
    private FamilyLink familyLink;
    @NotNull(message = "User's role")
    private Role role;

    public FamilyLink getFamilyLink() {
        return familyLink;
    }

    public void setFamilyLink(FamilyLink familyLink) {
        this.familyLink = familyLink;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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
