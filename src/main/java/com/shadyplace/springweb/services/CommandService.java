package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Booking;
import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.models.enums.CommandStatus;
import com.shadyplace.springweb.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    public List<Command> getCommandByUser(User user){
        return this.commandRepository.getCommandByUser(user);
    }

    public void save(Command command) {
        this.commandRepository.save(command);
    }
    public void saveWithBookingList(Command command) {
        try {
            command.setTotalPrice(computeTotalPrice(command.getBookings()));
            command.setCreatedAt(Calendar.getInstance());
            command.setStatus(CommandStatus.CART);
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
        double totalPrice = 0;
        for (Booking booking : bookings){
            totalPrice += booking.getBookingPrice();
        }

        double factor = Math.pow(10, 2); // two digits after the decimal point
        totalPrice = Math.round(totalPrice * factor) / factor;

        return totalPrice;
    }

    public void delete(Command command){
        this.commandRepository.delete(command);
    }


}
