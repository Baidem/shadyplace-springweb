package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.ParasolForm;
import com.shadyplace.springweb.models.*;
import com.shadyplace.springweb.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
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

        // La liste d'emplacement réccupérée depuis mon service je l'ajoute à l'objet commande de mon formulaire
        bookingForm.setParasols(parasolFormList);

        // Revalidate bookingForm
        DataBinder binder = new DataBinder(bookingForm);
        binder.setValidator(validator);
        binder.validate(bookingForm, "bookingForm");
        bindingResult = binder.getBindingResult();
        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

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

            return "redirect:/";
        }

    }

}
