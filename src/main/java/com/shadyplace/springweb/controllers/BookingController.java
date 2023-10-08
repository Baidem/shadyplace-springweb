package com.shadyplace.springweb.controllers;

import com.lowagie.text.DocumentException;
import com.shadyplace.springweb.forms.BookingForm;
import com.shadyplace.springweb.forms.ParasolForm;
import com.shadyplace.springweb.models.*;
import com.shadyplace.springweb.services.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    PDFService pdfService;
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

        // hasErrors
        if (bindingResult.hasErrors()) {

            model.addAttribute("fields", bindingResult);
            model.addAttribute("bookingForm", bookingForm);

            // select
            model.addAttribute("equipmentList", equipmentList);
            model.addAttribute("lineList", lineList);

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

    @RequestMapping(value = "/pdf/{command}", method = RequestMethod.GET)
    public void downloadPdf(@PathVariable(required = false) Command command,
                            HttpServletResponse response)
            throws DocumentException, IOException {

        if (command == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Command not found");
        }

        this.pdfService.generatePdf(command);

        InputStream inputStream = new FileInputStream(
                new File("src/main/resources/static/pdf/spring_pdf.pdf")
        );
        IOUtils.copy(inputStream, response.getOutputStream());
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=export_command" +currentDateTime+".pdf";
        response.setHeader(headerKey, headerValue);

        response.flushBuffer();
    }


}
