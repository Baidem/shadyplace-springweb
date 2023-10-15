package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.ParasolForm;
import com.shadyplace.springweb.models.*;
import com.shadyplace.springweb.models.enums.CommandStatus;
import com.shadyplace.springweb.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;
    @Autowired
    ParasolFormService parasolFormService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    LineService lineService;
    @Autowired
    Validator validator;
    @Autowired
    private CommandService commandService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView addBooking(){

        List<Equipment> equipmentList = this.equipmentService.findAll();
        List<Line> lineList = this.lineService.findAll();

        ModelAndView mv = new ModelAndView("booking/form");

        BookingForm bookingForm = new BookingForm();
        mv.addObject("bookingForm", bookingForm);
        mv.addObject("equipmentList", equipmentList);
        mv.addObject("lineList", lineList);

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addBookingSubmit(@Valid BookingForm bookingForm,
                                 BindingResult bindingResult,
                                 @RequestBody String postPayload, Model model){

        // Selects options
        List<Equipment> equipmentList = this.equipmentService.findAll();
        List<Line> lineList = this.lineService.findAll();

        // Convert postPayload to parasolFormList and set to bookingform
        List<ParasolForm> parasolFormList =
                this.parasolFormService.convertPayloadToParasolFormList(postPayload);
        bookingForm.setParasols(parasolFormList);

        // Revalidate bookingForm
        DataBinder binder = new DataBinder(bookingForm);
        binder.setValidator(validator);
        binder.validate(bookingForm, "bookingForm");
        bindingResult = binder.getBindingResult();
        List<ObjectError> globalErrors = bindingResult.getGlobalErrors(); // catch DateOrderConstraint error

        // hasErrors
        if (bindingResult.hasErrors()) {

            model.addAttribute("fields", bindingResult);
            model.addAttribute("bookingForm", bookingForm);

            // select
            model.addAttribute("equipmentList", equipmentList);
            model.addAttribute("lineList", lineList);
            model.addAttribute("globalErrors", globalErrors);

            return "booking/form";
        } else { // Saving the Command with its Bookings
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(authentication.getName()) ;

            List<Booking> bookings =
                    this.bookingService.BookingFormToBookingList(bookingForm, user);

            Command command = new Command(bookings, user);
            command.setComment(bookingForm.getComment());

            this.commandService.saveWithBookingList(command);

            model.addAttribute("fields", bindingResult);
            model.addAttribute("user", user);
            model.addAttribute("command", command);

            return "booking/confirm";
        }

    }

    @RequestMapping(value = "/cancel/{command}", method = RequestMethod.GET)
    public String commandCancel(@PathVariable Command command) {
        if (shouldCancelCommand(command)) {
            commandService.delete(command);
        }
        return "redirect:/booking/new";
    }

    private boolean shouldCancelCommand(Command command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if (command.getId() == 0 || user.getId() != command.getUser().getId() || command.getStatus() != CommandStatus.CART) {
            return false;
        }

        return true;
    }


}
