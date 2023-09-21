package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Customer;
import com.shadyplace.springweb.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByEmail(String email);
}
