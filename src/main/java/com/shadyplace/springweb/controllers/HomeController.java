package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.forms.SearchForm;
import com.shadyplace.springweb.models.articleBlog.Article;
import com.shadyplace.springweb.services.articleBlog.ArticleService;
import com.shadyplace.springweb.services.articleBlog.UploadImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UploadImageService uploadImageService;

    @RequestMapping("")
    public ModelAndView home(@RequestParam(required = false) String page, @RequestParam(required = false) String searchBar) {
        if (page == null) {
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("home");

        if (searchBar == null) {
            searchBar = "";
        }

        Page<Article> articles = this.articleService.getArticlePageBySearchForm(new SearchForm(searchBar), 4, pageNumber - 1);

        mv.addObject("articles", articles);
        mv.addObject("pageNumber", (String) page);
        mv.addObject("form", new SearchForm(searchBar));

        return mv;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView searchFormSubmit(@RequestParam(required = false) String page,
                                         @Valid SearchForm searchForm,
                                         BindingResult bindingResult){

        if(page == null){
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("home");

        Page<Article> articles = this.articleService
                .getArticlePageBySearchForm(searchForm, 4, pageNumber-1);

        mv.addObject("articles", articles);
        mv.addObject("pageNumber", (String) page );
        mv.addObject("form", searchForm);

        return mv;
    }



}
