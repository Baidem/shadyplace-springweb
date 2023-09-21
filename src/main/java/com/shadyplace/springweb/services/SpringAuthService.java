package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Customer;
import com.shadyplace.springweb.repository.CustomerRepository;
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
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = this.customerRepository.findByEmail(email);

        if(customer == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("USER");
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(role);

        return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(), roles);
    }

}
