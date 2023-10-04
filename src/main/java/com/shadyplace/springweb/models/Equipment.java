package com.shadyplace.springweb.models;

import com.shadyplace.springweb.models.enums.EquipmentOption;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "equipement")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Equipement option cannot be null.")
    private EquipmentOption option;
    @Column(columnDefinition = "double(5,2)", nullable = false)
    @NotNull(message = "Equipement price cannot be null.")
    @PositiveOrZero(message = "Equipement price cannot be negative.")
    private double price;

    public Equipment() {
    }

    public Equipment(EquipmentOption option, double price) {
        this.option = option;
        this.price = price;
    }

    public Equipment(long id, EquipmentOption option, double price) {
        this.id = id;
        this.option = option;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOption(EquipmentOption option) {
        this.option = option;
    }

    public EquipmentOption getOption() {
        return option;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
