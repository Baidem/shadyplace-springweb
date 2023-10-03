package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.exception.FileTypeException;
import com.shadyplace.springweb.forms.SearchForm;
import com.shadyplace.springweb.models.Article;
import com.shadyplace.springweb.models.Image;
import com.shadyplace.springweb.services.ArticleService;
import com.shadyplace.springweb.services.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.GregorianCalendar;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    ArticleService articleService;
    @Autowired
    ImageService imageService;

    @RequestMapping( "/article/list")
    public ModelAndView getAll(@RequestParam(required = false) String page,
                               @RequestParam(required = false) String searchBar) {
        if (page == null) {
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("admin/article/list");

        if (searchBar == null) {
            searchBar = "";
        }

        Page<Article> articles = this.articleService
                .getArticlePageBySearchForm(new SearchForm(searchBar), 4, pageNumber - 1);

        mv.addObject("articles", articles);
        mv.addObject("pageNumber", (String) page);
        mv.addObject("form", new SearchForm(searchBar));

        return mv;
    }

    @RequestMapping(value = "article/list", method = RequestMethod.POST)
    public ModelAndView searchFormSubmit(@RequestParam(required = false) String page,
                                         @Valid SearchForm searchForm,
                                         BindingResult bindingResult){

        if(page == null){
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("admin/article/list");

        Page<Article> articles = this.articleService
                .getArticlePageBySearchForm(searchForm, 4, pageNumber-1);

        mv.addObject("articles", articles);
        mv.addObject("pageNumber", (String) page );
        mv.addObject("form", searchForm);

        return mv;
    }

//    @RequestMapping(value = "/article/add", method = RequestMethod.GET)
//    public ModelAndView add(){
//
//        ModelAndView mv = new ModelAndView("admin/form-art-img");
//        Article article = new Article();
//        Image image = new Image();
//        article.setImage(image);
//
//        mv.addObject("article", article);
//
//
//        return mv;
//    }
//
//    @RequestMapping(value = "/article/add", method = RequestMethod.POST)
//    public String addSubmit(
//            @Validated Article article,
//            BindingResult bindingResult,
//            @RequestParam("uploadImage") MultipartFile uploadImage ,
//            Model model) {
//
//        if (article == null) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "article not found");
//        }
//        if(bindingResult.hasErrors()){
//            return "admin/form-art-img";
//        } else {
//            try {
//                String fileName = this.imageService.upload(uploadImage);
//                article.getImage().setLocation(fileName);
//                article.getImage().setAddedAt(new GregorianCalendar());
//                article.getImage().setOriginalName(uploadImage.getOriginalFilename());
//                article.getImage().setMimeType(uploadImage.getContentType());
//
//                // save file
//                imageService.save(article.getImage());
//                article.setPublicationDate(new GregorianCalendar());
//                article.setImage(article.getImage());
//                articleService.save(article);
//
//                // redirection
//                return "redirect:/admin/article/list#navbar";
//
//            } catch (FileTypeException e) {
//                model.addAttribute("uploadError", "We only accept JPG and PNG files");
//                return "admin/form-art-img";
//            } catch (IOException e) {
//                model.addAttribute("uploadError", "Unable to load, please contact an administrator");
//                return "admin/form-art-img";
//            }
//        }
//    }

    @RequestMapping(value = "/article/add", method = RequestMethod.GET)
    public ModelAndView add(){

        ModelAndView mv = new ModelAndView("admin/article/form");
        Article article = new Article();

        mv.addObject("article", article);

        return mv;
    }

    @RequestMapping(value = "/article/add", method = RequestMethod.POST)
    public String addSubmit(@Validated Article article, BindingResult bindingResult, Model model) {

        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "article not found");
        }
        if(bindingResult.hasErrors()){
            return "admin/article/form";
        } else {
                // save file
                Image defaultImage = imageService.findByLocation("upload/default-image.jpg");
                if (defaultImage != null) {article.setImage(defaultImage);}
                article.setPublicationDate(new GregorianCalendar());
                article.setImage(article.getImage());
                articleService.save(article);

                // redirection
                return "redirect:/admin/article/preview/" + article.getId();
        }
    }


    @RequestMapping("article/preview/{article}")
    public ModelAndView article(@PathVariable(required = false) Article article){

        ModelAndView mv = new ModelAndView("/admin/article/preview");
        mv.addObject("article", article);

        return  mv;
    }

    @RequestMapping(value = "/article/delete/{article}", method = RequestMethod.GET)
    public String delete(@Valid Article article){

        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found"
            );
        }
        this.articleService.remove(article);

        return "redirect:/admin/article/list#navbar";
    }

    @RequestMapping(value ="/article/edit/{article}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(required = false) Article article){

        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found");
        }

        ModelAndView mv = new ModelAndView("admin/article/form");
        mv.addObject("article", article);

        return  mv;
    }

    @RequestMapping(value = "/article/edit/{article}", method = RequestMethod.POST)
    public String editSubmit(@Valid Article article, BindingResult bindingResult, Model model){

        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found"
            );
        }
        if(bindingResult.hasErrors()) {
            return "admin/article/form";
        } else {
                articleService.save(article);

            // redirection
            return "redirect:/admin/article/preview/" + article.getId();
        }
    }


