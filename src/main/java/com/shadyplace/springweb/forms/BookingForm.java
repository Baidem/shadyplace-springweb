package com.shadyplace.springweb.forms;

import com.shadyplace.springweb.constraints.DateOrderConstraint;
import com.shadyplace.springweb.constraints.OpeningDatesConstraint;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@DateOrderConstraint(fieldStart = "dateStart", fieldEnd = "dateEnd")
public class BookingForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date cannot be null")
    @Future(message = "This date has already passed")
    @OpeningDatesConstraint()
    private Date dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date cannot be null")
    @Future(message = "This date has already passed")
    @OpeningDatesConstraint()
    private Date dateEnd;
    private String Comment;
    private List<EquipmentAndLineForm> locations;

    public BookingForm() {
        this.locations = new ArrayList<>();
        this.locations.add(new EquipmentAndLineForm());
    }
    public void addLocation(EquipmentAndLineForm equipmentAndLineForm){
        this.locations.add(equipmentAndLineForm);
    }
    public void removeLocation(EquipmentAndLineForm equipmentAndLineForm){
        this.locations.remove(equipmentAndLineForm);
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public List<EquipmentAndLineForm> getLocations() {
        return locations;
    }

    public void setLocations(List<EquipmentAndLineForm> locations) {
        this.locations = locations;
    }
}
