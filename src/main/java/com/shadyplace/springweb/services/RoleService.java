package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Role;
import com.shadyplace.springweb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findByRoleName(String roleName) {
        return this.roleRepository.findFirstByRoleName(roleName);
    }

    public void save(Role role){
        this.roleRepository.save(role);
    }
}