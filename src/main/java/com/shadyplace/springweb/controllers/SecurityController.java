package com.shadyplace.springweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class SecurityController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("security/login");

        return mv;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    ModelAndView logout(){
        ModelAndView mv = new ModelAndView("security/logout");
        return mv;
    }
    @RequestMapping(value = "/not-found", method = RequestMethod.GET)
    ModelAndView notFound(){
        ModelAndView mv = new ModelAndView("notFound");
        return mv;
    }
}
