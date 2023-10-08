package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Booking;
import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.repository.BookingRepository;
import com.shadyplace.springweb.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public List<Command> getCommandByUser(User user){
        return this.commandRepository.getCommandByUser(user);
    }

    public void saveWithBookingList(Command command) {
        try {
            command.setTotalPrice(computeTotalPrice(command.getBookings()));
                //bill

            for (Booking booking : command.getBookings()) {
                booking.setCommand(command);
            }
            commandRepository.save(command);
                System.out.println("hello");

        } catch (Exception e){
            throw new RuntimeException("Command save error : "+e.getMessage());
        }
    }

    private double computeTotalPrice(List<Booking> bookings){
        double sum = 0;
        for (Booking booking : bookings){
            sum += booking.getBookingPrice();
        }

        double factor = Math.pow(10, 2); // two digits after the decimal point
        sum = Math.round(sum * factor) / factor;

        return sum;
    }


//    // TODO go to CommandService obsolete?
//    public void saveCommandWithItsBookings(BookingForm bookingForm, String email){
//        Command command = new Command();
//        command.setUser(this.userRepository.findByEmail(email));
//        command.setComment(bookingForm.getComment());
//
//        this.commandRepository.save(command);
//        var counter = 0;
//
//        for (ParasolForm parasolForm : bookingForm.getParasols()) {
//
//            Booking booking = new Booking();
//            booking.setBookingDate(new GregorianCalendar());
//            booking.setLine(parasolForm.getLine());
//            booking.setEquipment(parasolForm.getEquipment());
//            booking.setCommand(command);
//            booking.setBookingStatus(BookingStatus.PENDING);
//            booking.setBookingPrice(10); // TODO price method
//            booking.setComment(command.getComment()); // TODO enlever doublon du comment
//            booking.setFidelityRank(userRepository.findByEmail(email).getCurrentFidelityRank());
//
//            this.bookingRepository.save(booking);
//            counter++;
//        }
//        System.out.println(counter);
//    }

}
