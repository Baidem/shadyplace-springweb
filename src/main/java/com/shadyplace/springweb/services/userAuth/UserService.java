package com.shadyplace.springweb.services.userAuth;

import com.shadyplace.springweb.forms.SearchForm;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.repository.userAuth.UserCriteriaRepository;
import com.shadyplace.springweb.repository.userAuth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserCriteriaRepository userCriteriaRepository;

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

    public Page<User> getUserPageBySearchForm(SearchForm searchForm, int nbResult, int page){
        Pageable pageable = PageRequest.of(page, nbResult);
        Page<User> userPaginated = this.userCriteriaRepository.getUserPageBySearchForm(searchForm, pageable);
        return userPaginated;
    }

}
