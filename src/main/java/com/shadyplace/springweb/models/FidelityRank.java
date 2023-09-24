package com.shadyplace.springweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


@Entity
@Table(name = "fidelity_ranks")
public class FidelityRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 1, nullable = false)
    @NotBlank(message = "Fidelity rank's label cannot be blank.")
    private String label;
    @Column(columnDefinition = "float(5,2)", name = "discount_price", nullable = false)
    @PositiveOrZero(message = "Fidelity rank discount price cannot be negative")
    @NotNull(message = "Fidelity rank's discount price cannot be null.")
    private double discountPrice;

    public FidelityRank() {
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
