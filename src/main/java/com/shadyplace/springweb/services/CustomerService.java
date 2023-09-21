package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Customer;
import com.shadyplace.springweb.models.User;
import com.shadyplace.springweb.repository.CustomerRepository;
import com.shadyplace.springweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer findByEmail(String email){
        return this.customerRepository.findByEmail(email);
    }


}
