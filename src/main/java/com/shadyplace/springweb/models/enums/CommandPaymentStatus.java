package com.shadyplace.springweb.models.enums;

public enum CommandPaymentStatus {
    CART("cart"),
    ABANDONED("abandoned"),
    AWAITING_PAYMENT("awaiting payment"),
    PAYMENT_MADE("payment made"),
    PAYMENT_SUCCESS("payment success"),
    PAYMENT_ERROR("payment error"),
    PAYMENT_REFUNDED("payment refunded"),
    PAYMENT_REFUSED("payment refused");

    private String label;

    CommandPaymentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
