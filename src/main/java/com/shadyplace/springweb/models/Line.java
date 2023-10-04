package com.shadyplace.springweb.models;

import com.shadyplace.springweb.models.enums.LineLabel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "_line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Line's label cannot be null.")
    private LineLabel label;
    @Column(columnDefinition = "float(5,2)", nullable = false)
    @PositiveOrZero(message = "Line's price cannot be negative.")
    @NotNull(message = "Line's price cannot be null.")
    private double price;

    public Line() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLabel(LineLabel label) {
        this.label = label;
    }

    public LineLabel getLabel() {
        return label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
