package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.models.enums.CommandStatus;
import com.shadyplace.springweb.models.paypal.CompletedOrder;
import com.shadyplace.springweb.services.CommandService;
import com.shadyplace.springweb.services.UserService;
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

    @RequestMapping(value = "/cart/{command}", method = RequestMethod.GET)
    public ModelAndView myCart(@PathVariable Command command) {
        ModelAndView mv = new ModelAndView("paypal/cart");
        User user = command.getUser();
        mv.addObject("command", command);
        mv.addObject("user", user);

        return mv;
    }

    @PostMapping(value = "/cart/{command}")
    public String myCartSubmit(@PathVariable Command command, @RequestParam("conditionsOfSale") boolean conditionsOfSale) {
        if (conditionsOfSale = true){
            command.setStatus(CommandStatus.AWAITING_PAYMENT);
            commandService.save(command);

            return "redirect:/paypal/payment/" + command.getId();
        }
        return "redirect:/cart/"+command.getId();
    }

    private boolean shouldDisplayCart(Command command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if (command.getId() == 0 || user.getId() != command.getUser().getId() || command.getStatus() != CommandStatus.CART) {
            return false;
        }

        return true;
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
        if (!(command.getStatus() == CommandStatus.AWAITING_PAYMENT)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error in command status"
            );

        } else {
            // send request to Paypal
            response.sendRedirect(
                    paypalService.createPayment(BigDecimal.valueOf(command.getTotalPrice()), command.getId())
                            .getRedirectUrl()
            );
            // update order status to AWAITING_PAYMENT
            command.setStatus(CommandStatus.AWAITING_PAYMENT);
            this.commandService.save(command);
        }

    }

    @RequestMapping("/cancel")
    public ModelAndView cancel(){
        ModelAndView mv = new ModelAndView("paypal/cancel");
        return mv;
    }

    @RequestMapping(value = "/cancel/{command}", method = RequestMethod.GET)
    public ModelAndView paymentCancel(@PathVariable Command command) {
        if (shouldCancelCommand(command)) {
            commandService.delete(command);
        }
        ModelAndView mv = new ModelAndView("paypal/cancel");
        return mv;
    }

    private boolean shouldCancelCommand(Command command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if (command.getId() == 0 || user.getId() != command.getUser().getId() || command.getStatus() != CommandStatus.AWAITING_PAYMENT) {
            return false;
        }

        return true;
    }

    @RequestMapping("/success")
    public ModelAndView success(){
        ModelAndView mv = new ModelAndView("paypal/success");
        return mv;
    }
}
