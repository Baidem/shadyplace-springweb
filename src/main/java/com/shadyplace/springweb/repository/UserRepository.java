package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    @Override
    User save(User user);

    @Query(value = "FROM User WHERE id != :id AND email = :email")
    List<User> findByEmailWithoutId(String email, Long id);

    @Override
    List<User> findAll();

}
