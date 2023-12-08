package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.exception.FileTypeException;
import com.shadyplace.springweb.forms.SearchCommandForm;
import com.shadyplace.springweb.forms.SearchForm;
import com.shadyplace.springweb.forms.UserForm;
import com.shadyplace.springweb.models.articleBlog.Article;
import com.shadyplace.springweb.models.articleBlog.Image;
import com.shadyplace.springweb.models.bookingResa.Booking;
import com.shadyplace.springweb.models.bookingResa.Command;
import com.shadyplace.springweb.models.enums.CommandValidationStatus;
import com.shadyplace.springweb.models.enums.Country;
import com.shadyplace.springweb.models.userAuth.FamilyLink;
import com.shadyplace.springweb.models.userAuth.Role;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.services.articleBlog.ArticleService;
import com.shadyplace.springweb.services.articleBlog.ImageService;
import com.shadyplace.springweb.services.bookingResa.CommandService;
import com.shadyplace.springweb.services.bookingResa.LocationService;
import com.shadyplace.springweb.services.userAuth.FamilyLinkService;
import com.shadyplace.springweb.services.userAuth.RoleService;
import com.shadyplace.springweb.services.userAuth.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    ArticleService articleService;
    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;
    @Autowired
    CommandService commandService;
    @Autowired
    LocationService locationService;
    @Autowired
    private FamilyLinkService familyLinkService;
    @Autowired
    Validator validator;
    @Autowired
    RoleService roleService;

    @RequestMapping( "/location-manually/{booking}")
    public ModelAndView locationManually(
            @PathVariable(required = false) Booking booking
    ) {
        if (booking == null) {
            ModelAndView mv = new ModelAndView("notFound");
            return mv;
        }

        ModelAndView mv = new ModelAndView("admin/location/manually");

        Map<String, String> planningLocatiolnMap = locationService.getPlanningMap(booking.getBookingDate());
        var beach2D = locationService.beach2D();

        mv.addObject("beach2D", beach2D);
        mv.addObject("booking", booking);
        mv.addObject("map", planningLocatiolnMap);

        return mv;
    }

    @RequestMapping(value = "/location-manually/{booking}", method = RequestMethod.POST)
    public ModelAndView locationManuallySubmit(
            @RequestParam(required = false) String location,
            @PathVariable(required = false) Booking booking
    ){
        if (booking == null) {
            ModelAndView mv = new ModelAndView("notFound");
            return mv;
        }

        Map<String, String> planningLocatiolnMap = locationService.getPlanningMap(booking.getBookingDate());

        ModelAndView mv = new ModelAndView("admin/location/manually");

        // TODO Service d'assignation de la position
        var beach2D = locationService.beach2D();

        mv.addObject("beach2D", beach2D);
        mv.addObject("booking", booking);
        mv.addObject("map", planningLocatiolnMap);

        return mv;
    }

    @RequestMapping( "/location-automatic/{booking}")
    public String locationAutomatic(
            @PathVariable(required = false) Booking booking
    ) {
        if (booking == null) {
            ModelAndView mv = new ModelAndView("notFound");
            return "redirect:/not-found";
        }

        // TODO auto incrémentation de la localisation
        locationService.autoLocationBooking(booking);

        return "redirect:/admin/commands-manager-form/" + booking.getCommand().getId();
    }

    @RequestMapping( "/commands-manager-form/{command}")
    public ModelAndView commandsManagerForm(
        @RequestParam(required = false) String commandStatus,
        @PathVariable(required = false) Command command
    ) {
        if (command == null) {
            ModelAndView mv = new ModelAndView("notFound");
            return mv;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        ModelAndView mv = new ModelAndView("admin/command/commandManagerForm");

        mv.addObject("command", command);

        return mv;
    }

    @RequestMapping(value = "/commands-manager-form/{command}", method = RequestMethod.POST)
    public ModelAndView commandsManagerFormSubmit(
            @RequestParam(required = false) String commandStatus,
            @PathVariable(required = false) Command command
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        ModelAndView mv = new ModelAndView("admin/command/commandManagerForm");

        if (commandStatus.equals("validated")) {
            command.setValidationStatus(CommandValidationStatus.VALIDATED);
        } else if (commandStatus.equals("refused")) {
            command.setValidationStatus(CommandValidationStatus.REFUSED);
        } else {
            command.setValidationStatus(CommandValidationStatus.PENDING);
        }
        commandService.save(command);

        mv.addObject("command", command);

        return mv;
    }

    @RequestMapping( "/commands-manager")
    public ModelAndView commandsManager(
        @RequestParam(required = false) String page,
        @RequestParam(required = false) String searchBar,
        @RequestParam(required = false) String filterStatus
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if (page == null) {
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("admin/command/commandManager");

        if (searchBar == null) {
            searchBar = "";
        }
        if (filterStatus == null) {
            filterStatus = "";
        }

        Page<Command> commands = this.commandService
                .getCommandPageBySearchForm(
                        new SearchCommandForm(searchBar, filterStatus),
                        4,
                        pageNumber - 1
                );

        mv.addObject("commands", commands);
        mv.addObject("pageNumber", (String) page);
        mv.addObject("form", new SearchCommandForm(searchBar, filterStatus));

        return mv;
    }

    @RequestMapping(value = "/commands-manager", method = RequestMethod.POST)
    public ModelAndView commandsManagerSubmit(
            @RequestParam(required = false) String page,
            @Valid SearchCommandForm searchCommandForm,
            BindingResult bindingResult
    ){
        if (searchCommandForm.getSearchContentBar() == null) {
            searchCommandForm.setSearchContentBar("");
        }
        if (searchCommandForm.getFilterStatus() == null) {
            searchCommandForm.setFilterStatus("filterAll");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if(page == null){
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("admin/command/commandManager");

        Page<Command> commands = this.commandService
                .getCommandPageBySearchForm(searchCommandForm, 4, pageNumber-1);

        mv.addObject("commands", commands);
        mv.addObject("pageNumber", (String) page );
        mv.addObject("form", searchCommandForm);

        return mv;
    }

    @RequestMapping( "/article/list")
    public ModelAndView articleList(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String searchBar
    ){
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
    public ModelAndView articleListSubmit(
            @RequestParam(required = false) String page,
            @Valid SearchForm searchForm,
            BindingResult bindingResult
    ){
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

    @RequestMapping(value = "/article/add", method = RequestMethod.GET)
    public ModelAndView articleAdd(){

        ModelAndView mv = new ModelAndView("admin/article/form");
        Article article = new Article();

        mv.addObject("article", article);

        return mv;
    }

    @RequestMapping(value = "/article/add", method = RequestMethod.POST)
    public String articleAddSubmit(
            @Validated Article article,
            BindingResult bindingResult,
            Model model
    ){
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
    public ModelAndView articlePreview(
            @PathVariable(required = false) Article article
    ){
        ModelAndView mv = new ModelAndView("/admin/article/preview");
        mv.addObject("article", article);

        return  mv;
    }

    @RequestMapping(value = "/article/delete/{article}", method = RequestMethod.GET)
    public String articleDelete(
            @Valid Article article
    ) throws IOException {
        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found"
            );
        }
        if (!article.getImage().getLocation().equals("upload/default-image.jpg")) {
            Image imageToDelete = article.getImage();
            String imageFileToDeleteLocation = imageToDelete.getLocation();
            this.articleService.remove(article);
            this.imageService.remove(imageToDelete);
            this.imageService.deleteImageFile(imageFileToDeleteLocation);

        } else {
            this.articleService.remove(article);
        }

        return "redirect:/admin/article/list";
    }

    @RequestMapping(value ="/article/edit/{article}", method = RequestMethod.GET)
    public ModelAndView articleEdit(
            @PathVariable(required = false) Article article
    ){
        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found");
        }

        ModelAndView mv = new ModelAndView("admin/article/form");
        mv.addObject("article", article);

        return  mv;
    }

    @RequestMapping(value = "/article/edit/{article}", method = RequestMethod.POST)
    public String articleEditSubmit(
            @Valid Article article, BindingResult bindingResult,
            Model model
    ){
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

    @RequestMapping(value = "/image/change/{article}", method = RequestMethod.GET)
    public ModelAndView changeArticleImage(
            @PathVariable(required = false) Article article
    ){

        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found"
            );
        }

        String oldImageLocation =  article.getImage().getLocation();

        Image image = new Image();

        ModelAndView mv = new ModelAndView("admin/image/form");
        mv.addObject("image", image);
        mv.addObject("article", article);

        return mv;
    }

    @RequestMapping(value = "/image/change/{article}", method = RequestMethod.POST)
    public String changeArticleImageSubmit(
            @Validated Image image,
            BindingResult bindingResult,
            Article article,
            @RequestParam("uploadImage") MultipartFile uploadImage,
            Model model
    ) {
        String oldImageLocation="";
        if (article.getImage() != null){
            oldImageLocation = article.getImage().getLocation();
        }
        if(bindingResult.hasErrors()){
            return "admin/image/form";
        } else {
            try {
                String fileName = this.imageService.upload(uploadImage);
                image.setLocation(fileName);
                image.setAddedAt(new GregorianCalendar());
                image.setOriginalName(uploadImage.getOriginalFilename());
                image.setMimeType(uploadImage.getContentType());

                // save
                imageService.save(image);
                article.setImage(image);
                articleService.save(article);

                // clean old image
                if (!oldImageLocation.equals("upload/default-image.jpg")){
                    this.imageService.remove(imageService.findByLocation(oldImageLocation));
                    this.imageService.deleteImageFile(oldImageLocation);
                }

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

    @RequestMapping( "/user/list")
    public ModelAndView userList(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String searchBar
    ){
        if (page == null) {
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("admin/user/userList");

        if (searchBar == null) {
            searchBar = "";
        }

        Page<User> users = this.userService
                .getUserPageBySearchForm(new SearchForm(searchBar), 4, pageNumber - 1);

        mv.addObject("users", users);
        mv.addObject("pageNumber", (String) page);
        mv.addObject("form", new SearchForm(searchBar));

        return mv;
    }

    @RequestMapping(value = "user/list", method = RequestMethod.POST)
    public ModelAndView userListSubmit(
            @RequestParam(required = false) String page,
            @Valid SearchForm searchForm,
            BindingResult bindingResult
    ){
        if(page == null){
            page = "1";
        }
        int pageNumber = Integer.valueOf(page);

        ModelAndView mv = new ModelAndView("admin/user/userList");

        Page<User> users = this.userService
                .getUserPageBySearchForm(searchForm, 4, pageNumber-1);

        mv.addObject("users", users);
        mv.addObject("pageNumber", (String) page );
        mv.addObject("form", searchForm);

        return mv;
    }

    // MY USER FORM //
    @RequestMapping(value = "/user/{user}", method = RequestMethod.GET)
    public ModelAndView userForm(@PathVariable User user){
        // Country selects //
        List<Country> countryList = Country.getAllDestinationCountries();
        // Family link selects //
        List<FamilyLink> familyLinkList = familyLinkService.getAll();
        // Roles selects //
        List<Role> roleList = roleService.getAll();
        // Account Form //
        UserForm userForm = new UserForm();
        userForm.setFirstname(user.getFirstname());
        userForm.setLastname(user.getLastname());
        userForm.setEmail(user.getEmail());
        userForm.setCountry(user.getResidenceCountry().getName());
        userForm.setFamilyLink(user.getFamilyLink());
        userForm.setRole(user.getRoles().get(0));
        // Model And View //
        ModelAndView mv = new ModelAndView("admin/user/userForm");
        mv.addObject("countryList", countryList);
        mv.addObject("userForm", userForm);
        mv.addObject("user", user);
        mv.addObject("familyLinkList", familyLinkList);
        mv.addObject("roleList", roleList);

        return mv;
    }
    // MY ACCOUNT FORM SUBMIT //
    @RequestMapping(value = "/user/{user}", method = RequestMethod.POST)
    public String userFormSubmit(
            @Valid UserForm userForm,
            @PathVariable User user,
            BindingResult bindingResult,
            Model model
    ) {
        // Country selects //
        List<Country> countryList = Country.getAllDestinationCountries();
        // Family link selects //
        List<FamilyLink> familyLinkList = familyLinkService.getAll();
        // Roles selects //
        List<Role> roleList = roleService.getAll();
        // Revalidate bookingForm
        DataBinder binder = new DataBinder(userForm);
        binder.setValidator(validator);
        binder.validate(userForm, "userForm");
        bindingResult = binder.getBindingResult();
        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

        // hasErrors //
        if (bindingResult.hasErrors()) {
            // Model //
            model.addAttribute("fields", bindingResult);
            model.addAttribute("userForm", userForm);
            model.addAttribute("countryList", countryList);
            model.addAttribute("familyLinkList", familyLinkList);
            model.addAttribute("errors", globalErrors);
            model.addAttribute("user", user);
            model.addAttribute("roleList", roleList);


            return "admin/user/userForm";
        } else {
            // Set user with accountForm //
            user.setFirstname(userForm.getFirstname());
            user.setLastname(userForm.getLastname());
            user.setEmail(userForm.getEmail());
            Country newCountry = Country.getCountryByNameOrAbbreviation(userForm.getCountry());
            user.setResidenceCountry(newCountry);
            user.setFamilyLink(userForm.getFamilyLink());
            List<Role> roles = new ArrayList<>();
            roles.add(userForm.getRole());
            user.setRoles(roles);
            // Saving the User modified //
            this.userService.saveUser(user);

            return "redirect:/admin/user/" + user.getId();
        }
    }

}
