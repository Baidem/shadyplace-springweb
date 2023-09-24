package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

}
