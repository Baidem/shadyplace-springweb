package com.shadyplace.springweb.models.paypal;

import com.shadyplace.springweb.models.bookingResa.Command;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class CompletedOrder {

    @Id
    private String payId;

    @Basic
    private String status;

    @ManyToOne(optional = false)
    public Command command;

    public CompletedOrder(String status, String payId) {
        this.status = status;
        this.payId = payId;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public CompletedOrder(String status) {
        this.status = status;
    }

    public CompletedOrder() {
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

