package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.models.articleBlog.Article;
import com.shadyplace.springweb.models.articleBlog.Image;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.services.userAuth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/myaccount")
public class MyaccountController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView myaccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        ModelAndView mv = new ModelAndView("myaccount/myaccount");
        mv.addObject("user", user);

        return mv;
    }

}