//    @RequestMapping(value ="/article/edit/{article}", method = RequestMethod.GET)
//    public ModelAndView edit(@PathVariable(required = false) Article article){
//
//        if (article == null) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Article not found");
//        }
//
//        ModelAndView mv = new ModelAndView("admin/form");
//        mv.addObject("article", article);
//
//        return  mv;
//    }
//
//    @RequestMapping(value = "/article/edit/{article}", method = RequestMethod.POST)
//    public String editSubmit(@Valid Article article, BindingResult bindingResult,Model model){
//
//
//        if (article == null) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Article introuvable"
//            );
//        }
//        if(bindingResult.hasErrors()) {
//            return "admin/form";
//        } else {
//            this.articleService.save(article);
//            return "redirect:/admin/article/list#navbar";
//        }
//    }

    @RequestMapping(value = "/image/add/{article}", method = RequestMethod.GET)
    public ModelAndView addArticleImage(@PathVariable(required = false) Article article){

        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found"
            );
        }

        Image image = new Image();
        
        ModelAndView mv = new ModelAndView("admin/image/form");
        mv.addObject("image", image);
        mv.addObject("article", article);
        
        return mv;
    }

    @RequestMapping(value = "/image/add/{article}", method = RequestMethod.POST)
    public String addArticleImageSubmit(
            @Validated Image image,
            BindingResult bindingResult,
            Article article,
            @RequestParam("uploadImage") MultipartFile uploadImage,
            Model model
    ) {

        if(bindingResult.hasErrors()){
            return "admin/image/form";
        } else {
            try {
                String fileName = this.imageService.upload(uploadImage);
                image.setLocation(fileName);
                image.setAddedAt(new GregorianCalendar());
                image.setOriginalName(uploadImage.getOriginalFilename());
                image.setMimeType(uploadImage.getContentType());

                // save file
                imageService.save(image);
                article.setImage(image);
                articleService.save(article);

                // redirection
                return "redirect:/admin/article/preview/" + article.getId();

            } catch (FileTypeException e) {
                model.addAttribute("uploadError", "We only accept JPG and PNG files");
                return "admin/image/form";
            } catch (IOException e) {
                model.addAttribute("uploadError", "Unable to load, please contact an administrator");
                return "admin/image/form";
            }
        }
    }
    @RequestMapping(value = "/image/edit/{article}", method = RequestMethod.GET)
    public ModelAndView editArticleImage(@PathVariable(required = false) Article article){

        Image image;
        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found"
            );
        }

            System.out.println("hello");
        ModelAndView mv = new ModelAndView("admin/image/form");
        if (article.getImage() == null) {
            image = new Image();
        } else {
            image = article.getImage();
        }
        if (article.getImage().getLocation().equals("upload/default-image.jpg")) {

        }

        mv.addObject("image", image);
        mv.addObject("article", article);

        return mv;
    }

    @RequestMapping(value = "/image/edit/{article}", method = RequestMethod.POST)
    public String editArticleImageSubmit(
            @Validated Image image,
            BindingResult bindingResult,
            Article article,
            @RequestParam("uploadImage") MultipartFile uploadImage,
            Model model
    ) {
        System.out.println("Hello");
        if(bindingResult.hasErrors()){
            model.addAttribute("image", image);
            return "admin/image/form";
        } else {
            try {
                if (!uploadImage.getOriginalFilename().equals("")) {
                    String fileName = this.imageService.upload(uploadImage);
                    image.setLocation(fileName);
                    image.setAddedAt(new GregorianCalendar());
                    image.setOriginalName(uploadImage.getOriginalFilename());
                    image.setMimeType(uploadImage.getContentType());
                    imageService.save(image);
                }
                // save file
                article.setImage(image);
                articleService.save(article);

                // redirection
                return "redirect:/admin/article/preview/" + article.getId();

            } catch (FileTypeException e) {
                model.addAttribute("uploadError", "We only accept JPG and PNG files");
                return "admin/image/form";
            } catch (IOException e) {
                model.addAttribute("uploadError", "Unable to load, please contact an administrator");
                return "admin/image/form";
            }
        }
    }

}
