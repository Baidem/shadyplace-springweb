package com.shadyplace.springweb.forms;

import com.shadyplace.springweb.models.Equipment;
import com.shadyplace.springweb.models.Line;

public class EquipmentAndLineForm {

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
