package com.shadyplace.springweb.services.userAuth;

import com.shadyplace.springweb.models.userAuth.Role;
import com.shadyplace.springweb.repository.userAuth.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Role> getAll(){
        return roleRepository.findAll();
    }
}