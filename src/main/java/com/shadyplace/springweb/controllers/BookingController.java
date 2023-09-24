package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.BookingForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView addBooking(){

        ModelAndView mv = new ModelAndView("bookings/form");

        BookingForm bookingForm = new BookingForm();
        mv.addObject("bookingForm", bookingForm);

        return mv;
    }
}
