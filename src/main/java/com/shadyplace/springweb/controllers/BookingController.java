package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.LocationForm;
import com.shadyplace.springweb.repository.CommandRepository;
import com.shadyplace.springweb.repository.UserRepository;
import com.shadyplace.springweb.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
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
    Validator validator;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView addBooking(){

        ModelAndView mv = new ModelAndView("booking/form");

        BookingForm bookingForm = new BookingForm();
        mv.addObject("bookingForm", bookingForm);

        return mv;
    }

    // Soumission du formulaire de reservation
    // Il prend en paramètre un objet BookingForm
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addReservation(@Valid BookingForm bookingForm,
                                 BindingResult bindingResult,
                                 @RequestBody String postPayload, Model model){

        // J'appel mon service de reservation pour lui demander de transformer le body
        // de ma requête HTTP en objet en list d'emplacement
        List<LocationForm> listLocationForm = this.bookingService.payloadToResas(postPayload);

        // La liste d'emplacement réccupérée depuis mon service je l'ajoute à l'objet commande de mon formulaire
        bookingForm.setLocations(listLocationForm);

        // Permet de revalider notre champs reservation Form
        // Nous l'avons modifié donc il faut revalider.
        DataBinder binder = new DataBinder(bookingForm);
        binder.setValidator(validator);
        binder.validate(bookingForm, "reservationForm");
        bindingResult = binder.getBindingResult();

        if (bindingResult.hasErrors()) {


            model.addAttribute("fields", bindingResult);
            // On ajoute notre objet de reservation
            model.addAttribute("reservationForm", bookingForm);

            return "reservations/form";
        } else {
            // Envoyer les données en BDD
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            this.bookingService.persistReservationFromForm(bookingForm, currentPrincipalName);

            return "redirect:/";
        }

    }

}
