package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.AccountForm;
import com.shadyplace.springweb.models.enums.Country;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.services.userAuth.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/myaccount")
public class MyaccountController {
    @Autowired
    UserService userService;
    @Autowired
    Validator validator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView myaccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        ModelAndView mv = new ModelAndView("myaccount/myaccount");
        mv.addObject("user", user);

        return mv;
    }
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelAndView myaccountForm(){
        // User //
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        // Country selects //
        List<Country> countryList = Country.getAllDestinationCountries();
        // Account Form //
        AccountForm accountForm = new AccountForm();
        accountForm.setFirstname(user.getFirstname());
        accountForm.setLastname(user.getLastname());
        accountForm.setEmail(user.getEmail());
        accountForm.setCountry(user.getResidenceCountry().getName());
        // Model And View //
        ModelAndView mv = new ModelAndView("myaccount/myaccountForm");
        mv.addObject("countryList", countryList);
        mv.addObject("accountForm", accountForm);
        mv.addObject("user", user);

        return mv;
    }
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String myaccountFormSubmit(
            @Valid AccountForm accountForm,
            BindingResult bindingResult,
            Model model
    ) {
        // User //
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        // Country selects //
        List<Country> countryList = Country.getAllDestinationCountries();
        // Revalidate bookingForm
        DataBinder binder = new DataBinder(accountForm);
        binder.setValidator(validator);
        binder.validate(accountForm, "accountForm");
        bindingResult = binder.getBindingResult();
        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

        // hasErrors //
        if (bindingResult.hasErrors()) {
            // Model //
            model.addAttribute("fields", bindingResult);
            model.addAttribute("accountForm", accountForm);
            model.addAttribute("countryList", countryList);
            model.addAttribute("errors", globalErrors);
            model.addAttribute("user", user);

            return "myaccount/myaccountForm";
        } else {
            // Set user with accountForm //
            user.setFirstname(accountForm.getFirstname());
            user.setLastname(accountForm.getLastname());
            user.setEmail(accountForm.getEmail());
            Country newCountry = Country.getCountryByNameOrAbbreviation(accountForm.getCountry());
            user.setResidenceCountry(newCountry);
            // Saving the User modified //
            this.userService.saveUser(user);

            return "redirect:/myaccount";
        }
    }
}
