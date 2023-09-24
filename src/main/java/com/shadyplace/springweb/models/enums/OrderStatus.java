package com.shadyplace.springweb.models.enums;

public enum OrderStatus {
    CARD("card", "CA"),
    ABANDONED("abandoned", "AB"),
    AWAITING_PAYMENT("awaiting payment", "AP"),
    PAYMENT_MADE("payment made", "PM"),
    PAYMENT_REFUSED("payment refused", "PR");
    private String label;
    private String abbreviation;

    OrderStatus(String label, String abbreviation) {
        this.label = label;
        this.abbreviation = abbreviation;
    }

    public String getLabel() {
        return label;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
