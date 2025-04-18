package com.shadyplace.springweb.models.enums;

public enum CommandValidationStatus {
    // pending, validated, refused
    PENDING("pending", "P"),
    VALIDATED("validated", "V"),
    REFUSED("refused", "RS");

    private String label;
    private String abbreviation;

    CommandValidationStatus(String label, String abbreviation) {
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
