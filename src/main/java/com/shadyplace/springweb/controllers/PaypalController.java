package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.models.bookingResa.Command;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.models.enums.CommandPaymentStatus;
import com.shadyplace.springweb.models.paypal.CompletedOrder;
import com.shadyplace.springweb.services.bookingResa.CommandService;
import com.shadyplace.springweb.services.userAuth.UserService;
import com.shadyplace.springweb.services.paypal.PaypalService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    UserService userService;

    // MY CART //
    @RequestMapping(value = "/cart/{command}", method = RequestMethod.GET)
    public ModelAndView myCart(@PathVariable Command command) {
        ModelAndView mv = new ModelAndView("paypal/cart");
        User user = command.getUser();
        mv.addObject("command", command);
        mv.addObject("user", user);

        return mv;
    }
    // MY CART SUBMIT //
    @PostMapping(value = "/cart/{command}")
    public String myCartSubmit(@PathVariable Command command, @RequestParam("conditionsOfSale") boolean conditionsOfSale) {
        if (conditionsOfSale = true){
            command.setPaymentStatus(CommandPaymentStatus.AWAITING_PAYMENT);
            commandService.save(command);

            return "redirect:/paypal/payment/" + command.getId();
        }
        return "redirect:/cart/"+command.getId();
    }
    // SHOULD DISPLAY CART //
    private boolean shouldDisplayCart(Command command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if (command.getId() == 0 || user.getId() != command.getUser().getId() || command.getPaymentStatus() != CommandPaymentStatus.CART) {
            return false;
        }

        return true;
    }
    // CAPTURE PAYMENT //
    @RequestMapping(value = "/capture/{command}", method = RequestMethod.GET)
    public String capturePayment(@RequestParam("token") String token, @PathVariable Command command) {

        CompletedOrder completedOrder = this.paypalService.capturePayment(token, command);

        if (completedOrder.getStatus() == "success") {
            return "redirect:/paypal/success";
        } else {
            return "redirect:/paypal/error";
        }
     }
    // PROCEED PAYMENT //
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
        if (!(command.getPaymentStatus() == CommandPaymentStatus.AWAITING_PAYMENT)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error in command status"
            );

        } else {
            // send request to Paypal
            response.sendRedirect(
                    paypalService.createPayment(BigDecimal.valueOf(command.getTotalPrice()), command)
                            .getRedirectUrl()
            );
            // update order status to AWAITING_PAYMENT
            command.setPaymentStatus(CommandPaymentStatus.AWAITING_PAYMENT);
            this.commandService.save(command);
        }

    }
    // CANCEL //
    @RequestMapping("/cancel")
    public ModelAndView cancel(){
        ModelAndView mv = new ModelAndView("paypal/cancel");
        return mv;
    }
    // PAYMENT CANCEL //
    @RequestMapping(value = "/cancel/{command}", method = RequestMethod.GET)
    public ModelAndView paymentCancel(@PathVariable Command command) {
        if (shouldCancelCommand(command)) {
            commandService.delete(command);
            System.out.println(command.toString());
        }
        ModelAndView mv = new ModelAndView("paypal/cancel");
        return mv;
    }
    // SHOULD CANCEL COMMAND //
    private boolean shouldCancelCommand(Command command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if (command.getId() == 0 || user.getId() != command.getUser().getId() || command.getPaymentStatus() != CommandPaymentStatus.AWAITING_PAYMENT) {
            return false;
        }

        return true;
    }
    // SUCCESS //
    @RequestMapping("/success")
    public ModelAndView success(){
        System.out.println("success ?");

        ModelAndView mv = new ModelAndView("paypal/success");

        return mv;
    }
}
