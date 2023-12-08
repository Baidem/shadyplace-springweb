package com.shadyplace.springweb.services.bookingResa;

import com.shadyplace.springweb.forms.SearchCommandForm;
import com.shadyplace.springweb.models.bookingResa.Booking;
import com.shadyplace.springweb.models.bookingResa.Command;
import com.shadyplace.springweb.models.enums.CommandValidationStatus;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.models.enums.CommandPaymentStatus;
import com.shadyplace.springweb.repository.bookingResa.CommandCriteriaRepository;
import com.shadyplace.springweb.repository.bookingResa.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CommandService {
    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    CommandCriteriaRepository commandCriteriaRepository;

    public Optional<Command> findById(Long id) {
        return commandRepository.findById(id);
    }
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
            command.setPaymentStatus(CommandPaymentStatus.CART);
            command.setValidationStatus(CommandValidationStatus.PENDING);

            for (Booking booking : command.getBookings()) {
                booking.setCommand(command);
            }
            commandRepository.save(command);

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

    public Page<Command> getCommandPageByUserAndSearchForm(User user, SearchCommandForm searchCommandForm, int nbResult, int page){
        Pageable pageable = PageRequest.of(page, nbResult);
        Page<Command> commandPaginated = this.commandCriteriaRepository.getCommandPageByUserAndSearchForm(user, searchCommandForm, pageable);
        return commandPaginated;
    }

    public Page<Command> getCommandPageBySearchForm(SearchCommandForm searchCommandForm, int nbResult, int page){
        Pageable pageable = PageRequest.of(page, nbResult);
        Page<Command> commandPaginated = this.commandCriteriaRepository.getCommandPageBySearchForm(searchCommandForm, pageable);
        return commandPaginated;
    }
}
