package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Role;
import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpringAuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        for (Role role: user.getRoles()){
            SimpleGrantedAuthority authRole =  new SimpleGrantedAuthority(role.getRoleName());
            roles.add(authRole);
         }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }

}
