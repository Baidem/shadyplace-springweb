package com.shadyplace.springweb.forms;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class LocationForm {

    @Min(value = 1, message = "The first line is line n°1")
    @Max(value = 8, message = "The last line is line n°8")
    private Integer line;

    private String equipment;

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
