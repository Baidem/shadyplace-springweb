package com.shadyplace.springweb.models;

import com.shadyplace.springweb.models.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "commands")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "total_price",columnDefinition = "float(10,2)")
    @PositiveOrZero(message = "Command's totalPrice cannot be negative.")
    private double totalPrice;
    @Column(columnDefinition = "TEXT")
    private String bill;
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Command's status cannot be null.")
    private OrderStatus status;
    @Column(name = "created_at",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent(message = "Command's createdAt cannot be in the future.")
    @NotNull(message = "")
    private Calendar createdAt;
    @Column(name = "updated_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent(message = "Command's updatedAt cannot be in the future.")
    private Calendar updatedAt;
    @ManyToOne(optional = false)
    @NotNull(message = "Command's user cannot be null.")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "command", cascade = CascadeType.ALL)
    private List<Booking> bookings;
    public Command(){
        this.bookings = new ArrayList<>();
        this.createdAt = Calendar.getInstance();
        this.status = OrderStatus.AWAITING_PAYMENT;
    }
    public void addBooking(Booking booking){
        this.bookings.add(booking);
    }
    public void removeBooking(Booking booking){
        this.bookings.remove(booking);
    }
    public List<Booking> getBookings(){
        return this.bookings;
    }

    public long getId() {
        return id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User customer) {
        this.user = customer;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }


}
