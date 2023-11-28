package com.shadyplace.springweb.services.userAuth;

import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.repository.userAuth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void save(User user){
        this.userRepository.save(user);
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User saveUser(User user){
        return this.userRepository.save(user);
    }

    public List<User> findByEmailAndNotId(String email, Long id){
        return this.userRepository.findByEmailWithoutId(email, id);
    }

    public void delete(User user){
        this.userRepository.delete(user);
    }

}
