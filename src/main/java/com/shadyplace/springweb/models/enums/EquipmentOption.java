package com.shadyplace.springweb.models.enums;

public enum EquipmentOption {
    A_BED("1 bed", "B"),
    TWO_BEDS("2 beds", "BB"),
    A_SEAT("1 seat", "S"),
    A_SEAT_A_BED("1 bed + 1 seat", "BS"),
    TWO_SEATS("2 seats", "SS"),
    NOTHING("nothing", "NO");

    private String label;
    private String abbreviation;

    EquipmentOption(String label, String abbreviation) {
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
