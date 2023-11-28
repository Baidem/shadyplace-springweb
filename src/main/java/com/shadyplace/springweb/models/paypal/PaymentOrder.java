package com.shadyplace.springweb.models.paypal;

import com.shadyplace.springweb.models.bookingResa.Command;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class PaymentOrder implements Serializable {
    @Id
    @Basic
    private String payId;
    @Basic
    private String status;
    @Basic
    private String redirectUrl;

    public PaymentOrder(String status, String payId, String redirectUrl) {
        this.status = status;
        this.payId = payId;
        this.redirectUrl = redirectUrl;
    }

    @ManyToOne(optional = false)
    public Command command;

    public PaymentOrder(String status) {
        this.status = status;
    }

    public PaymentOrder() {
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
