package com.shadyplace.springweb.services.bookingResa;


import com.shadyplace.springweb.models.bookingResa.Booking;
import com.shadyplace.springweb.models.bookingResa.Line;
import com.shadyplace.springweb.models.bookingResa.Location;
import com.shadyplace.springweb.models.bookingResa.Parasol;
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

    private final int LINE_SIZE = 36;
    private final int RANK_SIZE = 8;

    public Location finByLineAndRank(int lineNumber, int rankNumber) {
        return locationRepository.findLocationByLineNumberAndRankNumber(lineNumber, rankNumber);
    }
    public void save(Location location){
        locationRepository.save(location);
    }

    public List<Location> getAll(){
        return locationRepository.findAll();
    }

    public List<Location> getSortLocationList() {
        List<Location> list = locationRepository.findAll();

        Comparator<Location> locationComparator = Comparator
                .comparingInt(Location::getLineNumber)
                .thenComparingInt(Location::getRankNumber);

        list.sort(locationComparator);

        return list;
    }

    public Parasol[][] beachBuilder(Booking booking) {
        List<Location> locations = this.getSortLocationList();
        List<Booking> bookingsAtThisDate = bookingRepository.getAllByBookingDate(booking.getBookingDate());

        Parasol[][] parasols = new Parasol[8][36];

        for (Location loc : locations) {
            Parasol p = new Parasol();
            p.setLineNumber(loc.getLineNumber());
            p.setRankNumber(loc.getRankNumber());

            if (booking.getLocation() != null &&
                    loc.getLineNumber() == booking.getLocation().getLineNumber() &&
                    loc.getRankNumber() == booking.getLocation().getRankNumber()) {
                p.setSelected(true);
            }

            for (Booking b : bookingsAtThisDate) {
                if (b.getLocation() != null &&
                        loc.getLineNumber() == b.getLocation().getLineNumber() &&
                        loc.getRankNumber() == b.getLocation().getRankNumber()) {
                    p.setBookingId(b.getId());
                }
            }

            parasols[p.getLineNumber() - 1][p.getRankNumber() - 1] = p;
        }

        return parasols;
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