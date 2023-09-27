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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }



}