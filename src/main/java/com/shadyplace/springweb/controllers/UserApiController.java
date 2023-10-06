package com.shadyplace.springweb.controllers;

import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.services.CommandService;
import com.shadyplace.springweb.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequestMapping("/api/users")
@RestController()
public class UserApiController {

    @Autowired
    UserService userService;
    @Autowired
    CommandService commandService;

    @GetMapping(value = "/")
    public List<User> getAll() {
        return this.userService.findAll();
    }

    @GetMapping(value = "/{user}")
    public User getOne(@PathVariable(name = "user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"); // réponse 404
        }
        return user;
    }

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

        User existUser = this.userService.findByEmail(user.getEmail());

        if (existUser != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "L'utilisateur avec cet email existe déjà"
            );
        }
        user = this.userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{user}")
    public ResponseEntity<User> update(
            @PathVariable(name = "user", required = false) User user,
            @Valid @RequestBody User userUpdate
    ){
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet utilisateur n'existe pas"); // réponse 404
        }

        List<User> userWithEmail = this.userService.findByEmailAndNotId(userUpdate.getEmail(), user.getId());

        if (!userWithEmail.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Un autre utilisateur a cet email"
            ); // réponse 400
        }
        userUpdate.setId((user.getId()));
        userUpdate = this.userService.saveUser(userUpdate);
        return new ResponseEntity<User>(userUpdate, HttpStatus.OK); // réponse 201 created
    }


    @DeleteMapping(value = "/{user}")
    public void deleteOne(@PathVariable(name = "user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"); // réponse 404
        } else {
            if (!this.commandService.getCommandByUser(user).isEmpty()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Impossible de supprimer cette utilisateur, il a des commandes"
                ); //réponse 400
            }
        }
        this.userService.delete(user);
    }

}
