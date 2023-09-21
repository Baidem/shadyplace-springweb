package com.shadyplace.springweb.models;

import com.shadyplace.springweb.models.enums.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Calendar;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Customer's email cannot be blank.")
    @Email(message = "Customer's email format is invalid.")
    private String email;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Customer's password cannot be blank.")
    private String password; // TODO REGEX PASSWORD

    @Column(length = 50, nullable = false)
    @Length(max = 50, message = "The user's first name must be no more than 50 characters long.")
    @NotBlank(message = "Customer's firstname cannot be blank.")
    private  String firstname;
    @Column(length = 50, nullable = false)
    @Length(max = 50, message = "The user's last name must be no more than 50 characters long.")
    @NotBlank(message = "Customer's lastname cannot be blank.")
    private String lastname;

    @Transient
    @NotBlank(message = "Customer's password confirm cannot be blank")
    private String confirmPassword;

    @Column(name = "registration_date",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registrationDate;

    @Column(name = "residence_country", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Customer's country cannot be null.")
    private Country residenceCountry;

    @ManyToOne()
    @JoinColumn(name = "family_link_id", referencedColumnName = "id")
    private FamilyLink familyLink;

    @ManyToOne()
    @JoinColumn(name = "current_fidelity_rank_id", referencedColumnName = "id")
    @NotNull(message = "Customer.currentFidelityRank cannot be null.")
    private FidelityRank currentFidelityRank;


    public Customer() {

    }
    public Customer(String lastname, String firstname, String email, String password, String confirmPassword, Country residenceCountry) {
        this();
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.residenceCountry = residenceCountry;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public FamilyLink getFamilyLink() {
        return familyLink;
    }

    public void setFamilyLink(FamilyLink familyLink) {
        this.familyLink = familyLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Calendar registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Country getResidenceCountry() {
        return residenceCountry;
    }

    public void setResidenceCountry(Country residenceCountry) {
        this.residenceCountry = residenceCountry;
    }

    public FidelityRank getCurrentFidelityRank() {
        return currentFidelityRank;
    }

    public void setCurrentFidelityRank(FidelityRank currentFidelityRank) {
        this.currentFidelityRank = currentFidelityRank;
    }
}
