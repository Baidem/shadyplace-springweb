package com.shadyplace.springweb.services.bookingResa;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.ParasolForm;
import com.shadyplace.springweb.models.bookingResa.Booking;
import com.shadyplace.springweb.models.bookingResa.Line;
import com.shadyplace.springweb.models.enums.CommandValidationStatus;
import com.shadyplace.springweb.models.userAuth.FamilyLink;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.repository.bookingResa.BookingRepository;
import com.shadyplace.springweb.repository.bookingResa.LineRepository;
import com.shadyplace.springweb.repository.userAuth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    LineService lineService;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    private LineRepository lineRepository;

    public List<Booking> BookingFormToBookingList(BookingForm bookingForm, User user) {
        List<Calendar> datesInRange = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>();

        Calendar currentDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        currentDate.setTime(bookingForm.getDateStart());
        endDate.setTime(bookingForm.getDateEnd());

        // List all dates in the range
        while (!currentDate.after(endDate)) {
            datesInRange.add((Calendar) currentDate.clone());
            currentDate.add(Calendar.DATE, 1);
        }

        // For each date in the list, a reservation is created and added to the result.
        for (Calendar calendar : datesInRange){
            for (ParasolForm parasolForm : bookingForm.getParasols()) {
                Booking booking = new Booking();

                booking.setBookingDate(calendar);
                booking.setEquipment(parasolForm.getEquipment());
                booking.setFidelityRank(user.getCurrentFidelityRank());
                booking.setLine(parasolForm.getLine());
                Double price = computePrice(user.getFamilyLink(), booking);
                booking.setBookingPrice(price);

                bookings.add(booking);
            }
        }

        return bookings;
    }

    private double computePrice(FamilyLink familyLink, Booking booking){

        double linePrice = booking.getLine().getPrice();
        double equipmentPrice = booking.getEquipment().getPrice();
        double fidelityRankDiscountPrice = booking.getFidelityRank().getDiscountPrice();
        double familyLinkRate = familyLink.getDiscountRate();

        double price = (linePrice + equipmentPrice - fidelityRankDiscountPrice) * (1-familyLinkRate);

        // Price rounded to two decimal places
        double factor = Math.pow(10, 2);
        price = Math.round(price * factor) / factor;

        return price;
    }

    public List<Booking> getByCommandId(Long id){
        return this.bookingRepository.getByCommandId(id);
    }

    public Map<String, Integer> getAvailablePlaceCounts(Calendar dateOfStart, Calendar dateOfEnd) {

        // For each "line" we associate the maximum number of places //
        Map<String, Integer> availablePlaceMap = new HashMap<String, Integer>();
        List<Line> lines = lineRepository.findAll();
        for (Line line : lines) {
            availablePlaceMap.put(line.getLabel(), line.getMaxPlace());
        }

        // List of dates in the order range //
        List<Calendar> datesInRange = new ArrayList<>();

        Calendar currentDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        currentDate.setTime(dateOfStart.getTime());
        endDate.setTime(dateOfEnd.getTime());

        while (!currentDate.after(endDate)) {
            datesInRange.add((Calendar) currentDate.clone());
            currentDate.add(Calendar.DATE, 1);
        }

        // For each date, get bookings //
        List<Map<String, Integer>> listOfMap = new ArrayList<Map<String, Integer>>();
        for (Calendar date : datesInRange){
            var map = new HashMap<String, Integer>();
            map.putAll(availablePlaceMap);
            List<Booking> bookings = bookingRepository.getAllByBookingDate(date);
            for (Booking booking : bookings) {
                map.put(booking.getLine().getLabel(), map.get(booking.getLine().getLabel()) - 1);
            }
            listOfMap.add(map);
        }
        for (Map<String, Integer> map : listOfMap) {
            for (Map.Entry<String, Integer> mapentry : map.entrySet()) {
                if (mapentry.getValue() < availablePlaceMap.getOrDefault(mapentry.getKey(), Integer.MAX_VALUE)) {
                    availablePlaceMap.put(mapentry.getKey(), mapentry.getValue());
                }
            }
        }
        return availablePlaceMap;
    }
}

