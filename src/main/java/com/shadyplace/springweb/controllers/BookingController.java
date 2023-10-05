package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.PlaceOptionForm;
import com.shadyplace.springweb.models.Equipment;
import com.shadyplace.springweb.models.Line;
import com.shadyplace.springweb.services.BookingService;
import com.shadyplace.springweb.services.EquipmentService;
import com.shadyplace.springweb.services.LineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    BookingService bookingService;
//    @Autowired
//    EquipmentService equipmentService;
//    @Autowired
//    LineService lineService;
//
//    @Autowired
//    Validator validator;

//    @RequestMapping(value = "/new", method = RequestMethod.GET)
//    public ModelAndView addBooking(){
//
//        List<Equipment> equipmentList = this.equipmentService.getAll();
//        List<Line> lineList = this.lineService.getAll();
//
//        ModelAndView mv = new ModelAndView("booking/form");
//
//        BookingForm bookingForm = new BookingForm();
//        mv.addObject("bookingForm", bookingForm);
//        mv.addObject("equipmentList", equipmentList);
//        mv.addObject("lineList", lineList);
//
//        return mv;
//    }
//
//    // Soumission du formulaire de reservation
//    // Il prend en paramètre un objet BookingForm
//    @RequestMapping(value = "/new", method = RequestMethod.POST)
//    public String addReservation(@Valid BookingForm bookingForm,
//                                 BindingResult bindingResult,
//                                 @RequestBody String postPayload, Model model){
//
//        List<Equipment> equipmentList = this.equipmentService.getAll();
//        List<Line> lineList = this.lineService.getAll();
//
//
//        // J'appel mon service de reservation pour lui demander de transformer le body
//        // de ma requête HTTP en objet en list d'emplacement
//        List<PlaceOptionForm> listPlaceOptionForm = this.bookingService.payloadToResas(postPayload);
//
//        // La liste d'emplacement réccupérée depuis mon service je l'ajoute à l'objet commande de mon formulaire
//        bookingForm.setLocations(listPlaceOptionForm);
//
//        // Permet de revalider notre champs reservation Form
//        // Nous l'avons modifié donc il faut revalider.
//        DataBinder binder = new DataBinder(bookingForm);
//        binder.setValidator(validator);
//        binder.validate(bookingForm, "reservationForm");
//        bindingResult = binder.getBindingResult();
//
//        if (bindingResult.hasErrors()) {
//
//
//            model.addAttribute("fields", bindingResult);
//            // On ajoute notre objet de reservation
//            model.addAttribute("reservationForm", bookingForm);
//
//            // select
//            model.addAttribute("equipmentList", equipmentList);
//            model.addAttribute("lineList", lineList);
//
//
//            return "reservations/form";
//        } else {
//            // Envoyer les données en BDD
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentPrincipalName = authentication.getName();
//
//            this.bookingService.persistReservationFromForm(bookingForm, currentPrincipalName);
//
//            return "redirect:/";
//        }
//
//    }

}
