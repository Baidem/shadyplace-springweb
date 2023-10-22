package com.shadyplace.springweb.forms;

import com.shadyplace.springweb.models.bookingResa.Equipment;
import com.shadyplace.springweb.models.bookingResa.Line;

public class ParasolForm {

    private Line line;

    private Equipment equipment;

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
