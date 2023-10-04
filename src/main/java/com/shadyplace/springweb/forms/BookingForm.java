package com.shadyplace.springweb.forms;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class BookingForm {

    @NotBlank(message = "Please enter a start date")
    private GregorianCalendar dateStart;
    @NotBlank(message = "Please enter an end date")
    private GregorianCalendar dateEnd;
    private String Comment;
    private List<PlaceOptionForm> locations;

    public BookingForm() {
        this.locations = new ArrayList<>();
        this.locations.add(new PlaceOptionForm());
    }
    public void addLocation(PlaceOptionForm placeOptionForm){
        this.locations.add(placeOptionForm);
    }
    public void removeLocation(PlaceOptionForm placeOptionForm){
        this.locations.remove(placeOptionForm);
    }

    public GregorianCalendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(GregorianCalendar dateStart) {
        this.dateStart = dateStart;
    }

    public GregorianCalendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(GregorianCalendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public List<PlaceOptionForm> getLocations() {
        return locations;
    }

    public void setLocations(List<PlaceOptionForm> locations) {
        this.locations = locations;
    }
}
