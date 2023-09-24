package com.shadyplace.springweb.models.enums;

public enum LineLabel {
    PREMIUM("premium", "P"),
    BACK("back", "B");

    private String label;
    private String abbreviation;

    LineLabel(String label, String abbreviation) {
        this.label = label;
        this.abbreviation = abbreviation;
    }
}