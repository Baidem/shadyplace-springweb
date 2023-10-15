package com.shadyplace.springweb.services;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.ParasolForm;
import com.shadyplace.springweb.models.*;
import com.shadyplace.springweb.models.enums.BookingStatus;
import com.shadyplace.springweb.repository.*;
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

        // Pour chaque date de la liste une réservation est construite et ajoutée au résultat
        for (Calendar calendar : datesInRange){
            for (ParasolForm parasolForm : bookingForm.getParasols()) {
                Booking booking = new Booking();

                booking.setBookingDate(calendar);
                booking.setBookingStatus(BookingStatus.PENDING);
                booking.setComment(bookingForm.getComment());
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

}

