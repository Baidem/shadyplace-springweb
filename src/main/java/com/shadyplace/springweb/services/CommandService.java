package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Booking;
import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.repository.BookingRepository;
import com.shadyplace.springweb.repository.CommandRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public void createWithBookingList(Command command) {
        try {
            commandRepository.save(command);

            for (Booking booking : command.getBookings()) {
                bookingRepository.save(booking);
            }

        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
