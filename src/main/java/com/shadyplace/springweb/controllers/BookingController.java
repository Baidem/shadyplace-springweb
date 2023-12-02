package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.ParasolForm;
import com.shadyplace.springweb.forms.SearchCommandForm;
import com.shadyplace.springweb.models.bookingResa.Booking;
import com.shadyplace.springweb.models.bookingResa.Command;
import com.shadyplace.springweb.models.bookingResa.Equipment;
import com.shadyplace.springweb.models.bookingResa.Line;
import com.shadyplace.springweb.models.enums.CommandPaymentStatus;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.services.bookingResa.*;
import com.shadyplace.springweb.services.userAuth.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping( "/mybookinglist")
    public ModelAndView getAll(@RequestParam(required = false) String page,
                               @RequestParam(required = false) String searchBar,
                               @RequestParam(required = false) String filterStatus
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if (page == null) {
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("booking/commandList");

        if (searchBar == null) {
            searchBar = "";
        }
        if (filterStatus == null) {
            filterStatus = "";
        }

        Page<Command> commands = this.commandService
                .getCommandPageByUserAndSearchForm(
                        user,
                        new SearchCommandForm(searchBar, filterStatus),
                        4,
                        pageNumber - 1
                );

        mv.addObject("commands", commands);
        mv.addObject("pageNumber", (String) page);
        mv.addObject("form", new SearchCommandForm(searchBar, filterStatus));

        return mv;
    }

    @RequestMapping(value = "/mybookinglist", method = RequestMethod.POST)
    public ModelAndView searchFormSubmit(
            @RequestParam(required = false) String page,
            @Valid SearchCommandForm searchCommandForm,
            BindingResult bindingResult
    ){

        if (searchCommandForm.getSearchContentBar() == null) {
            searchCommandForm.setSearchContentBar("");
        }
        if (searchCommandForm.getFilterStatus() == null) {
            searchCommandForm.setFilterStatus("filterAll");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if(page == null){
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("booking/commandList");

        Page<Command> commands = this.commandService
                .getCommandPageByUserAndSearchForm(user, searchCommandForm, 4, pageNumber-1);

        mv.addObject("commands", commands);
        mv.addObject("pageNumber", (String) page );
        mv.addObject("form", searchCommandForm);

        return mv;
    }


    @RequestMapping(value = "/details/{command}", method = RequestMethod.GET)
    public ModelAndView bookingDetails(@PathVariable Command command){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if(user != command.getUser()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Booking not found");
        }
        ModelAndView mv = new ModelAndView("booking/commandDetails");
        List<Booking> bookings = command.getBookings();
        if(bookings.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Booking details not found");
        }

        mv.addObject("bookings", bookings);

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView addBooking(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName()) ;

        List<Equipment> equipmentList = this.equipmentService.findAll();
        List<Line> lineList = this.lineService.findAll();
        Map<String, Integer> counterMap = new HashMap<String, Integer>();

        ModelAndView mv = new ModelAndView("booking/reservationForm");

        BookingForm bookingForm = new BookingForm();
        mv.addObject("bookingForm", bookingForm);
        mv.addObject("equipmentList", equipmentList);
        mv.addObject("lineList", lineList);
        mv.addObject("user", user);
        mv.addObject("counterMap", counterMap);

        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addBookingSubmit(
            @Valid BookingForm bookingForm,
            BindingResult bindingResult,
            @RequestBody String postPayload, Model model
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Map<String, Integer> counterMap = new HashMap<>();
        if (bookingForm.getDateStart().getTime() <= bookingForm.getDateEnd().getTime()) {
            Calendar dateToStart = Calendar.getInstance();
            dateToStart.setTime(bookingForm.getDateStart());
            Calendar dateToEnd = Calendar.getInstance();
            dateToEnd.setTime(bookingForm.getDateEnd());
            counterMap = bookingService.getAvailablePlaceCounts(dateToStart, dateToEnd);
        }

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
            model.addAttribute("user", user);
            model.addAttribute("counterMap", counterMap);

            return "booking/reservationForm";
        } else { // Saving the Command with its Bookings
            List<Booking> bookingList =
                    this.bookingService.BookingFormToBookingList(bookingForm, user);

            Command command = new Command(bookingList, user);
            command.setComment(bookingForm.getComment());

            this.commandService.saveWithBookingList(command);

            return "redirect:/paypal/cart/" + command.getId() + "#navbar";
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

        if (command.getId() == 0 || user.getId() != command.getUser().getId() || command.getPaymentStatus() != CommandPaymentStatus.CART) {
            return false;
        }

        return true;
    }


}
