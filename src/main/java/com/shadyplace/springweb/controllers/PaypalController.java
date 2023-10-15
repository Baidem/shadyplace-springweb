package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.models.enums.CommandStatus;
import com.shadyplace.springweb.models.paypal.CompletedOrder;
import com.shadyplace.springweb.services.CommandService;
import com.shadyplace.springweb.services.PaypalService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequestMapping("/paypal")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;
    @Autowired
    CommandService commandService;

    // obsolète
    @RequestMapping(value = "/panier", method = RequestMethod.GET)
    public ModelAndView panier() {
        ModelAndView mv = new ModelAndView("panier");
        return mv;
    }

    @RequestMapping(value = "/capture", method = RequestMethod.GET)
    public String capturePayment(@RequestParam("token") String token) {

        CompletedOrder completedOrder = this.paypalService.capturePayment(token);

        if (completedOrder.getStatus() == "success") {
            return "redirect:/paypal/success";
        } else {
            return "redirect:/paypal/error";
        }
     }

    @RequestMapping(value = "/payment/{command}", method = RequestMethod.GET)
    public void proceedPayment(
            HttpServletResponse response, @Valid Command command,
            BindingResult bindingResult, Model model
    ) throws IOException {
        // check the command
        if (command == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Command not found"
            );
        }
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid command"
            );
        }
        if (!(command.getStatus() == CommandStatus.CART)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error in command status"
            );

        } else {
            // send request to Paypal
            response.sendRedirect(
                    paypalService.createPayment(BigDecimal.valueOf(command.getTotalPrice()))
                            .getRedirectUrl()
            );
            // update order status to AWAITING_PAYMENT
            command.setStatus(CommandStatus.AWAITING_PAYMENT);
            this.commandService.save(command);
        }

    }

    @RequestMapping("/cancel")
    public ModelAndView cancel(){
        ModelAndView mv = new ModelAndView("booking/cancel");
        return mv;
    }
}
