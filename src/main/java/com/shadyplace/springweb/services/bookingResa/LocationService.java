package com.shadyplace.springweb.services.bookingResa;


import com.shadyplace.springweb.models.bookingResa.Booking;
import com.shadyplace.springweb.models.bookingResa.Line;
import com.shadyplace.springweb.models.bookingResa.Location;
import com.shadyplace.springweb.repository.bookingResa.BookingRepository;
import com.shadyplace.springweb.repository.bookingResa.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Location finByLineAndRank(int lineNumber, int rankNumber) {
        return locationRepository.findLocationByLineNumberAndRankNumber(lineNumber, rankNumber);
    }
    public void save(Location location){
        locationRepository.save(location);
    }

    public List<Location> getAll(){
        return locationRepository.findAll();
    }

    public Map<String, String> getPlanningMap(Calendar bookingDate) {
        Map<String, String> map = new HashMap<String, String>();
        List<Location> locations = locationRepository.findAll();
        List<Location> occupiedLocations = locationRepository.getAllLocationByBookingDate(bookingDate);

        for (Location location : locations) {
            String key = location.getLineNumber()+","+ location.getRankNumber();
            boolean isOccupied = occupiedLocations.stream()
                    .anyMatch(occLocation -> occLocation.getLineNumber() == location.getLineNumber()
                            && occLocation.getRankNumber() == location.getRankNumber());

            map.put(key, isOccupied ? "Occupied" : "Available");
        }
        return map;
    }

    public String[][] beach2D() {
        String[][] array2D = new String[8][36];
        for (int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < array2D[i].length; j++) {
                array2D[i][j] = (i + 1) + "," + (j + 1);
            }
        }
        return array2D;
    }

    public boolean autoLocationBooking(Booking booking) {
        Line line = booking.getLine();
        Calendar date = booking.getBookingDate();
        List<Location> allLocationInLine = this.getAllLocationInline(line);
        List<Location> reservedLocationInLine = this.getReservedLocationInline(date, line);

        for (Location loc : allLocationInLine) {
            boolean locationNotReserved = true;
            for (Location reservedLoc : reservedLocationInLine) {
                if (loc.getId() == reservedLoc.getId()) {
                    locationNotReserved = false;
                    break;
                }
            }
            if (locationNotReserved) {
                booking.setLocation(loc);
                bookingRepository.save(booking);
                return true;
            }
        }
        return false;
    }

    private List<Location> getAllLocationInline(Line line){
        List<Location> locations = new ArrayList<Location>();
        List<Integer> numberLineList = new ArrayList<Integer>();
        for (String st : Arrays.stream(line.getLineNumberList().split(",")).toList()) {
            Integer nb = Integer.parseInt(st);
            locations.addAll(locationRepository.getLocationsByLineNumber(nb));
        }

        return locations;
    }
    private List<Location> getReservedLocationInline(Calendar bookingDate, Line line){
        List<Location> locations = new ArrayList<Location>();
        List<Integer> numberLineList = new ArrayList<Integer>();
        for (String st : Arrays.stream(line.getLineNumberList().split(",")).toList()) {
            Integer nb = Integer.parseInt(st);
            locations.addAll(locationRepository.getLocationsByBookingDateAndLineNumber(bookingDate, nb));
        }

        return locations;
    }

}

